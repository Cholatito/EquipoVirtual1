package pe.edu.upc.equipovirtual1.servicesInterfaces;

import java.util.List;
import pe.edu.upc.equipovirtual1.dtos.CulturalEventBudgetDTO;

public interface IEventService {
    List<CulturalEventBudgetDTO> e2_findCulturalEventBudgets();
}
