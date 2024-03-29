package de.nitrobox.authorization;

import java.security.KeyPair;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Import(AuthorizationServerEndpointsConfiguration.class)
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  private KeyPair keyPair;

  public AuthorizationServerConfiguration(
      AuthenticationManager authenticationManager, KeyPair keyPair) {
    this.authenticationManager = authenticationManager;
    this.keyPair = keyPair;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(oauthDataSource())
        .passwordEncoder(passwordEncoder());
  }


  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    var tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(
        Arrays.asList(tokenEnhancer(endpoints.getClientDetailsService()),
            issuerAwareJwtAccessTokenEnhancer(), accessTokenConverter())
    );
    endpoints.tokenStore(tokenStore())
        .tokenEnhancer(tokenEnhancerChain)
        .approvalStoreDisabled()
        .authenticationManager(authenticationManager);
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource oauthDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  TokenStore tokenStore() {
    return new JdbcTokenStore(oauthDataSource());
  }

  @Bean
  JwtAccessTokenConverter accessTokenConverter() {
    var jwtTokenConverter = new JwtAccessTokenConverter();
    jwtTokenConverter.setKeyPair(this.keyPair);
    return jwtTokenConverter;
  }

  @Bean
  IssuerAwareJwtAccessTokenEnhancer issuerAwareJwtAccessTokenEnhancer() {
    return new IssuerAwareJwtAccessTokenEnhancer();
  }

  private TokenEnhancer tokenEnhancer(ClientDetailsService clientDetailsService) {
    return new TenantAwareJwtAccessTokenEnhancer(clientDetailsService);
  }
}
