package com.example.photologger.photo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configurable   //구성
@EnableWebSecurity  //웹 보안 활성화
@AllArgsConstructor //롬복 생성자
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();//비밀번호 암호화
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic()//header에 username과 password를 실어 보내면 브라우저 or 서버에서 그값을 읽어 인증하는 방식
            .and()
                .authorizeRequests()    //HttpServletRequest에 따라 접근(access)을 제한합니다.
                .antMatchers("/accounts/**").hasRole("USER") //accounts 으로 시작하는 경로는 | USER 롤을 가진 사용자만 접근 가능합니다.
                .antMatchers("/**").permitAll() //모든 경로에 대해 | 권한없이 접근 가능합니다.
            .and()
                .formLogin()    //form 기반으로 인증을 하도록 합니다. 로그인 정보는 기본적으로 HttpSession을 이용합니다.
                .loginPage("/login")    //기본 제공되는 form 말고, 커스텀 로그인 폼을 사용.
                .defaultSuccessUrl("/accounts/login/result")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/accounts/logout"))
                .logoutSuccessUrl("/logout/reslut")
                .invalidateHttpSession(true)
            .and()
                .exceptionHandling().accessDeniedPage("/accounts/denied");
    }
}
