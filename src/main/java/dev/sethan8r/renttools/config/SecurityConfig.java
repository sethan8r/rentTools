package dev.sethan8r.renttools.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //private final RoleBasedAuthenticationSuccessHandler successHandler;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authz -> authz
//                        // Полностью публичные разделы
//                        .requestMatchers(
//                                "/", "/home", "/about", "/services", "/catalog/**",
//                                "/products/**", "/articles/**", "/contact"
//                        ).permitAll()
//
//                        // Страницы аутентификации
//                        .requestMatchers("/login.html", "/register", "/registration.html").permitAll()
//                        .requestMatchers("/*.html", "/**/*.html").permitAll()
//
//                        // Статические ресурсы
//                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
//
//                        // Защищенные разделы (только для авторизованных)
//                        .requestMatchers("/profile/**", "/cart/**", "/orders/**").authenticated()
//
//                        // Админка
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//
//                        // Все остальное - публичное
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Страница логина
//                        .loginProcessingUrl("/perform_login") // URL для обработки формы
//                        .successHandler(successHandler) // Кастомный обработчик успешного входа
//                        .failureUrl("/login?error=true") // Страница при ошибке
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/?logout=true")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(false)
//                );
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RoleBasedAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers("/profile/**").authenticated()
                        .requestMatchers("/courier/**").hasAnyRole("ADMIN", "SUPERADMIN", "COURIER")
                        .requestMatchers("/order/**").authenticated()
                        .requestMatchers("/delivery/**").hasAnyRole("ADMIN", "SUPERADMIN", "COURIER")
                        .requestMatchers("/picture/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
                        .requestMatchers("/registration/courier").hasAnyRole("ADMIN", "SUPERADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Кастомная страница входа (можно убрать, будет стандартная)
                        .successHandler(successHandler)
                        .failureUrl("/login?error=true") // Используем наш кастомный обработчик. После успешного входа перенаправляем сюда
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                // Для API - возвращаем JSON
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType("application/json");
                                response.getWriter().write("{\"error\": \"Требуется аутентификация\"}");
                            } else {
                                // Для HTML - перенаправляем на страницу ошибки
                                response.sendRedirect("/error/401");
                            }
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                // Для API - возвращаем JSON
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.setContentType("application/json");
                                response.getWriter().write("{\"error\": \"Недостаточно прав\"}");
                            } else {
                                // Для HTML - перенаправляем на страницу ошибки
                                response.sendRedirect("/error/403");
                            }
                        })
                )
                .csrf(csrf -> csrf.disable()); // Временно отключаем CSRF

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder); // Используем безопасный encoder
        return authProvider;
    }
}
