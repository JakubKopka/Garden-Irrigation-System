package kopka.jakub.gardensystem.Model;




import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Transactional
public class Section {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "section_number")
    private int sectionNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "irrigation", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Irrigation irrigation;

    public Section() {
    }

    public Section(@NotNull int sectionNumber, String description, @NotNull int duration, @NotNull Irrigation irrigation) {
        this.sectionNumber = sectionNumber;
        this.description = description;
        this.duration = duration;
        this.irrigation = irrigation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int section_number) {
        this.sectionNumber = section_number;
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

    public Irrigation getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(Irrigation irrigation) {
        this.irrigation = irrigation;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", section_number=" + sectionNumber +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return sectionNumber == section.sectionNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionNumber);
    }
}
