package com.workintech.twitter.config;


import com.workintech.twitter.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {


    /*    Bu demek oluyor ki:
    BCryptPasswordEncoder sınıfından bir nesne üretiliyor
    Bu nesne Spring context’e bir bean olarak ekleniyor
    Artık bu nesneyi her yerde kullanabilirsin:*/
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}


/*    Bu yapı sayesinde:
    Bir AuthenticationManager nesnesi üretip Spring'e veriyorsun.
    Bu yapı Spring Security'nin kimlik doğrulamada kullanacağı asıl mekanizmayı oluşturuyor.
    Spring başka yerlerde AuthenticationManager'a ihtiyaç duyarsa, bu tanımlanan yapı kullanılacak.*/

@Bean //aynı userDetailService ve encoderı kullanması için bu @bean yazılıyor
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
    DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
}


@Bean //SecurityFilterChain aplikasyonu secur ederken bizim ekleyeceklerimizi ekleyerek eklediklerimizi
// dikakte alarak işlem yapıyor. Çünkü kendi security işlemlerimizde oluyor
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity
            .csrf(csrf->csrf.disable())//farklı domainlerden istek atmamızı engelliyor o
            // yüzden disable yapıyoruz
            //kısa hali  csrf.(AbstractHttpConfigurer::disable);
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth->{
                auth.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
                auth.requestMatchers(HttpMethod.GET,"/users/**").permitAll();
                auth.requestMatchers(HttpMethod.GET,"/tweet/**").permitAll();
/*               auth.requestMatchers(HttpMethod.GET,"/account").hasAuthority("ADMIN");
                auth.requestMatchers(HttpMethod.GET, "/account/{id}").hasAnyAuthority("ADMIN", "USER");
                //auth.requestMatchers(HttpMethod.GET,"/account/{id}").hasAuthority("ADMIN");
                //auth.requestMatchers(HttpMethod.GET,"/account/{id}").hasAuthority("USER");
                auth.requestMatchers(HttpMethod.POST,"/account").hasAuthority("ADMIN");
                auth.requestMatchers(HttpMethod.PUT,"/account").hasAuthority("ADMIN");
                auth.requestMatchers(HttpMethod.DELETE,"/account").hasAuthority("ADMIN");*/
                auth.anyRequest().authenticated();
            })
            //.formLogin(Customizer.withDefaults())//Login olma için mevcut arayüz var onu kullanmak istiyorsan
            .httpBasic(Customizer.withDefaults())//nereden login yapabileceğimiz gösteriyor
            .build();
}
}
