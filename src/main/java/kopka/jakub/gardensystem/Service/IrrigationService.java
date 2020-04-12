package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IrrigationService {

    @Autowired
    IrrigationRepo irrigationRepo;


    public Irrigation getIrrigation() {
        List<Irrigation> irrigations = (List<Irrigation>) irrigationRepo.findAll();
        return irrigations.get(0);
    }

    public void addCron(Cron cron) {
        Irrigation irrigation = getIrrigation();
        String[] cronsSplit = cron.getCron().split(":");
        cron.setCron("0 " + cronsSplit[1] + " " + cronsSplit[0] + " * * ?");
//        irrigation.addCron(cron);
        irrigationRepo.save(irrigation);
    }




//    public void addCron(String cron) {
//        Irrigation irrigation = getIrrigation();
//        irrigation.addCron(cron);
//        irrigationRepo.save(irrigation);
//    }
}
