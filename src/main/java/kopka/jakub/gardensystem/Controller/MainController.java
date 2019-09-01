package kopka.jakub.gardensystem.Controller;

import com.pi4j.io.gpio.*;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.GPIO.GPIOStatus;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Service.DynamicSchedulerVersion2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
public class MainController {

    @Autowired
    Action action;

    @Autowired
    DynamicSchedulerVersion2 dynamicSchedulerVersion2;


    @Autowired
    IrrigationRepo irrigationRepo;

    @RequestMapping("/open/{pin}")
    public String open(@PathVariable int pin) throws InterruptedException {
        System.out.println(action.openSequence(pin).toString());
        String status = action.openSequence(pin);
        return status;
    }

    @RequestMapping("/close/{pin}")
    public String close(@PathVariable int pin){
        action.closeSequence(pin);
        return action.closeSequence(1);
    }

    @RequestMapping(value = "/start/{pin}/{seconds}", method = RequestMethod.GET)
    public String start(@PathVariable int pin, @PathVariable int seconds) throws InterruptedException {
        String status = action.openSequence(pin);
        Thread.sleep(seconds*1000);
        action.closeSequence(pin);
        return "start " + pin + seconds;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public  List<GPIOStatus> getGPIOInfo() {
        List<GPIOStatus> gpioStatusList = new ArrayList<>();
//        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
//            gpioStatusList.add(new GPIOStatus(pin.getName(), pin.getState().toString()));
//        }
        return gpioStatusList;
    }


    @RequestMapping(value = "/demo/{time}", method = RequestMethod.GET)
    public void demo() throws InterruptedException {
        System.out.println("=============START DEMO==================");
//        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
//            pin.high();
//            System.out.println("----------------");
//            System.out.println(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + " " + pin.getState().toString());
//            TimeUnit.MINUTES.sleep(1);
//            pin.low();
//            System.out.println(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())  + " " + pin.getState().toString());
//            TimeUnit.SECONDS.sleep(10);
//        }
        System.out.println("=============END DEMO==================");
    }

    @RequestMapping(value = "/change/{time}", method = RequestMethod.GET)
    public void change(@PathVariable String time) {
        System.out.println("====================="+ time);
        Irrigation irrigation = irrigationRepo.findAll().get(0);
        irrigation.setActive(true);
        String[] strings = time.split(":");
//        irrigation.setCron("0 "+ strings[1]+" "+strings[0]+" * * ?");
        irrigationRepo.save(irrigation);
        dynamicSchedulerVersion2.cancel();
        dynamicSchedulerVersion2.activate();
    }


    @RequestMapping("/stop")
    public void stop() {
        Irrigation irrigation = irrigationRepo.findAll().get(0);
        irrigation.setActive(false);
        irrigationRepo.save(irrigation);

//        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
//            pin.low();
//        }
        dynamicSchedulerVersion2.cancel();
        // sprawdzic czy wszystkie piny są off
    }

}
