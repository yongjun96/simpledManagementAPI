package co.kr.yongjun.restfulservice.controller;

import co.kr.yongjun.restfulservice.bean.User;
import co.kr.yongjun.restfulservice.dao.UserDaoService;
import co.kr.yongjun.restfulservice.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "user-controller", description = "일반 사용자를 위한 컨트롤러입니다.") // class의 대한 설명 (swagger)
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }


    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
         List<User> userList = service.findAll();

        if(userList == null){
            throw new UserNotFoundException(String.format("users not found"));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");

        FilterProvider filters = new SimpleFilterProvider().addFilter("userInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userList);

        mapping.setFilters(filters);

        return mapping;
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "ID를 찾지 못했습니다."),
            @ApiResponse(responseCode = "500", description = "서버오류"),
    })
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@Parameter(
            description = "사용자 ID", required = true, example = "1") @PathVariable Long id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //필터생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userInfo", filter);

        //EntityModel에 user 넣음
        EntityModel entityModel = EntityModel.of(user);

        // 현재 작업 중인 클레스에 retrieveAllUsers와 연결
        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("all-users")); // all-users -> http://localhost:8088/users

        //MappingJacksonValue에 entityModel를 넣어줌.
        MappingJacksonValue mapping = new MappingJacksonValue(entityModel);

        mapping.setFilters(filters);

        return mapping;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User userSave = service.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSave.getId())
                .toUri();


        return ResponseEntity.created(uri).build();
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){

        User deletedUser = service.deleteById(id);

        if(deletedUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // noContent 204
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/users/{user}/{id}")
    public ResponseEntity ModifiedUSer(@Valid @RequestBody User user, @PathVariable Long id){

        User modUser = service.modifiedUser(user, id);

        if(modUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
