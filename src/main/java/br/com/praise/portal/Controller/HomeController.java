package br.com.praise.portal.Controller;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class HomeController {

    @Autowired
    private UserRepository repo;
    
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = repo.findFirstByID(id);
        model.addAttribute("user", user);
        return "home/index";
    }
}
