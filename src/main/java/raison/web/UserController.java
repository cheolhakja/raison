package raison.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raison.domain.User;
import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute UserDataCarrier userDataCarrier, Model model) {
        System.out.println("userDataCarrier = " + userDataCarrier);
        users.add(userDataCarrier.toUser());
        return "redirect:/user/list";
    }

    @GetMapping(path = "/user/list")
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping(path = "/user/create")
    public String userForm(Model model) {
        model.addAttribute("userDataCarrier", new UserDataCarrier());
        return "user/form";
    }
}
