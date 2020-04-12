package kopka.jakub.gardensystem.Controller;

import com.pi4j.io.gpio.*;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.GPIO.GPIOStatus;
import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.CronRepo;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import kopka.jakub.gardensystem.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class MainController {

    @Autowired
    MainService mainService;

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    CronRepo cronRepo;

    @Autowired
    SectionRepo sectionRepo;

    @Autowired
    SectionService sectionService;

    @Autowired
    CronService cronService;

    @Autowired
    RaspberryService raspberryService;


    @RequestMapping("/")
    public String home(Model model) {
        if (cronService.getAllCrons().size() > 0) {
            model.addAttribute("hour", cronService.getClosestCron().getTime());

        } else {
            model.addAttribute("hour", "none");
        }
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("perDay", cronService.getAllCrons().size());
        model.addAttribute("crons", cronService.getAllCrons());
        return "index";
    }


    @RequestMapping("/manual")
    public String manual(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("pins", raspberryService.getPinsInfo());
        return "manual";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


}
