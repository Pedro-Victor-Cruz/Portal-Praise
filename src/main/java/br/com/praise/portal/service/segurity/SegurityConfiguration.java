package br.com.praise.portal.service.segurity;

import br.com.praise.portal.Enum.Roles;
import br.com.praise.portal.model.Role;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurityConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/register/new").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/portal/home").hasAnyAuthority(Roles.CLIENTE.name(), Roles.GERENTE.name(), Roles.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        String login = authentication.getName();
                        System.out.println(login);
                        Optional<User> user = userRepository.login(login);
                        if(user.isPresent()) {
                            int tempLogIn = (60*60);
                            CookieService.setCookie(response, "userID", String.valueOf(user.get().getID()),tempLogIn);
                            CookieService.setCookie(response, "nameUser", String.valueOf(user.get().getUsername()),tempLogIn);
                        }
                        String redirectURL = "/portal/home";
                        response.sendRedirect(redirectURL);
                    }
                })
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll()
                .deleteCookies("JSESSIONID", "userID", "nameUser")
                .and()
                .csrf().disable();
        return http.build();
    }

    private boolean getAutorization(User user, String role) {
        for (Role roles: user.getRoles())
            if(roles.getRole().equals(role))
                return true;
        return false;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/static/assets/**");
    }

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
