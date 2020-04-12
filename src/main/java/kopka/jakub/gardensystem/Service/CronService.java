package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Dto.CronDto;
import kopka.jakub.gardensystem.Mapper;
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
public class CronService {

    @Autowired
    CronRepo cronRepo;

    @Autowired
    IrrigationService irrigationService;

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    DynamicSchedulerVersion2 dynamicSchedulerVersion2;

    @Autowired
    Mapper mapper;


    public void deleteById(Long id){
        System.out.println("WEszlo");
        cronRepo.deleteById(id);
        try{
            dynamicSchedulerVersion2.cancel();
            dynamicSchedulerVersion2.activate();
        }catch (Exception ex){

        }
    }

    public Cron getClosestCron() {
        Cron cron = DynamicSchedulerVersion2.getClosestTimeInCrone((List<Cron>) cronRepo.findAll());
        return cron;
    }

    public void addCron(CronDto cron) {

        Cron cron1 = new Cron();
        cron1.setActive(true);
        Irrigation irrigation = irrigationRepo.findAll().get(0);;
        cron1.setIrrigation(irrigation);
        cron1.setCron(mapper.mapStringToCron(cron.getData()));
        cron1.setTime(cron.getData());
        cronRepo.save(cron1);

        try {
            dynamicSchedulerVersion2.cancel();
            dynamicSchedulerVersion2.activate();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

    }

    public List<Cron> getAllCrons() {
        return (List<Cron>) cronRepo.findAll();
    }
}
