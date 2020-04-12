package kopka.jakub.gardensystem.Service;

//import kopka.jakub.gardensystem.Repository.ConfigRepo;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.CronRepo;
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

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
public class DynamicSchedulerVersion2 implements SchedulingConfigurer {

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    Action action;

    @Autowired
    CronRepo cronRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSchedulerVersion2.class);

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
        List<Irrigation> irrigationList = (List<Irrigation>) irrigationRepo.findAll();
        List<Cron> cronList = (List<Cron>) cronRepo.findAll();

        Cron closestCrone = getClosestTimeInCrone(cronList);
        if(closestCrone != null){
            CronTrigger croneTrigger = new CronTrigger(closestCrone.getCron(), TimeZone.getDefault());
            future = taskRegistrar.getScheduler().schedule(() -> scheduleCron(irrigationList.get(0)), croneTrigger);
        }
    }


    public static Cron getClosestTimeInCrone(List<Cron> listCrone) {

        LocalTime now = LocalTime.now();
        LocalTime closest = null;
        boolean isAfter = false;
        Cron cron = null;
        for (int i = 0; i < listCrone.size(); i++) {
            String[] splitCroneTime = listCrone.get(i).getCron().split(" ");
            int cronh = Integer.parseInt(splitCroneTime[2]);
            int cronmin = Integer.parseInt(splitCroneTime[1]);
            int cronsec = Integer.parseInt(splitCroneTime[0]);
            LocalTime cronTime = LocalTime.of(cronh, cronmin, cronsec);

            if (closest == null) {
//                System.out.println(" closest == null");
                closest = cronTime;
                cron = listCrone.get(i);
//                System.out.println(" closest = " + cronTime);
            }
//            System.out.println("closest != null ");
//            System.out.println(now + " is Before " + cronTime + "?");
            if (now.isBefore(cronTime)) {
                isAfter = true;
//                System.out.println("true");
//                System.out.println(closest + ".isAfter( " + cronTime + ")?");
                if (closest.isAfter(cronTime)) {
//                    System.out.println("true");
                    closest = cronTime;
                    cron = listCrone.get(i);
//                    System.out.println("closest " + closest);
                }
                if (now.isAfter(closest)) {
                    closest = cronTime;
                    cron = listCrone.get(i);
                }
            } else if (!isAfter) {
//                System.out.println("isAfter == " + isAfter);
//                System.out.println(" !isAfter ");
                if (closest.isAfter(cronTime)) {
//                    LOGGER.info(closest + " isAfter(" + cronTime + ") === true");
                    closest = cronTime;
                    cron = listCrone.get(i);
//                    LOGGER.info("closest = " + closest);
                }
            }
//            LOGGER.info("********** Najbliższe nawadnianie będzie o godzinie: " + closest);

        }


        LOGGER.info("********** Najbliższe nawadnianie będzie o godzinie: " + cron.getTime());
        return cron;
    }

    public void scheduleCron(Irrigation irrigation) {
        LOGGER.info("WYKONANO ZAPLANOWANY TASK  \t cron->");
        List<Irrigation> irrigationList = (List<Irrigation>) irrigationRepo.findAll();
        List<Section> sectionList = irrigation.getSections();

//        System.out.println("==========="+ sectionList.size());
        for (int i = 0; i < sectionList.size(); i++) {
            if (!irrigation.isActive()) {
                LOGGER.info("Irrigation is NOT active");
                break;
            }
            LOGGER.info("+++++++++ Skecja nr " + (i + 1) + " jest otwarta");
            try {
                action.openSequence(sectionList.get(i).getSectionNumber());

                TimeUnit.MINUTES.sleep(sectionList.get(i).getDuration());

                action.closeSequence(sectionList.get(i).getSectionNumber());
                LOGGER.info("+++++++++ Skecja nr " + (i + 1) + " jest zamknięta");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        cancel();
        activate();

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