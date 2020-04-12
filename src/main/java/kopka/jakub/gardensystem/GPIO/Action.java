package kopka.jakub.gardensystem.GPIO;

import com.pi4j.io.gpio.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;

@Getter
@Setter
@Component
@Scope("singleton")
public class Action {

//    Należy odkomentować
//    private GpioController gpio = GpioFactory.getInstance();
    private List<GpioPinDigitalMultipurpose> pins;


    public Action() {
        pins = new ArrayList<>();
        //    Należy odkomentować
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_01, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_02, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_03, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_04, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_05, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_06, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_07, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_08, PinMode.DIGITAL_OUTPUT));
//
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_09, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_10, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_11, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_12, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_13, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_14, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_15, PinMode.DIGITAL_OUTPUT));
//        pins.add(gpio.provisionDigitalMultipurposePin(RaspiPin.GPIO_16, PinMode.DIGITAL_OUTPUT));

        for (GpioPinDigitalOutput pin : pins) {
            pin.setMode(PinMode.DIGITAL_OUTPUT);
        }
    }

    public String openSequence(int gpionumber) {
        pins.get(gpionumber - 1).high();
        return pins.get(gpionumber - 1).getName() + " " + pins.get(gpionumber - 1).getState().toString();
    }

    public String closeSequence(int gpionumber) {
        pins.get(gpionumber - 1).low();
        return pins.get(gpionumber - 1).getName() + " " + pins.get(gpionumber - 1).getState().toString();
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        //    Należy odkomentować
//        gpio.shutdown();
    }


}
