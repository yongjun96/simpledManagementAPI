package co.kr.yongjun.restfulservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    //API를 사용할 때 Basic Auth에 username과 password를 설정
    @Bean
    UserDetailsService userDetailsService(){

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails newUser = User.withUsername("yongjun")
                .password(passwordEncoder().encode("password1004"))
                .authorities("read")
                .build();

        userDetailsManager.createUser(newUser);

        return userDetailsManager;
    }

    //비밀번호를 인코딩해서 사용할 수 있도록 해줌
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //h2 콘솔에서는 사용하지 않도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    //API에서 Delete를 허용해주는 빈
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http,
                                              HandlerMappingIntrospector introspector) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
