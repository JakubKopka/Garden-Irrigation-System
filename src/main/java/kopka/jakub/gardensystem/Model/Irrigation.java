package kopka.jakub.gardensystem.Model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Irrigation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "cron")
    private String cron;

    @Column(name = "sections")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Section> sections = new ArrayList<>();

    public Irrigation() {
    }

    public Irrigation(String cron, List<Section> sections) {
        this.cron = cron;
        this.sections = sections;
    }

    public Irrigation(String cron) {
        this.cron = cron;
    }

    public void addNewSection(Section section){
        this.sections.add(section);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }


    @Override
    public String toString() {
        return "Irrigation{" +
                "id=" + id +
                ", cron='" + cron + '\'' +
                ", sections=" + sections +
                '}';
    }
}
