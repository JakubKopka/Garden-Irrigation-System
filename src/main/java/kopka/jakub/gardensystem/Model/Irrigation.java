package kopka.jakub.gardensystem.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    private List<Cron> crons;

    @OneToMany(
            targetEntity = Section.class,
            mappedBy = "irrigation",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Section> sections = new ArrayList<>();

    @Column(name = "active")
    private boolean active = true;

}
