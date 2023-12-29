package co.kr.yongjun.restfulservice.controller;

import co.kr.yongjun.restfulservice.bean.Post;
import co.kr.yongjun.restfulservice.bean.User;
import co.kr.yongjun.restfulservice.exception.UserNotFoundException;
import co.kr.yongjun.restfulservice.repository.PostRepository;
import co.kr.yongjun.restfulservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    //userRepository안에 있는 findAll이라는 (jpa에서 제공) 함수로 전체 데이터 조회
    @GetMapping("/users")
    public ResponseEntity retrieveAllUsers(){

        List<User> userList = userRepository.findAll();
        int count = userList.size();

        HashMap<String, Object> userListAndCount = new HashMap<>();
        userListAndCount.put("users", userList);
        userListAndCount.put("count", count);

        return ResponseEntity.ok(userListAndCount);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable Long id){
        //Optional로 존재 여부를 체크
        //findById함수는 기본적으로 제공되는 함수
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException("id-"+ id);
        }

        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("전체 유저검색"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("users/{id}")
    public void deleteUserById(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User saveUser = userRepository.save(user);

        //현재 생성된 데이터를 기준으로 사용자 id를 가져오고 URI를 이용해서 Http반환
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostById(@PathVariable Long id){
        //Optional로 존재 여부를 체크
        //findById함수는 기본적으로 제공되는 함수
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException("id-"+ id);
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody Post post){

        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw new UserNotFoundException("찾지 못한 id = "+ id);
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("id")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
