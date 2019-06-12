package de.nitrobox.authorization;

import java.security.KeyPair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@SpringBootApplication
public class AuthorizationApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationApplication.class, args);
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.authorization")
  AuthorizationServerProperties authorizationServerProperties(){
    return new AuthorizationServerProperties();
  }

  @Bean
  public KeyPair keyPair(AuthorizationServerProperties authorizationServerProperties) {
    final var jwt = authorizationServerProperties.getJwt();
    final var keyStoreKeyFactory =
        new KeyStoreKeyFactory(new ClassPathResource(jwt.getKeyStore()),
            jwt.getKeyPassword().toCharArray());
    return keyStoreKeyFactory.getKeyPair(jwt.getKeyAlias());
  }
}
