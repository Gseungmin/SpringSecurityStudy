package Study.SpringSecurity.domain.entity;

import Study.SpringSecurity.domain.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;
    private String password;
    private String age;
    private String role;

    public Member(MemberDto memberDto) {
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
        this.age = memberDto.getAge();
        this.role = memberDto.getRole();
    }
}
