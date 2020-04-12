package kopka.jakub.gardensystem.Model;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "Irrigation_list_id", referencedColumnName="id")
    Irrigation irrigation;
}
