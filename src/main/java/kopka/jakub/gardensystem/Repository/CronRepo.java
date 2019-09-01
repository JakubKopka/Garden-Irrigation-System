package kopka.jakub.gardensystem.Repository;

import kopka.jakub.gardensystem.Model.Cron;
import kopka.jakub.gardensystem.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronRepo extends JpaRepository<Cron, Long> {
    Cron findByCron(String cron);
}
