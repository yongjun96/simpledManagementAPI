package co.kr.yongjun.restfulservice.controller;

import co.kr.yongjun.restfulservice.bean.User;
import co.kr.yongjun.restfulservice.bean.UserV2;
import co.kr.yongjun.restfulservice.dao.AdminUserDaoService;
import co.kr.yongjun.restfulservice.dao.UserDaoService;
import co.kr.yongjun.restfulservice.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")   //현제 class의 URI 제일 앞에 들어가게 됨
public class AdminUserController {

    private AdminUserDaoService service;

    public AdminUserController(AdminUserDaoService service){
        this.service = service;
    }


    // 버전관리 1번 : @GetMapping("/v1/users/{id}")
    // 버전관리 2번 : @GetMapping(value = "/users/{id}", params = "version=1")
    // 버전관리 3번 : @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveAdminUser(@PathVariable Long id){
        User user = service.adminFindOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        //BeanUtils.copyProperties(user, admin); --> user에 저장되어 있는 값이 admin에 그대로 복사됨

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "password", "userType");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);

        mapping.setFilters(filterProvider);

        return mapping;
    }


    // 버전관리 1번 : @GetMapping("/v2/users/{id}")
    // 버전관리 2번 : @GetMapping(value = "/users/{id}", params = "version=2")
    // 버전관리 3번 : @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    // 번전관리 4번 : @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json") --> 마임타입
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveAdminUserV2(@PathVariable Long id){
        User user = service.adminFindOne(id);

        UserV2 userV2 = new UserV2();

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }else{
            BeanUtils.copyProperties(user, userV2);  // --> user에 저장되어 있는 값이 admin에 그대로 복사됨
            userV2.setGrade("VIP");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "password", "userType", "grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);

        mapping.setFilters(filterProvider);

        return mapping;
    }


    @GetMapping("/users")
    public MappingJacksonValue retrieveAllAdminUser(){
        List<User> userList = service.adminFindAll();


            if (userList == null) {
                throw new UserNotFoundException(String.format("users not found"));
            }
            //BeanUtils.copyProperties(user, admin); --> user에 저장되어 있는 값이 admin에 그대로 복사됨

            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "password", "userType");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfo", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(userList);

            mapping.setFilters(filterProvider);

        return mapping;
    }


}
