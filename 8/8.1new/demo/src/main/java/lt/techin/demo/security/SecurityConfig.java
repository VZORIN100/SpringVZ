package lt.techin.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// Nesusiję tiesiogiai su Spring Security; reikalinga nurodyti Spring'ui, jog čia yra Bean'ų
@Configuration

// Reikalinga tam, kad nurodyti jog čia yra SecurityFilterChain; konfigūracija Spring Security
@EnableWebSecurity
public class SecurityConfig {

  //Day 8 JWT. At beginning of Teacher Example in ITJ we commented out this big @Bean section below.
  // Also changed a few things.

  // openssl rsa -pubout -in app.key -out app.pub
  // Nuoroda į application.properties failą
  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  // openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
  // Nuoroda į application.properties failą
  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;


  //Sukuriame Bean'ą kontekste (IoC container)
  //Nenaudojant šios anotacijos, neveiks mūsų custom filtras
  @Bean
  // filterChain - kuriame savo, custom filtrą. Apsauginis tikrintojas - nepraleis prie Controller,
  // jei neatitinka šios apsaugos
  public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
    http
            // Cross-site request forgery
            // Customizer - funkcinis interfeisas
            // Užkomentuoti .csrf neužteks - reikia lambdos
            // Galima išjungti, tik development tikslams
            .csrf(c -> c.disable())

            // Basic auth funkcionalumas
            .httpBasic(Customizer.withDefaults())

            // Forma kurią galime matyti. Galima išjungti arba palikti
            // .formLogin(Customizer.withDefaults())

            .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("SCOPE_ROLE_ADMIN")
                            //.requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")

                            // Gali būti ir kitų taisyklių
                            .requestMatchers(HttpMethod.GET, "/api/books").hasAuthority("SCOPE_ROLE_ADMIN")
                            //.requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")

                            // Leidžiama daryti bet kam, net neautintifikuotui klientui, į šį endpoint
                            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                            // Visi kiti endpoint'ai uždari
                            .anyRequest().authenticated()
                    //.anyRequest().permitAll()
            )

            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
            // Atjungiam by default session management, sessijos info laikys JWT
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Helperiai JWT klaidom atvaizduoti
            .exceptionHandling((exceptions) -> exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );

    return http.build();
  }

  @Bean
  // Reikia įgyvendinti, norint patenkinti
  // 1-ą kontraktą
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //Added below stuff on day 8 JWT.

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(key).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    // JSON Web Key
    JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwks);
  }

}

//================================================================================
//@Configuration copied from 7.1 . Before JWT stuff inserted.
//
//
/// / Nesusiję tiesiogiai su Spring Security; reikalinga nurodyti Spring'ui, jog čia yra Bean'ų
//@Configuration
//
/// / Reikalinga tam, kad nurodyti jog čia yra SecurityFilterChain; konfigūracija Spring Security
//@EnableWebSecurity
//public class SecurityConfig {
//
//  // Sukuriame Bean'ą kontekste (IoC container)
//  // Nenaudojant šios anotacijos, neveiks mūsų custom filtras
//  @Bean
//
//  // filterChain - kuriame savo, custom filtrą. Apsauginis tikrintojas - nepraleis prie Controller,
//  // jei neatitinka šios apsaugos
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            // Cross-site request forgery
//            // Customizer - funkcinis interfeisas
//            // Užkomentuoti .csrf neužteks - reikia lambdos
//            // Galima išjungti, tik development tikslams
//            .csrf(c -> c.disable())
//
//            // Basic auth funkcionalumas
//            .httpBasic(Customizer.withDefaults())
//
//            // Forma kurią galime matyti. Galima išjungti arba palikti
//            // .formLogin(Customizer.withDefaults())
//
//            .authorizeHttpRequests(authorize -> authorize
//                            .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
//
//                            // Gali būti ir kitų taisyklių
//                            .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")
//
//                            // Leidžiama daryti bet kam, net neautintifikuotui klientui, į šį endpoint
//                            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//
//                            // Visi kiti endpoint'ai uždari
//                            .anyRequest().authenticated()
//                    //.anyRequest().permitAll()
//            );
//
//    return http.build();
//  }
//
//  @Bean
//  // Reikia įgyvendinti, norint patenkinti
//  // 1-ą kontraktą
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//}