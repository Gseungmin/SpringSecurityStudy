package Study.SpringSecurity.security.authetication.userdetails;

import Study.SpringSecurity.domain.entity.Member;
import Study.SpringSecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService 구현
 * */
@Service
@RequiredArgsConstructor
public class FormMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * Id 검증 로직
     * 해당 Member 객체가 없을 시 UsernameNotFoundException 발생
     * 해당 Member 객체가 있을 시 User 인터페이스를 상속받은 객체 생성 및 반환
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB 연동하여 데이터 여부 확인
        Member member = memberRepository.findByUsername(username);

        //데이터가 존재하지 않다면 에러 발생
        if (member == null) {
            throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다.");
        }

        //Member 객체에 권한 부여
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole()));

        //Member 객체 및 권한 정보를 담는 객체 생성
        MemberContext memberContext = new MemberContext(member, roles);

        return memberContext;
    }
}
