package pe.edu.upc.equipovirtual1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.equipovirtual1.entities.CulturalActivity;

public interface IActivityRepository extends JpaRepository<CulturalActivity, Long> {
}
