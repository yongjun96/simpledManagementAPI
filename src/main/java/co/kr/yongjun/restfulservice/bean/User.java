package co.kr.yongjun.restfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFilter("userInfo")
@JsonIgnoreProperties(value = {"password", "ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name = "USERS")
public class User {

    @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성됩니다.")
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Schema(title = "사용자 이름", description = "사용자 이름을 입력합니다.")
    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @Column(name = "NAME")
    private String name;

    @Schema(title = "사용자 등록일", description = "사용자 등록일을 입력합니다. 미입력 시 현재 날짜가 등록됩니다.")
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    //@JsonIgnore
    @Schema(title = "사용자 비밀번호", description = "사용자의 비밀번호를 입력합니다.")
    private String password;

    //@JsonIgnore
    @Schema(title = "사용자 주민번호", description = "사용자의 주민번호를 입력합니다.")
    private String ssn;

    @Schema(title = "사용자 관리타입", description = "사용자의 관리타입을 입력합니다.")
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(Long id, String name, Date joinDate, String password, String ssn, UserType userType) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
        this.userType = userType;
    }
}
