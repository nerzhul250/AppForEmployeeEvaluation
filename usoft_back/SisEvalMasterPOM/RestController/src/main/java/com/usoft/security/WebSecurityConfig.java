package com.usoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		//securedEnabled = true,
		//jsr250Enabled = true,
		prePostEnabled = true
		)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeRequests().antMatchers("/usoftapi/auth/**").permitAll()
			
			.antMatchers(HttpMethod.GET,"/usoftapi/areas/**").hasAnyRole(Roles.ADMIN.toString(), Roles.OBSERVER.toString())
			.antMatchers(HttpMethod.POST,"/usoftapi/areas/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.PATCH,"/usoftapi/areas/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE,"/usoftapi/areas/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers(HttpMethod.GET,"/usoftapi/careerplans/**").hasAnyRole(Roles.ADMIN.toString(), Roles.OBSERVER.toString())
			.antMatchers(HttpMethod.POST,"/usoftapi/careerplans/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.PATCH,"/usoftapi/careerplans/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE,"/usoftapi/careerplans/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers("/usoft/criteria/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers(HttpMethod.GET,"/usoftapi/evaluations/**").hasAnyRole(Roles.ADMIN.toString(), Roles.OBSERVER.toString(), Roles.EVALUATOR.toString())
			.antMatchers(HttpMethod.POST,"/usoftapi/evaluations/**").hasAnyRole(Roles.ADMIN.toString(),Roles.EVALUATOR.toString())
			.antMatchers(HttpMethod.PATCH,"/usoftapi/evaluations/**").hasAnyRole(Roles.ADMIN.toString(),Roles.EVALUATOR.toString())
			.antMatchers(HttpMethod.DELETE,"/usoftapi/evaluations/**").hasAnyRole(Roles.ADMIN.toString(),Roles.EVALUATOR.toString())
			
			.antMatchers(HttpMethod.GET,"/usoftapi/indicators/**").hasAnyRole(Roles.OBSERVER.toString(), Roles.ADMIN.toString())
			.antMatchers(HttpMethod.POST,"/usoftapi/indicators/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.PATCH,"/usoftapi/indicators/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE,"/usoftapi/indicators/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers("/usoftapi/positions/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers("/usoftapi/role/**").hasRole(Roles.ADMIN.toString())
			
			.antMatchers(HttpMethod.GET,"/usoftapi/users/**").hasAnyRole(Roles.ADMIN.toString(),Roles.EVALUATOR.toString(),Roles.OBSERVER.toString(),Roles.EMPLOYEE.toString() )
			.antMatchers(HttpMethod.PATCH,"/usoftapi/users").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.PATCH,"/usoftapi/users/profile").hasAnyRole(Roles.ADMIN.toString(),Roles.EVALUATOR.toString(),Roles.OBSERVER.toString(),Roles.EMPLOYEE.toString() )
			.antMatchers(HttpMethod.POST,"/usoftapi/users/**").hasRole(Roles.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE,"/usoftapi/users/**").hasRole(Roles.ADMIN.toString())
			.anyRequest().authenticated().and()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);			
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}
}