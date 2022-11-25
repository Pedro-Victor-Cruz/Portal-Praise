package br.com.praise.portal.Controller;

import br.com.praise.portal.model.Client;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.client.ClientsRepository;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping("/portal/admin/client")
public class ClientMiddleware {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ClientsRepository clientsRepo;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);

        model.addAttribute("clients", clientsRepo.findAll());
        return "client/list";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);


        return "client/cadastrar";
    }

    @RequestMapping(value = {"/activateOrInactivate/{id}"}, method = RequestMethod.GET)
    public String activateOrInactivate(@PathVariable String id) {
        Optional<Client> client = clientsRepo.findById(id);
        boolean status = client.get().isAtivo();
        client.get().setAtivo(!status);
        clientsRepo.save(client.get());
        return "redirect:/portal/admin/client/list";
    }
}
