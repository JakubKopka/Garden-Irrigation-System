package kopka.jakub.gardensystem.Service;


//import kopka.jakub.gardensystem.Model.ConfigItem;
//import kopka.jakub.gardensystem.Repository.ConfigRepo;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.CronRepo;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class DynamicScheduler implements SchedulingConfigurer {

    private static Logger LOGGER = LoggerFactory.getLogger(DynamicScheduler.class);

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    SectionRepo sectionRepo;

    @Autowired
    CronRepo cronRepo;


    @PostConstruct
    public void initDatabase() {
//        Irrigation irrigation = new Irrigation();
//        Cron cron = new Cron("0 0 6 * * ?", irrigation);
//        Section section = new Section(1, "Statyczne przód lewo góra", 20, irrigation);
//
//        irrigationRepo.save(irrigation);
//        cronRepo.save(cron);
//        sectionRepo.save(section);
//        irrigationRepo.deleteAll();
//        sectionRepo.deleteAll();
//        cronRepo.deleteAll();
//
//        Irrigation irrigation = new Irrigation();
//        irrigationRepo.save(irrigation);
//
//
//         Cron cron = new Cron("0 0 20 * * ?", irrigation);
//         irrigation.addNewCron(cron);
//         cronRepo.save(cron);
//         irrigationRepo.save(irrigation);
////
////
////
//        Section section1 = new Section(1, "Statyczne przód lewo góra", 20, irrigation);
//        Section section2 = new Section(2, "Statyczne przód lewo dół", 20, irrigation);
//        Section section3 = new Section(3, "Statyczne przód prawo góra", 20, irrigation);
//        Section section4 = new Section(4, "Statyczne przód prawo góra", 20, irrigation);
//        Section section5 = new Section(5, "Turbonowe przód", 25, irrigation);
//        Section section6 = new Section(6, "Kropelkowe przód", 25, irrigation);
//
//
//
//        List<Section> s = Arrays.asList(section1, section2,section3,  section4, section5, section6);
//        sectionRepo.saveAll(s);
//        irrigationRepo.save(irrigation);
//
//        cronRepo.save(cron);
//        sectionRepo.saveAll(s);
//        cronRepo.save(cron);


    }

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(1);
        scheduler.initialize();
        return scheduler;
    }


    // We can have multiple tasks inside the same registrar as we can see below.
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(poolScheduler());

    }


}
