package de.nitrobox.authorization;

import java.util.HashMap;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    var additionalInfo = new HashMap<String, Object>();
    additionalInfo.put("tenantId", "43");
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
        additionalInfo);
    return accessToken;
  }
}
