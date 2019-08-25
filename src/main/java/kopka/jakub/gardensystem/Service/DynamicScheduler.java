package kopka.jakub.gardensystem.Service;


//import kopka.jakub.gardensystem.Model.ConfigItem;
//import kopka.jakub.gardensystem.Repository.ConfigRepo;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DynamicScheduler implements SchedulingConfigurer {

    private static Logger LOGGER = LoggerFactory.getLogger(DynamicScheduler.class);

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    SectionRepo sectionRepo;


    @PostConstruct
    public void initDatabase() {


        Irrigation irrigation = new Irrigation("0/5 * * * * ?");
        Section section1 = new Section(1, "Statyczne przód L1", 15);
//        Section section2 = new Section(2, "Statyczne przód L2", 15);
//
//        Section section3 = new Section(3, "Statyczne przód P1", 15);
//        Section section4 = new Section(4, "Statyczne przód P2", 15);
//        Section section5 = new Section(5, "Turbinowe przód", 15);
//
//        Section section6 = new Section(6, "Kropelkowe przód", 20);
        sectionRepo.save(section1);
        irrigation.addNewSection(section1);
//        sectionRepo.save(section2);
//        irrigation.addNewSection(section2);
//        sectionRepo.save(section3);
//        irrigation.addNewSection(section3);
//        sectionRepo.save(section4);
//        irrigation.addNewSection(section4);
//        sectionRepo.save(section5);
//        irrigation.addNewSection(section5);
//        sectionRepo.save(section6);
//        irrigation.addNewSection(section6);
        irrigationRepo.save(irrigation);
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
