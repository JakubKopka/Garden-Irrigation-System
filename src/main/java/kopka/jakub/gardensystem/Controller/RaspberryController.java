package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.GPIO.GPIOStatus;
import kopka.jakub.gardensystem.Service.DynamicSchedulerVersion2;
import kopka.jakub.gardensystem.Service.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("raspberry")
public class RaspberryController {

    @Autowired
    RaspberryService raspberryService;


    @RequestMapping("/open/{pin}")
    public String open(@PathVariable int pin) throws InterruptedException {
        raspberryService.open(pin);
        return "redirect:/manual";
    }

    @RequestMapping("/close/{pin}")
    public String close(@PathVariable int pin) {
        raspberryService.close(pin);
        return "redirect:/manual";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start() {
        raspberryService.start();
        return "redirect:/manual";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public List<GPIOStatus> getGPIOInfo() {
        return raspberryService.gpioStatus();
    }


    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stop() {
        raspberryService.stop();
        return "redirect:/manual";
    }
}
