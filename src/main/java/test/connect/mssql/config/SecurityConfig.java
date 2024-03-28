package test.connect.mssql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.
                authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/loginProc","/join","/joinProc","/send_JoinMail").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth
                        .loginProcessingUrl("/login").permitAll()
                );
        //.httpBasic(Customizer.withDefaults());

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        http
                .sessionManagement((auth) -> auth
                                .maximumSessions(1) // 1개의 id에 허용할 수 있는 중복 로그인 갯수
                                .maxSessionsPreventsLogin(false) // 허용된 중복 로그인 갯수를 초과한 경우
                        // true 일때 새로 로그인하는 것을 차단
                        // false 일때 기존 세션 하나 삭제
                );

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId() // 세션 고정 공격을 보호
                );


        return http.build();
    }

}
