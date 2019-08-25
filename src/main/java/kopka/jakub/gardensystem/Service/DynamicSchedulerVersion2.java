package kopka.jakub.gardensystem.Service;

//import kopka.jakub.gardensystem.Repository.ConfigRepo;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Alternative version for DynamicScheduler
 * This one should support everything the basic dynamic scheduler does,
 * and on top of it, you can cancel and re-activate the scheduler.
 */
@Service
public class DynamicSchedulerVersion2 implements SchedulingConfigurer {

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    Action action;

    private static Logger LOGGER = LoggerFactory.getLogger(DynamicScheduler.class);

    public ScheduledTaskRegistrar scheduledTaskRegistrar;

    ScheduledFuture future;
    Map<ScheduledFuture, Boolean> futureMap = new HashMap<>();

    @Bean
    public TaskScheduler poolScheduler2() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(1);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler2());
        }

        CronTrigger croneTrigger = new CronTrigger(irrigationRepo.findAll().get(0).getCron(), TimeZone.getDefault());
        future = taskRegistrar.getScheduler().schedule(() -> scheduleCron(irrigationRepo.findAll().get(0)), croneTrigger);
    }

    public void scheduleCron(Irrigation irrigation) {
        List<Section> sections = irrigation.getSections();
        System.out.println(irrigation);
        LOGGER.info("WYKONANO ZAPLANOWANY TASK  \t cron->{}", irrigation.getCron());

        int timeInMinutes = 5;
        int kropelkowe = 5;

        for(int i = 1; i<=6; i++){
            if(!irrigationRepo.findAll().get(0).isActive()) {
                break;
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++ Wykonano pętle nr " + i);
            try {
//                            action.openSequence(n);
                if(i == 6){
                    TimeUnit.SECONDS.sleep(kropelkowe);
                } else {
                    TimeUnit.SECONDS.sleep(timeInMinutes);
                }
//                            action.closeSequence(n);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//        IntStream.iterate(1, n->n+1)
//                .limit(6)
//                .forEach(n->{
//                    if(irrigationRepo.findAll().get(0).isActive()) {
//
//                    }
//                        System.out.println("Wykonano pętle nr " + n);
//                        try {
////                            action.openSequence(n);
//                            if(n == 6){
//                                TimeUnit.SECONDS.sleep(kropelkowe);
//                            } else {
//                                TimeUnit.SECONDS.sleep(timeInMinutes);
//                            }
////                            action.closeSequence(n);
//                            TimeUnit.SECONDS.sleep(5);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                });

    }


    /**
     * @param mayInterruptIfRunning {@code true} if the thread executing this task
     *                              should be interrupted; otherwise, in-progress tasks are allowed to complete
     */
    public void cancelFuture(boolean mayInterruptIfRunning, ScheduledFuture future) {
        LOGGER.info("Cancelling a future");
        future.cancel(mayInterruptIfRunning); // set to false if you want the running task to be completed first.
        futureMap.put(future, false);
    }

    public void activateFuture(ScheduledFuture future) {
        LOGGER.info("Re-Activating a future");
        futureMap.put(future, true);
        configureTasks(scheduledTaskRegistrar);
    }

    public void cancel() {
        cancelFuture(true, future);
    }

    public void activate() {
        activateFuture(future);
    }

}