package Study.SpringSecurity.security.authetication.userdetails;

import Study.SpringSecurity.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Member 객체와 객체의 권한을 감싸는 클래스로 인증 토큰 객체를 만들기 위해 전달
 * */
@Getter
public class MemberContext extends User {

    private final Member member;

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.member = member;
    }
}
