package Study.SpringSecurity.security.authetication.oauth;

import Study.SpringSecurity.domain.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;

    public SessionUser(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
    }
}
