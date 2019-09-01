package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Repository.CronRepo;
import kopka.jakub.gardensystem.Service.CronService;
import kopka.jakub.gardensystem.Service.DynamicSchedulerVersion2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/cron")
public class CronController {

    @Autowired
    CronService cronService;

    @Autowired
    CronRepo cronRepo;

    @Autowired
    DynamicSchedulerVersion2 dynamicSchedulerVersion2;


    @GetMapping(value = "/all")
    public List<Cron> getAllCrons(){
        return cronService.getAllCrons();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteCron(@PathVariable (name = "id") Long id) {
        cronService.deleteById(id);
        dynamicSchedulerVersion2.cancel();
        dynamicSchedulerVersion2.activate();
    }

    @PostMapping(value = "/add")
    public void addCron(@RequestBody Cron cron){
        System.out.println("-=================== "+ cron);
        cronService.addCron(cron);
        dynamicSchedulerVersion2.cancel();
        dynamicSchedulerVersion2.activate();
    }

    @RequestMapping(value = "/closest", method = RequestMethod.GET)
    public Cron getClosestCron() {
       return cronService.getClosestCron();
    }
}
