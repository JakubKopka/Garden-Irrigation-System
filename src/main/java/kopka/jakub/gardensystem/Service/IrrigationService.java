package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrrigationService {

    @Autowired
    IrrigationRepo irrigationRepo;


    public Irrigation getIrrigation() {
        return irrigationRepo.findAll().get(0);
    }
}
