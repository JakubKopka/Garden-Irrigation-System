package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Dto.CronDto;
import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Repository.CronRepo;
import kopka.jakub.gardensystem.Service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;


@Controller
@RequestMapping(value = "/cron")
public class CronController {

    @Autowired
    CronService cronService;

    @Autowired
    CronRepo cronRepo;


    @RequestMapping()
    public String time(Model model) {
        model.addAttribute("crons", cronRepo.findAll());
        CronDto cronDto = new CronDto();
        model.addAttribute("data", cronDto);
        return "cron";
    }

    @GetMapping(value = "/all")
    public List<Cron> getAllCrons() {
        return cronService.getAllCrons();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCron(@PathVariable(name = "id") Long id) {
        cronService.deleteById(id);
        return "redirect:/cron";
    }

    @PostMapping(value = "/add")
    public String addCron(@ModelAttribute CronDto cron) {
        cronService.addCron(cron);
        return "redirect:/cron";
    }

    @RequestMapping(value = "/closest", method = RequestMethod.GET)
    public Cron getClosestCron() {
        return cronService.getClosestCron();
    }
}
