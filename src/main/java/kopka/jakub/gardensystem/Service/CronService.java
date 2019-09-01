package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Repository.CronRepo;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CronService {

    @Autowired
    CronRepo cronRepo;

    @Autowired
    IrrigationService irrigationService;

    @Autowired
    IrrigationRepo irrigationRepo;

    public  void deleteById(Long id){
        cronRepo.deleteById(id);
    }

    public Cron getClosestCron() {
        Cron cron = DynamicSchedulerVersion2.getClosestTimeInCrone(cronRepo.findAll());
        System.out.println("--------------------- " + cron);
        return cron;
    }

    public void addCron(Cron cron) {
        cronRepo.save(cron);
    }

    public List<Cron> getAllCrons() {
        return cronRepo.findAll();
    }
}
