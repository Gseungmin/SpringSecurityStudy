package Study.SpringSecurity.domain.entity;

import Study.SpringSecurity.domain.dto.MemberDto;
import lombok.*;

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
    private String email;
    private String role;

    public Member(MemberDto memberDto) {
        this.username = memberDto.getUsername();
        this.password = memberDto.getPassword();
        this.email = memberDto.getEmail();
        this.role = memberDto.getRole();
    }

    public Member(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Member update(String username) {
        this.username = username;
        return this;
    }
}
