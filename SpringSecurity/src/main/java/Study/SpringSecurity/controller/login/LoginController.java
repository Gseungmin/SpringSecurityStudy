package Study.SpringSecurity.controller.login;

import Study.SpringSecurity.domain.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model)
    {
        model.addAttribute("error" , error);
        model.addAttribute("exception" , exception);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().getClass().equals(Member.class)) {
            Member member = (Member) authentication.getPrincipal();
            model.addAttribute("username", member.getUsername());
        } else {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("username", oauth2User.getAttributes().get("email"));
        }
        model.addAttribute("exception", exception);
        return "member/denied";
    }
}
