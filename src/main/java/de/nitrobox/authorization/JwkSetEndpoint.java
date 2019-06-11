package de.nitrobox.authorization;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
class JwkSetEndpoint {

  private KeyPair keyPair;

  public JwkSetEndpoint(KeyPair keyPair) {
    this.keyPair = keyPair;
  }

  @GetMapping("/.well-known/jwks.json")
  @ResponseBody
  public Map<String, Object> getKey() {
    var publicKey = (RSAPublicKey) this.keyPair.getPublic();
    var key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }
}