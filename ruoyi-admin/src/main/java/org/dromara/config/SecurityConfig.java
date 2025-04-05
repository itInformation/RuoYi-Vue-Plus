package org.dromara.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * admin 监控 安全配置
 *
 * @author Lion Li
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(HttpMethod.OPTIONS).permitAll().
                    // 放行登录、注册、登出、验证码等接口
                    requestMatchers(
                        "/auth/login",    // 登录接口
                        "/client/auth/login",    // 登录接口
                        "/auth/register", // 注册接口
                        "/client/auth/register", // 注册接口
                        "/auth/logout",   // 登出接口
                        "/client/auth/logout",   // 登出接口
                        "/resource/sms/code",   // 短信验证码
                        "/auth/code",   // 登出接口
                        "/client/user/profile/avatar"   // 用户上传头像接口
                    ).permitAll()
                    .anyRequest().authenticated()) // 配置登录行为（与放行的登录接口匹配）
            .formLogin(form -> form
                .loginProcessingUrl("/auth/login")
                .permitAll()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/client/auth/login")
                .permitAll()
            )
            // 配置登出行为（与放行的登出接口匹配）
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/client/auth/logout")
                .permitAll()
            )
            .build();
    }

}
