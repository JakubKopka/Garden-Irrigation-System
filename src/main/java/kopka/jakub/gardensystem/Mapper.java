package kopka.jakub.gardensystem;

import kopka.jakub.gardensystem.Dto.SectionDto;
import kopka.jakub.gardensystem.Model.Irrigation;
import kopka.jakub.gardensystem.Model.Section;
import kopka.jakub.gardensystem.Service.IrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    IrrigationService irrigationService;


    public String mapStringToCron(String time) {
//        6:20 -> "0 20 6 * * ?"
        String[] strings = time.split(":");
        return "0 " + strings[1] + " " + strings[0] + " * * ?";
    }

    public String mapCronToString(String cron){
        String[] strings = cron.split(" ");
        return strings[2]+":"+strings[1];
    }


    public Section mapToSection(SectionDto sectionDto){
        Section section = new Section();
        section.setSectionNumber(sectionDto.getSectionNumber());
        section.setDescription(sectionDto.getDescription());
        Irrigation irrigation = irrigationService.getIrrigation();
        section.setIrrigation(irrigation);
        section.setDuration(sectionDto.getDuration());
        return section;
    }

    public SectionDto mapToSectionDto(Section section){
        SectionDto sectionDto =  new SectionDto();
        sectionDto.setSectionNumber(section.getSectionNumber());
        sectionDto.setDescription(section.getDescription());
        sectionDto.setDuration(section.getDuration());
        return sectionDto;
    }

    //TODO
    // CronDto
}
