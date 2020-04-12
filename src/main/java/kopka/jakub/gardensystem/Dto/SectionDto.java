package kopka.jakub.gardensystem.Dto;


import kopka.jakub.gardensystem.Model.Section;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectionDto{

    private int sectionNumber;
    private String description;
    private int duration;
}
