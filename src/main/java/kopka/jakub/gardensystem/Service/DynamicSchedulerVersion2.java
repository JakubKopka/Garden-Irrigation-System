package kopka.jakub.gardensystem.Service;

//import kopka.jakub.gardensystem.Repository.ConfigRepo;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.Model.Cron;
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

import javax.transaction.Transactional;
import java.time.LocalTime;
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
@Transactional
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
        List<Cron> cronList = new ArrayList<>(irrigationRepo.findAll().get(0).getCrons());
        Cron closestCrone = getClosestTimeInCrone(cronList);
        CronTrigger croneTrigger = new CronTrigger(closestCrone.getCron(), TimeZone.getDefault());
        System.out.println("WEszlooooo 1");
        future = taskRegistrar.getScheduler().schedule(() -> scheduleCron(irrigationRepo.findAll().get(0)), croneTrigger);
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
                System.out.println(" closest == null");
                closest = cronTime;
                cron = listCrone.get(i);
                System.out.println(" closest = " + cronTime);
            }
            System.out.println("closest != null ");
            System.out.println(now + " is Before " + cronTime + "?");
            if (now.isBefore(cronTime)) {
                isAfter = true;
                System.out.println("true");
                System.out.println(closest + ".isAfter( " + cronTime + ")?");
                if (closest.isAfter(cronTime)) {
                    System.out.println("true");
                    closest = cronTime;
                    cron = listCrone.get(i);
                    System.out.println("closest " + closest);
                }
                if (now.isAfter(closest)) {
                    closest = cronTime;
                    cron = listCrone.get(i);
                }
            } else if (!isAfter) {
                System.out.println("isAfter == " + isAfter);
                System.out.println(" !isAfter ");
                if (closest.isAfter(cronTime)) {
                    System.out.println(closest + " isAfter(" + cronTime + ") === true");
                    closest = cronTime;
                    cron = listCrone.get(i);
                    System.out.println("closest = " + closest);
                }
            }
            System.out.println("====== closest: " + closest);
            System.out.println("====================================");

        }


        System.out.println("Closest: " + cron);

        return cron;
    }

    @Transactional
    public void scheduleCron(Irrigation irrigation) {
        List<Section> sections = irrigation.getSections();
        System.out.println(irrigation);
        LOGGER.info("WYKONANO ZAPLANOWANY TASK  \t cron->");

        System.out.println("WEszlooooo 2");

        List<Section> sectionList = irrigationRepo.findAll().get(0).getSections();
        System.out.println("----------------------------");
        System.out.println(sectionList);
        System.out.println("----------------------------");

        for (int i = 0; i < sectionList.size(); i++) {
            if (!irrigationRepo.findAll().get(0).isActive()) {
                Irrigation irrigation1 = irrigationRepo.findAll().get(0);
                irrigation1.setActive(true);
                irrigationRepo.save(irrigation1);
                break;
            }
            System.out.println("+++++++++ Skecja nr " + i + 1 + " jest otwarta");
//            try {
//                action.openSequence(sectionList.get(i).getSectionNumber());
//
//                TimeUnit.MINUTES.sleep(sectionList.get(i).getDuration());
//
//                action.closeSequence(sectionList.get(i).getSectionNumber());
//                System.out.println("+++++++++ Skecja nr " + i + 1 + " jest zamknięta");
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

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