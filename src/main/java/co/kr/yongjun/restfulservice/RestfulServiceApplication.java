package co.kr.yongjun.restfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@SpringBootApplication
public class RestfulServiceApplication {

    public static void main(String[] args) {
//        ApplicationContext ac =  SpringApplication.run(RestfulServiceApplication.class, args);
//        String[] allBeanNames = ac.getBeanDefinitionNames();
//
//        for (String allBeanName : allBeanNames) {
//            System.out.println(allBeanName);
//        }

        SpringApplication.run(RestfulServiceApplication.class, args);

    }

    /**
     * 스프링이 시작될 때 LocaleResolver가 메모리에 빈으로 등록
     */
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

}
