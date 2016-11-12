package getaclue.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * Configuration class for Spring security.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        // Allow all traffic to /, /css/**
        http
            .authorizeRequests()
                .antMatchers("/", "/resources/**", "/newuser").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .and()
            .logout().permitAll();
    }

    @Override
    protected final void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager());
    }

    /**
     * Create the {@link UserDetailsManager} bean.
     *
     * @return an implementation of the user details manager
     */
    @SuppressWarnings("checkstyle:designforextension")
    @Bean
    protected UserDetailsManager userDetailsManager() {
        // Using an in-memory user manager for simplicity
        return new InMemoryUserDetailsManager(new Properties());
    }

}
