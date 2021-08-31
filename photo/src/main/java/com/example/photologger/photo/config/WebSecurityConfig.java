
package com.example.photologger.photo.config;

import com.example.photologger.photo.jwt.JwtAuthenticationFilter;
import com.example.photologger.photo.jwt.JwtTokenProvider;
import com.example.photologger.photo.service.AccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable   //구성
@EnableWebSecurity  //웹 보안 활성화
@AllArgsConstructor //롬복 생성자
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AccountsService accountsService;
    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()//header에 username과 password를 실어 보내면 브라우저 or 서버에서 그값을 읽어 인증하는 방식
//            .and()
//                .authorizeRequests()    //HttpServletRequest에 따라 접근(access)을 제한합니다.
//                .antMatchers("/accounts/**").hasRole("USER") //accounts 으로 시작하는 경로는 | USER 롤을 가진 사용자만 접근 가능합니다.
//                .antMatchers("/**").permitAll() //모든 경로에 대해 | 권한없이 접근 가능합니다.
//            .and()
//                .formLogin()    //form 기반으로 인증을 하도록 합니다. 로그인 정보는 기본적으로 HttpSession을 이용합니다.
//                .loginPage("/login")    //기본 제공되는 form 말고, 커스텀 로그인 폼을 사용.
//                .defaultSuccessUrl("/accounts/login/result")
//                .permitAll()
//            .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/accounts/logout"))
//                .logoutSuccessUrl("/logout/reslut")
//                .invalidateHttpSession(true)
//            .and()
//                .exceptionHandling().accessDeniedPage("/accounts/denied");
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}

