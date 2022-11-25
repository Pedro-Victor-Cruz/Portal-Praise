package br.com.praise.portal.service.segurity;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/portal/admin/**").permitAll()//.hasAnyAuthority(Permissions.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .successHandler((request, response, authentication) -> {

                    String login = authentication.getName();
                    Optional<User> user = userRepository.login(login);
                    if(user.isPresent()) {
                        int tempLogIn = (60*60);
                        CookieService.setCookie(response, "userID", String.valueOf(user.get().getID()),tempLogIn);
                        CookieService.setCookie(response, "nameUser", String.valueOf(user.get().getUsername()),tempLogIn);
                    }
                    String redirectURL = "/portal/home";
                    response.sendRedirect(redirectURL);
                })
                .and()
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/login").permitAll();
                    logout.deleteCookies("JSESSIONID", "userID", "nameUser");
                })
                .sessionManagement(session -> {
                    session.maximumSessions(1).maxSessionsPreventsLogin(true);
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .csrf().disable();
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web
//                .ignoring()
//                .antMatchers("/static/assets/**");
//    }

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns(
//                        "/login",
//                        "/register",
//                        "/register/new",
//                        "/logar",
//                        "/assets/**"
//                );
//    }
}
