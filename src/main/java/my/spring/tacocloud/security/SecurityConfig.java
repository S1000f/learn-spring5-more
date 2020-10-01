package my.spring.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserRepositoryUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserRepositoryUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder pbkdf2PasswordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders").access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .passwordParameter("password") // 기본값 password 를 사용하지 않을경우 지정
                .defaultSuccessUrl("/design") // 로그인 성공시 자동 이동을 루트경로가 아니라 특정 지점을 지정가능
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                /* 스프링부트 내장 H2 을 사용하기 위해서 csrf, x-frame-options 을 해제하고
                * /h2-console 경로의 모든 접근을 허용시킨다. */
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new Pbkdf2PasswordEncoder());

//        // in-memory user store
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password("{noop}password1")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("user2")
//                .password("{noop}password2")
//                .roles("USER");

//        // JDBC user store
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled from users where username=?")
//                .authoritiesByUsernameQuery("select username, authority from authorities where username=?")
//                .passwordEncoder(new StubPasswordEncoder());

//        // LDAP user store
//        auth.ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .contextSource()
//                .root("dc=tacocloud, dc=com")
//                .ldif("classpath:ignore_users.ldif")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new Pbkdf2PasswordEncoder())
//                .passwordAttribute("userPassword");

    }

}
