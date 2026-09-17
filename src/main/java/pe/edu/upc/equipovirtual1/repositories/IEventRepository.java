package pe.edu.upc.equipovirtual1.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.equipovirtual1.dtos.CulturalEventBudgetDTO;
import pe.edu.upc.equipovirtual1.entities.CulturalEvent;

public interface IEventRepository extends JpaRepository<CulturalEvent, Long> {

    @Query(value = """
                 SELECT e.name AS evento,
                     c.name AS categoria,
                     SUM(a.quantity * a.cost) AS presupuesto_total
            FROM cultural_activity a
            JOIN cultural_event e ON a.event_id = e.id
            JOIN cultural_category c ON a.category_id = c.id
            GROUP BY e.id, e.name, c.id, c.name
            ORDER BY e.name, c.name, e.id, c.id
            """, nativeQuery = true)
    List<CulturalEventBudgetDTO> findCulturalEventBudgets();
}
