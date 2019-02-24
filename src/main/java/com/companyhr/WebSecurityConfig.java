package com.companyhr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class
WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf()
                //.and()
                .authorizeRequests()
                .antMatchers("/", "/greeting", "/result", "/resources/**",
                        "/registration", "/welcome", "/api/**", "/accesdenied").permitAll()
                .antMatchers("/home", "/init", "/restricted/adminhomepage", "/createhr").hasRole("ADMIN")
                .antMatchers("/restricted/userhomepage").authenticated()
                .antMatchers("/addholidayhr", "/restricted/hrhomepage", "/pendingusers"
                        , "/registrationdetailshr", "/registrationpersonaldetailshr", "/allusersdetails", "/approvependingvacancies", "/approve").hasRole("HR")
                .antMatchers("/restricted/afterlogin").authenticated()
                .antMatchers("/restricted/viewpersonaldetails").authenticated()
                .antMatchers("/registrationdetails").authenticated()
                .antMatchers("/adddayoff").authenticated()
                .antMatchers("/setcredits").authenticated()
                .antMatchers("/viewvacancies").authenticated()
                .antMatchers("/viewAllDaysOff").authenticated()
                .antMatchers("/restricted/viewcredis").authenticated()
                .antMatchers("/redirect").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/accesdenied")
                .loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/restricted/userhomepage", false)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/accesdenied");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Web resources
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/scripts/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
