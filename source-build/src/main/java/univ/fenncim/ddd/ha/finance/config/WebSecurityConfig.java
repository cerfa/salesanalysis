//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableCaching
public class WebSecurityConfig {

    @Value("${spring.security.user.name}")
    private String technicalUserName;
    @Value("${spring.security.user.password}")
    private String technicalUserKey;

    private final String  role_main = "ADMIN";

    public WebSecurityConfig() {
        //Just for sonarQube
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable();
        http.userDetailsService(this.userDetailsService()).authorizeRequests()
                .antMatchers("/finance/v1/api/**", "/finance/monitoring/**").authenticated();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails user1 = User.withUsername(technicalUserName).password(technicalUserKey)
                .passwordEncoder(rawPassword ->
                 Base64.getEncoder().encodeToString(rawPassword.getBytes(StandardCharsets.UTF_8)))
                .roles(role_main).authorities(role_main).build();

        inMemoryUserDetailsManager.createUser(user1);
        UserDetails user2 = User.withUsername("fenncims").password("64007a3b-a096-4c64-92f1-c81c6a6ced75")
                .passwordEncoder( rawPassword ->
            Base64.getEncoder().encodeToString(rawPassword.getBytes(StandardCharsets.UTF_8)))
                .roles("USER").authorities("USER").build();

        inMemoryUserDetailsManager.createUser(user2);
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("population");
    }
}
