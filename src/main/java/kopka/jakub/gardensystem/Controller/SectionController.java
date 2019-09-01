package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import kopka.jakub.gardensystem.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/section")
public class SectionController {

    @Autowired
    SectionService sectionService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public  List<Section> getAllSections(){
        return sectionService.getAllSections();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public  void updateSections(@RequestBody List<Section> sectionList){
        System.out.println("=========================");
        System.out.println(sectionList);
        System.out.println("=========================");
        sectionService.updateSections(sectionList);
    }

    @RequestMapping(value = "/delete/{sectonNumber}", method = RequestMethod.DELETE)
    public  void deleteById(@PathVariable int sectonNumber){
        sectionService.deleteBySectionNumber(sectonNumber);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addSection(@RequestBody Section section){
        sectionService.addSection(section);
    }


}
