package br.com.praise.portal.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.praise.portal.service.CookieService;

@Controller
public class LoginController {

    @Autowired
    private UserRepository repo;
    
    @GetMapping("/login")
    public String index(){
        return "login/login";
    }

    @PostMapping("/logar")
    public String logar(Model model, User adminParam, String email, String password, String lembrar, HttpServletResponse response) throws IOException {
        List<User> admin = this.repo.login(adminParam.getEmail());
        if(admin.size() > 0) {
            int tempLogIn = (60*60);
            if(lembrar != null) tempLogIn = (60*60*24*30);
            CookieService.setCookie(response, "userID", String.valueOf(admin.get(0).getID()),tempLogIn);
            CookieService.setCookie(response, "nameUser", String.valueOf(admin.get(0).getName()),tempLogIn);
            return "redirect:/";
        }
        model.addAttribute("erro", "Usuário ou senha inválido!");
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) throws IOException{
        CookieService.setCookie(response, "userID", "", 0);
        CookieService.setCookie(response, "nameUser", "", 0);
        return "redirect:/login";
    }
}
