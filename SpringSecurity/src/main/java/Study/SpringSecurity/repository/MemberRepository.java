package Study.SpringSecurity.repository;


import Study.SpringSecurity.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String loginId);

    Optional<Member> findByEmail(String email);
}
