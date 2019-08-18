package kopka.jakub.gardensystem.Model;




import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "section_number")
    private int section_number;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;

    public Section() {
    }

    public Section(@NotNull int section_number, String description, @NotNull int duration) {
        this.section_number = section_number;
        this.description = description;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSection_number() {
        return section_number;
    }

    public void setSection_number(int section_number) {
        this.section_number = section_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", section_number=" + section_number +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
