package raison.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raison.domain.User;
import raison.domain.UserRepository;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/users/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("loginDataCarrier", new LoginDataCarrier());
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute LoginDataCarrier loginDataCarrier, HttpSession session) {
        List<User> userList = userRepository.findByUserId(loginDataCarrier.getUserId());

        if(userList.size()>=2) {
            throw new IllegalArgumentException("같은 userId로 두명이상의 회원이 등록되어있음");
            //로그만 찍는 예외처리 안됨. 세밀한 예외처리
        }

        if(userList.isEmpty()) {
            throw new IllegalArgumentException("해당 userId로 등록된 회원이 없음");
        }

        User user = userList.get(0);

        if(!user.getPassword().equals(loginDataCarrier.getPassword())) {
            throw new IllegalArgumentException("비밀번호 오류");
        }

        System.out.println("session = " + session);
        session.setAttribute("user", user);



        return "redirect:/";
    }
}
