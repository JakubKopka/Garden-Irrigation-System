package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    IrrigationService irrigationService;

    public List<Section> getAllSections() {
        return irrigationService.getIrrigation().getSections();
    }
}
