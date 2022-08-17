package Study.SpringSecurity.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    private String loginId;
    private String password;
    private String age;
    private String role;
}