package kr.co.kmarket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigration {
	
	@Autowired
	private SecurityUserService service;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll();
		
		// 사이트 위변조 요청 방지
		http.csrf().disable();
		

		// 로그인 설정
		http.formLogin()
		.loginPage("/member/login") // 인증이 필요한 URL 접근 시 로그인 으로 이동
		.defaultSuccessUrl("/")		// 로그인 성공 시 이동 할 페이지
		.failureUrl("/member/login?success=try") // 로그인 실패 시 로그인 으로 이동
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
		.logoutSuccessUrl("/member/login?success=logout");

		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);


		return http.build();
	}
	
	@Bean
    public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
    }
}