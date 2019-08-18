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
        Section section = new Section(0, "Desription", 15);
        sectionRepo.save(section);
        irrigation.addNewSection(section);
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
