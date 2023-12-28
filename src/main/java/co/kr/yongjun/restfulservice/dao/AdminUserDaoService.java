package co.kr.yongjun.restfulservice.dao;

import co.kr.yongjun.restfulservice.bean.User;
import co.kr.yongjun.restfulservice.bean.UserType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AdminUserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Long userCount = 3L;

    static {
        users.add(new User(1L, "김용준", new Date(), "password1", "010-1254-4587", UserType.ADMIN));
        users.add(new User(2L, "김정옥", new Date(), "password2", "010-2254-4587", UserType.ADMIN));
        users.add(new User(3L, "김예현", new Date(), "password3", "010-3254-4587", UserType.ADMIN));
    }

    public List<User> adminFindAll(){

        return users;
    }


    public User adminFindOne(Long id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return  null;
    }

}
