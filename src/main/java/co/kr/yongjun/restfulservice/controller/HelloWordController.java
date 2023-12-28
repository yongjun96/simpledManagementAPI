package co.kr.yongjun.restfulservice.controller;

import co.kr.yongjun.restfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWordController {

    private MessageSource messageSource;


    // 처음 인스턴스가 스프링 컨텍스트에서 기동이 될 때. 해당하는 인스턴스들을 미리 만들어 놓고 메모리에 등록함.
    // 미리 등록된 다른 빈들을 가지고 와서 현재의 클레스에서 사용할 수 있도록 객체를 생성하지 않고 참조하는 형태로 주입
    public HelloWordController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    // GET
    // URI - /hello-world
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "helloWorld";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("hello world, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale){

        return messageSource.getMessage("greeting.message", null, locale);
    }


}
