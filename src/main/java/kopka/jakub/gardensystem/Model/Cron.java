package kopka.jakub.gardensystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cron {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "cron")
    private String cron;

    @Column(name = "time")
    private String time;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Irrigation_list_id", referencedColumnName="id")
    Irrigation irrigation;

}
