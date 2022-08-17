package Study.SpringSecurity.controller.member;


import Study.SpringSecurity.domain.dto.MemberDto;
import Study.SpringSecurity.domain.entity.Member;
import Study.SpringSecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AddMemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	public String createMemberForm(MemberDto memberDto) {
		return "register";
	}

	@PostMapping("/users")
	public String createMember(MemberDto memberDto) {
		Member member = new Member(memberDto);
		//패스워드 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberService.join(member);
		return "redirect:/";
	}
}
