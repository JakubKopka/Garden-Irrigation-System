package kopka.jakub.gardensystem.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//    @Fetch(value = FetchMode.SUBSELECT)
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)


@Entity
@Transactional
public class Irrigation {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @OneToMany(
            targetEntity = Cron.class,
            mappedBy = "irrigation",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Cron> crons = new ArrayList<>();

    @OneToMany(
            targetEntity = Section.class,
            mappedBy = "irrigation",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Section> sections = new ArrayList<>();

    @Column(name="active")
    private boolean active = true;

    public Irrigation() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cron> getCrons() {
        return crons;
    }

    public void setCrons(List<Cron> crons) {
        this.crons = crons;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void addNewSection(Section section) {
        this.sections.add(section);
    }

    public void addNewCron(Cron cron) {
        this.crons.add(cron);
    }

    public void addCron(Cron cron) {
        this.crons.add(cron);
    }
}
