package br.com.praise.portal.Controller;

import br.com.praise.portal.Enum.Permissions;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import br.com.praise.portal.service.UserDetailsServiceImpl;
import br.com.praise.portal.service.segurity.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/portal/admin")
public class AdminMiddleware {

    @Autowired
    private UserRepository userRepo;

    private UserDetailsServiceImpl userDetails;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminMiddleware(UserDetailsServiceImpl userDetails, PasswordEncoder passwordEncoder) {
        this.userDetails = userDetails;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = {"/users/list"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);

        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "admin/list";
    }

    @RequestMapping(value = {"/users/add"}, method = RequestMethod.GET)
    public String adminAddGet(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);
        model.addAttribute("perms", Permissions.values());
        return "admin/user_add";
    }

    @RequestMapping(value = {"/users/add"}, method = RequestMethod.POST)
    public String adminAdd(User user, @RequestParam("permissions") List<Permissions> permissions) {
        if (userRepo.login(user.getEmail()).isEmpty()) {
            if (permissions.contains(Permissions.ADMIN)) user.setKey("admin");
            user.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
            user.addPermissions(permissions);
            userRepo.insert(user);
        }
        return "redirect:/portal/admin/users/list";
    }

    @RequestMapping(value = {"/users/config/{idTarget}"}, method = RequestMethod.GET)
    public String config(@PathVariable String idTarget, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);
        User target = userRepo.findFirstByID(idTarget);
        model.addAttribute("target", target);
        model.addAttribute("permissions", Permissions.values());
        return "admin/config";
    }

    @RequestMapping(value = {"/users/update/{idTarget}"}, method = RequestMethod.POST)
    public String adminUpdate(@PathVariable String idTarget,
                              String firstname,
                              String lastname,
                              String email,
                              String key,
                              String password,
                              @RequestParam("permissions") List<Permissions> permissions) {
        User user = userRepo.findFirstByID(idTarget);
        if(user != null) {
            if(!firstname.isEmpty()) {
                user.setFirstname(firstname);
            }
            if (!lastname.isEmpty()) {
                user.setLastname(lastname);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (!password.isEmpty()) {
                String passwordEncrypt = SecurityConfiguration.passwordEncoder().encode(password);
                user.setPassword(passwordEncrypt);
            }
            if (!key.isEmpty()) {
                user.setKey(key);
            }
            user.addPermissions(permissions);
            userRepo.save(user);
        }
        return "redirect:/portal/admin/users/list";
    }

    @RequestMapping(value = {"/users/deleted/{idTarget}"}, method = RequestMethod.GET)
    public String deleted(@PathVariable String idTarget, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        User user = userRepo.findFirstByID(idTarget);
        if (user != null)
            userRepo.delete(user);
        return "redirect:/portal/admin/users/list";
    }

}
