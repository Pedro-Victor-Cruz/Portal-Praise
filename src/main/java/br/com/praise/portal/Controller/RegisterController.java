package br.com.praise.portal.Controller;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @PostMapping("/newregister")
    public String newRegister(User user, String confirmPassword, String password, Model model) {
        List<User> login = repository.login(user.getEmail());
        if(login.size() > 0) {
            model.addAttribute("error", "Email já cadastrado em nosso sistema!");
            return "redirect:/register";
        } else {
            if(!password.equals(confirmPassword)) {
                model.addAttribute("error", "Erro! Sua senha é diferente da confirmação!");
                return "redirect:/register";
            } else if(user.getPassword().length() < 8) {
                model.addAttribute("error", "Erro! Sua senha precisa ter no minimo 8 caracters!");
                return "redirect:/register";
            }
            String key = user.getKey().replace(" ", "-");
            user.setKey(key);
            repository.insert(user);
        }
        return "redirect:/login";
    }
}
