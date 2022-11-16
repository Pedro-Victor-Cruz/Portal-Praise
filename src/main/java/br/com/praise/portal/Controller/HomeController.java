package br.com.praise.portal.Controller;

import br.com.praise.portal.model.Device;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.device.DevicesRepository;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/portal")
public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DevicesRepository deviceRepo;

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);
        return "home/index";
    }

    @RequestMapping(value = {"/devices"}, method = RequestMethod.GET)
    public String device(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);

        List<Device> devices = user.getKey().equals("admin") ? deviceRepo.findAll() : deviceRepo.findAllDevicesByKey(user.getKey());
        model.addAttribute("dispositivos", devices);
        return "devices/index";
    }

    @RequestMapping(value = {"/devices/activateOrInactivate/{id}"}, method = RequestMethod.GET)
    public String deviceStatus(@PathVariable String id) {
        Device device = deviceRepo.findFirstByID(id);
        boolean status = device.isAtivo();
        device.setAtivo(!status);
        deviceRepo.save(device);
        return "redirect:/portal/devices";
    }

}
