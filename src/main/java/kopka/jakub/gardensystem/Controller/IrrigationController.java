package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Service.IrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping(value = "/irrigation")
public class IrrigationController {

    @Autowired
    IrrigationService irrigationService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Irrigation getIrrigation() {
        return irrigationService.getIrrigation();
    }

}
