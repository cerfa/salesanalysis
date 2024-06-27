//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    public CustomPasswordEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes(StandardCharsets.UTF_8)).equalsIgnoreCase(encodedPassword);
    }
}
