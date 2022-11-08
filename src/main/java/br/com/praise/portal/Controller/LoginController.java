package br.com.praise.portal.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import br.com.praise.portal.Enum.Roles;
import br.com.praise.portal.model.Role;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.com.praise.portal.service.CookieService;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

    @Autowired
    private UserRepository repo;
    
    @GetMapping("/login")
    public String index(){
        return "login/login";
    }

//    @PostMapping("/login")
//    public String logar(Model model, User adminParam, String username, String password, String lembrar, HttpServletResponse response) throws IOException {
//        Optional<User> admin = this.repo.name(adminParam.getUsername());
//        if(admin.isPresent()) {
//            int tempLogIn = (60*60);
//            if(lembrar != null) tempLogIn = (60*60*24*30);
//            CookieService.setCookie(response, "userID", String.valueOf(admin.get(0).getID()),tempLogIn);
//            CookieService.setCookie(response, "nameUser", String.valueOf(admin.get(0).getUsername()),tempLogIn);
//            return "redirect:/";
//        }
//        return "login/login";
//    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) throws IOException{
        CookieService.setCookie(response, "userID", "", 0);
        CookieService.setCookie(response, "nameUser", "", 0);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @PostMapping("/register/new")
    public String newRegister(User user, String confirmPassword, String password, Model model) {
        Optional<User> login = repo.login(user.getEmail());
        if(login.isPresent()) {
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
            Role role = new Role();
            role.setRole(Roles.CLIENTE);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            repo.insert(user);
        }
        return "redirect:/login";
    }
}
