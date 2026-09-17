package pe.edu.upc.equipovirtual1.servicesImplements;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.edu.upc.equipovirtual1.dtos.CulturalEventBudgetDTO;
import pe.edu.upc.equipovirtual1.repositories.IEventRepository;
import pe.edu.upc.equipovirtual1.servicesInterfaces.IEventService;

@Service
public class EventServiceImplement implements IEventService {
    private final IEventRepository eventRepository;

    public EventServiceImplement(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CulturalEventBudgetDTO> e2_findCulturalEventBudgets() {
        List<CulturalEventBudgetDTO> e2_resultados = eventRepository.findCulturalEventBudgets();
        return e2_resultados;
    }
}
