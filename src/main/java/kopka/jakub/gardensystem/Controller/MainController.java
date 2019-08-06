package kopka.jakub.gardensystem.Controller;

import com.pi4j.io.gpio.*;
import kopka.jakub.gardensystem.GPIO.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class MainController {

    @Autowired
    Action action;

    @RequestMapping("/open")
    public String open() throws InterruptedException {
        System.out.println(action.openSequence(1).toString());
        return action.openSequence(1);
    }

    @RequestMapping("/close")
    public String close(){
        action.closeSequence(1);
        return action.closeSequence(1);
    }

    @RequestMapping(value = "/start/{pin}/{seconds}", method = RequestMethod.GET)
    public String start(@PathVariable int pin, @PathVariable int seconds) throws InterruptedException {
        String status = action.openSequence(pin);
        Thread.sleep(seconds*1000);
        action.closeSequence(pin);
        return "start " + pin + seconds;
    }
}
