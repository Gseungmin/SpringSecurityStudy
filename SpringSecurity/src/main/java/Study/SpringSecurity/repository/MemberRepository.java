package Study.SpringSecurity.repository;


import Study.SpringSecurity.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);
}
