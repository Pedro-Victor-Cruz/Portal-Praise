package br.com.praise.portal.Controller;

import br.com.praise.portal.model.Client;
import br.com.praise.portal.model.Device;
import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.client.ClientsRepository;
import br.com.praise.portal.repository.device.DevicesRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/portal/device")
public class DeviceMiddleware {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DevicesRepository deviceRepo;

    @Autowired
    private ClientsRepository clientsRepo;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String device(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String id = CookieService.getCookie(request, "userID");
        User user = userRepo.findFirstByID(id);
        model.addAttribute("user", user);

        List<Device> devices = user.getKey().equals("admin") ? deviceRepo.findAll() : deviceRepo.findAllDevicesByKey(user.getKey());
        model.addAttribute("dispositivos", devices);
        return "devices/index";
    }

    @RequestMapping(value = {"/activateOrInactivate/{id}"}, method = RequestMethod.GET)
    public String deviceStatus(@PathVariable String id) {
        Device device = deviceRepo.findFirstByID(id);
        Optional<Client> client = clientsRepo.findByKey(device.getChave());
        if(client.isPresent()) {
            int ativos = deviceRepo.findAllDevicesByStatus(true).size();
            boolean status = device.isAtivo();
            if(!status) {
                if(ativos < client.get().getLicencas()) {
                    device.setAtivo(true);
                    deviceRepo.save(device);
                }
            } else {
                device.setAtivo(false);
                deviceRepo.save(device);
            }
        }
        return "redirect:/portal/device/list";
    }
}
