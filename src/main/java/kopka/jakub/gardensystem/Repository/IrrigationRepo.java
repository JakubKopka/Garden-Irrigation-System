package kopka.jakub.gardensystem.Repository;

import kopka.jakub.gardensystem.Model.Irrigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.stereotype.Repository;


@Repository
public interface IrrigationRepo extends JpaRepository<Irrigation, Long> {
}