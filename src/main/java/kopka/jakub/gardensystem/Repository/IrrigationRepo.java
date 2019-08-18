package kopka.jakub.gardensystem.Repository;

import kopka.jakub.gardensystem.Model.Irrigation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IrrigationRepo extends JpaRepository<Irrigation, Long> {
}