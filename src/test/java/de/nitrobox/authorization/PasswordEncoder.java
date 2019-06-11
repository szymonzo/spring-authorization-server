package de.nitrobox.authorization;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

  @Test
  public void name() {
    String test = new BCryptPasswordEncoder().encode("test");
    System.out.println(test);
  }
}
