package kopka.jakub.gardensystem.Repository;

import kopka.jakub.gardensystem.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section, Long> {
    Section findBySectionNumber(int sectionNumber);
    void deleteBySectionNumber(int sectionNumber);
}
