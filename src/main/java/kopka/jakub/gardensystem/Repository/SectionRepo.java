package kopka.jakub.gardensystem.Repository;

import kopka.jakub.gardensystem.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepo extends JpaRepository<Section, Long> {
}
