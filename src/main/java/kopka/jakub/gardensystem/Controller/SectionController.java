package kopka.jakub.gardensystem.Controller;

import kopka.jakub.gardensystem.Dto.CronDto;
import kopka.jakub.gardensystem.Dto.SectionDto;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import kopka.jakub.gardensystem.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/section")
public class SectionController {

    @Autowired
    SectionService sectionService;


    @RequestMapping()
    public String section(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("newSection", new SectionDto());
        return "section";
    }


    @RequestMapping("/add")
    public String addNewSection(@ModelAttribute SectionDto sectionDto) {
        sectionService.addSection(sectionDto);
        return "redirect:/section";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSectionGet(@PathVariable Long id, Model model) {
        Section section = sectionService.getSectionById(id);
        model.addAttribute("editSection", section);
        return "editsection";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editSectionPost(@PathVariable Long id, @ModelAttribute Section section) {
        sectionService.editSection(id, section);
        return "redirect:/section";
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateSections(@RequestBody List<Section> sectionList) {
        sectionService.updateSections(sectionList);
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable(name = "id") Long id) {
        sectionService.deleteById(id);
        return "redirect:/section";
    }

}
