package br.com.praise.portal.Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.praise.portal.model.Device;
import br.com.praise.portal.repository.device.DevicesRepository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeviceController {

    @Autowired
    private DevicesRepository deviceRepo;

    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/devices")
    public String device(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);

        List<Device> devices = deviceRepo.findAllDevicesByKey(user.getKey());
        devices.forEach(System.out::println);
        model.addAttribute("dispositivos", devices);
        return "devices/index";
    }

    @GetMapping("/devices/activateOrInactivate/{id}")
    public String deviceStatus(@PathVariable String id) {
        Device device = deviceRepo.findFirstByID(id);
        boolean status = device.isAtivo();
        device.setAtivo(!status);
        deviceRepo.save(device);
        return "redirect:/devices";
    }
}
