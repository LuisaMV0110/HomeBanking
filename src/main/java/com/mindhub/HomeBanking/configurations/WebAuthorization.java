package com.mindhub.HomeBanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers( "/web/index.html","/web/assets/js/index.js","/web/assets/css/index.css", "/web/assets/img/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts", "/api/clients/current/cards","/api/transactions","/api/logout","/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/clients/current/cards","/api/accounts/{id}").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers("/api/clients/current","/web/accounts.html" , "/web/account.html" , "/web/cards.html","/web/createCards.html","/web/transfers.html","/web/loan-application.html","/web/assets/js/**","/web/assets/css/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/admin/**" , "/rest/**" , "/h2-console/**", "/api/clients","/api/loans").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/manager/loans").hasAuthority("ADMIN")
                .anyRequest().denyAll();
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        // Añadir método para eliminar la cookie
        http.logout().logoutUrl("/api/logout").deleteCookies( "JSESSIONID" );

        // Desactiva la verificación de tokens csrf para evitar conflictos
        http.csrf().disable();
        // Se desactiva la configuración por defecto para acceder a la H2-console
        http.headers().frameOptions().disable();
        // Si el usuario no está autenticado envía un mensaje de error 401?
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // Se ejecuta cuando el usuario inicia sesión con éxito, y borra los atributos de autenticación almacenados en la sesión HTTP
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        // Se encarga de enviar una respuesta HTTP de error en caso de que se produzca un fallo en la autenticación.
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_BAD_REQUEST));
        // Devuelve un código de estado HTTP 200 (OK) después de que el usuario haya cerrado sesión correctamente.
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        // Es solicitado por el SecurityFilterChain
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}