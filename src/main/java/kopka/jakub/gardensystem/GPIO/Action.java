package kopka.jakub.gardensystem.GPIO;

import com.pi4j.io.gpio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class Action {

    private GpioController gpio = GpioFactory.getInstance();
    List<GpioPinDigitalMultipurpose > pins;


    public Action() {
        pins = new ArrayList<>();
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_01, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_02, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_03, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_04,PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_05, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_06, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_07, PinMode.DIGITAL_OUTPUT));
        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_08, PinMode.DIGITAL_OUTPUT));

//        for (GpioPinDigitalOutput pin : pins) {
//            pin.setMode(PinMode.DIGITAL_OUTPUT);
//        }
    }

    public String openSequence(int gpionumber) {
        pins.get(gpionumber-1).low();

        return pins.get(gpionumber-1).getState().toString();
    }

    public String  closeSequence(int gpionumber){
//
        pins.get(gpionumber-1).high();
        return pins.get(gpionumber-1).getState().toString();
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        gpio.shutdown();
    }
}
