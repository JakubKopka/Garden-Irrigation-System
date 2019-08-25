package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Service.IrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IrrigationController {

    @Autowired
    IrrigationService irrigationService;

    @RequestMapping("/irrigation")
    public Irrigation getIrrigation() {
        return irrigationService.getIrrigation();
    }
}
