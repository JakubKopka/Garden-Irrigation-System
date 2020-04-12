package kopka.jakub.gardensystem.Service;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import kopka.jakub.gardensystem.GPIO.Action;
import kopka.jakub.gardensystem.GPIO.GPIOStatus;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RaspberryService {


    @Autowired
    Action action;

    @Autowired
    DynamicSchedulerVersion2 dynamicSchedulerVersion2;


    @Autowired
    IrrigationRepo irrigationRepo;

    public String open(int pin) {
        System.out.println(action.openSequence(pin).toString());
        String status = action.openSequence(pin);
        return status;
    }

    public String close(int pin) {
        action.closeSequence(pin);
        return action.closeSequence(1);
    }

    public void start() {
        List<Irrigation> irrigations = (List<Irrigation>) irrigationRepo.findAll();
       dynamicSchedulerVersion2.scheduleCron(irrigations.get(0));
    }

    public void stop() {
//        List<Irrigation> irrigationList = (List<Irrigation>) irrigationRepo.findAll();
//        Irrigation irrigation = irrigationList.get(0);
//        irrigation.setActive(false);
//        irrigationRepo.save(irrigation);

        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
            pin.low();
        }
        dynamicSchedulerVersion2.cancel();
        dynamicSchedulerVersion2.activate();

        // sprawdzic czy wszystkie piny są off
    }

    public List<GPIOStatus> gpioStatus() {
        List<GPIOStatus> gpioStatusList = new ArrayList<>();
//        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
//            gpioStatusList.add(new GPIOStatus(pin.getName(), pin.getState().toString()));
//        }
        return gpioStatusList;
    }

    public void change(String time) {
        System.out.println("=====================" + time);
        List<Irrigation> irrigationList = (List<Irrigation>) irrigationRepo.findAll();
        Irrigation irrigation = irrigationList.get(0);
        irrigation.setActive(true);
        String[] strings = time.split(":");
//        irrigation.setCron("0 "+ strings[1]+" "+strings[0]+" * * ?");
        irrigationRepo.save(irrigation);
        dynamicSchedulerVersion2.cancel();
        dynamicSchedulerVersion2.activate();
    }

    public void demo() {
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

    public List<GPIOStatus> getPinsInfo() {
        List<GPIOStatus> gpioStatusList = new ArrayList<>();
        for (GpioPinDigitalMultipurpose pin: action.getPins()) {
            gpioStatusList.add(new GPIOStatus(pin.getName(), pin.getState().toString()));
        }

//        Na localu
//        gpioStatusList.add(new GPIOStatus("PIN O1", "HIGH"));
//        gpioStatusList.add(new GPIOStatus("PIN O2", "LOW"));


        return gpioStatusList;
    }
}
