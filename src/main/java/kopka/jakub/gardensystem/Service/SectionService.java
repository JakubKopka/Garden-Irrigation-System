package kopka.jakub.gardensystem.Service;

import kopka.jakub.gardensystem.Dto.SectionDto;
import kopka.jakub.gardensystem.Mapper;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Repository.IrrigationRepo;
import kopka.jakub.gardensystem.Repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    IrrigationService irrigationService;

    @Autowired
    SectionRepo sectionRepo;

    @Autowired
    IrrigationRepo irrigationRepo;

    @Autowired
    Mapper mapper;


    public List<Section> getAllSections() {
        return irrigationService.getIrrigation().getSections();
    }

    public void updateSections(List<Section> sectionList) {
        Irrigation irrigation = irrigationService.getIrrigation();
        Long irrigationId = irrigation.getId();

//        List<Section> sectionsDB = sectionRepo.findAll();

        //TODO
        // REFAKTOR

        sectionRepo.deleteAll();
        irrigation.setSections(sectionList);
        sectionRepo.saveAll(sectionList);




//        sectionList.stream().forEach(section ->{
//            if(sectionsDB.contains(section)){
//                // aktualizauj starą sekcję
////               Section sectionDB = sectionRepo.findBySection_number(section.getSection_number()).get(0);
////               sectionDB.setDescription(section.getDescription());
////               sectionDB.setDuration(section.getDuration());
//            } else {
//                Section newSection = section;
//                sectionRepo.save(section);
//            }
//        });

//        irrigation.setSections(sectionList);
//        irrigationRepo.save(irrigation);

    }

    public void deleteById(Long id) {
        sectionRepo.deleteById(id);
    }

    public void deleteBySectionNumber(int sectonNumber) {
        sectionRepo.deleteBySectionNumber(sectonNumber);
    }

    public void addSection(SectionDto s) {
        Section section = mapper.mapToSection(s);
        sectionRepo.save(section);
    }

    public Section getSectionById(Long id){
        return sectionRepo.findById(id).orElse(null);
    }

    public void editSection(Long id, Section section){
        section.setId(id);
        section.setIrrigation(irrigationService.getIrrigation());
        sectionRepo.save(section);
    }
}
