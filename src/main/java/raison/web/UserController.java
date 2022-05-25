package raison.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raison.domain.User;
import raison.domain.UserRepository;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository = new UserRepository();

    @PostMapping("/users")
    public String createUser(@ModelAttribute UserDataCarrier userDataCarrier, Model model) {
        System.out.println("userDataCarrier = " + userDataCarrier);
        users.add(userDataCarrier.toUser());
        userRepository.save(userDataCarrier.toUser());
        return "redirect:/users";
    }

    @GetMapping(path = "/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping(path = "/users/form")
    public String userForm(Model model) {
        model.addAttribute("userDataCarrier", new UserDataCarrier());
        return "user/form";
    }

    @GetMapping(path="/users/{id}/form")
    public String updateUserForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("userDataCarrier", new UserDataCarrier());
        model.addAttribute("user", userRepository.findById(id));

        return "user/updateForm";
    }

    @PostMapping(path = "/users/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam(value = "userId") String userId,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "email") String email) {
        userRepository.update(id, userId, password, name, email);
        return "redirect:/users";
    }
}
