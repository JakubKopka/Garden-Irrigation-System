package kopka.jakub.gardensystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
public class Cron {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "cron")
    private String cron;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "irrigation", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Irrigation irrigation;




    public Cron() {
    }

    public Cron(String cron, Irrigation irrigation) {
        this.cron = cron;
        this.irrigation = irrigation;
    }

    public Cron(String cron) {
        this.cron = cron;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Irrigation getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(Irrigation irrigation) {
        this.irrigation = irrigation;
    }

    @Override
    public String toString() {
        return "Cron{" +
                "id=" + id +
                ", cron='" + cron + '\'' +
                ", active=" + active +
                '}';
    }
}
