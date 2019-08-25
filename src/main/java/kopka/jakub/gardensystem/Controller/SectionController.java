package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import kopka.jakub.gardensystem.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SectionController {

    @Autowired
    SectionService sectionService;


    @RequestMapping("/section/all")
    public  List<Section> getAllSections(){
        return sectionService.getAllSections();
    }
}
