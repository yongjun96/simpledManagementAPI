package co.kr.yongjun.restfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data   // setter, getter 같이 생성됨
@AllArgsConstructor     // 생성자를 자동으로 추가
public class HelloWorldBean {

    private final String message;   //final은 setter안만듦

//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}
