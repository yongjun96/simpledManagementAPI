package co.kr.yongjun.restfulservice.dao;

import co.kr.yongjun.restfulservice.bean.User;
import co.kr.yongjun.restfulservice.bean.UserType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Long userCount = 3L;

    static {
        users.add(new User(1L, "김용준", new Date(), "password1", "010-1254-4587", UserType.USER));
        users.add(new User(2L, "김정옥", new Date(), "password2", "010-2254-4587", UserType.USER));
        users.add(new User(3L, "김예현", new Date(), "password3", "010-3254-4587", UserType.USER));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }

        if(user.getJoinDate() == null){
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }


    public User findOne(Long id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return  null;
    }

    public User deleteById(Long id){

        for (User user : users) {

            if(user.getId() == id){
                users.remove(user);
                return user;
            }
        }
        return null;
    }

    public User modifiedUser(User user, Long id){

        for (User user1 : users) {
            if(user1.getId() == id){

                if(user.getName() != null) {
                    user1.setName(user.getName());
                }

                if(user.getJoinDate() != null){
                    user1.setJoinDate(user.getJoinDate());
                }
                return user1;
            }
        }
        return null;
    }


}
