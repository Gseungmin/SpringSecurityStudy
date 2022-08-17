package Study.SpringSecurity.controller.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/users")
    public String addMemberForm() {
        return "register";
    }
}
