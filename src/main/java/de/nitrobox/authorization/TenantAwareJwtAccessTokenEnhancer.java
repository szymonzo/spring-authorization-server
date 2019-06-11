package de.nitrobox.authorization;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@RequiredArgsConstructor
public class TenantAwareJwtAccessTokenEnhancer implements TokenEnhancer {

  private final ClientDetailsService clientDetailsService;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    var clientDetails = clientDetailsService
        .loadClientByClientId(authentication.getOAuth2Request().getClientId());
    Map<String, Object> additionalInfo = new HashMap<>(clientDetails.getAdditionalInformation());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
