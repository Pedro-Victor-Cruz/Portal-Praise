package br.com.praise.portal.Controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.praise.portal.service.CookieService;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index(){
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) throws IOException{
        CookieService.setCookie(response, "userID", "", 0);
        CookieService.setCookie(response, "nameUser", "", 0);
        return "redirect:/login";
    }

}
