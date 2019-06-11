package de.nitrobox.authorization;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthorizationApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationApplication.class, args);
  }

  @Bean
  public KeyPair keyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
    return rsa.generateKeyPair();
  }

}
