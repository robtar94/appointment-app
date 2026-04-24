
package com.capgemini.training.appointmentbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth//
				.requestMatchers("/api/**").authenticated()//
				.anyRequest().permitAll())//
				.httpBasic(org.springframework.security.config.Customizer.withDefaults())//
				.formLogin(form -> form//
						.loginPage("/login")//
						.defaultSuccessUrl("/", true)//
						.failureUrl("/login?error=true")//
						.permitAll())//
				.logout(logout -> logout//
						.logoutUrl("/logout")//
						.logoutSuccessUrl("/login")//
						.permitAll())//
				.csrf(AbstractHttpConfigurer::disable)//
				.headers(headers -> headers//
						.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		return http.build();
	}

	@Bean
	static AnnotationTemplateExpressionDefaults templateExpressionDefaults() {
		return new AnnotationTemplateExpressionDefaults();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails admin = User.builder().username("admin").password("{noop}admin")
				.roles("ADMIN", "SPECIALIST", "RECEPTIONIST").build();

		UserDetails specialist = User.builder().username("specialist").password("{noop}specialist")
				.roles("SPECIALIST", "RECEPTIONIST").build();

		UserDetails receptionist = User.builder().username("receptionist").password("{noop}receptionist")
				.roles("RECEPTIONIST").build();

		UserDetails client = User.builder().username("client").password("{noop}client").roles().build();

		return new InMemoryUserDetailsManager(admin, specialist, receptionist, client);
	}
}
