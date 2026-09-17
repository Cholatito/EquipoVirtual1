package pe.edu.upc.equipovirtual1.repositories;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import pe.edu.upc.equipovirtual1.entity.CulturalActivity;
public interface CulturalActivityRepository extends JpaRepository<CulturalActivity, Integer> {
    interface CulturalBudgetProjection {
        String getEventName();
        String getCategoryName();
        BigDecimal getTotalBudget();
    }
    @Query(value = """
            SELECT e.name AS "eventName", c.name AS "categoryName",
                   SUM(a.quantity * a.cost) AS "totalBudget"
            FROM cultural_activity a
            JOIN cultural_event e ON e.id = a.event_id
            JOIN cultural_category c ON c.id = a.category_id
            GROUP BY e.id, e.name, c.id, c.name
            ORDER BY e.id, c.id
            """, nativeQuery = true)
    List<CulturalBudgetProjection> findCulturalBudgets();
}
