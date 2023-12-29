package co.kr.yongjun.restfulservice.repository;

import co.kr.yongjun.restfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


//구현하거나 선언시킬 내용은 없음
//기존에 만들어져 있던 Repository를 사용할 수 있도록 상속만 잘 시켜주면 됨
//JpaRepository를 상속받고 User라는 객체와 PK로 사용하는 타입을 받음
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
