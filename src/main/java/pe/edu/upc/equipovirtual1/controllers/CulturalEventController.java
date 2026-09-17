package pe.edu.upc.equipovirtual1.controllers;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.equipovirtual1.dtos.CulturalCategoryDTO;
import pe.edu.upc.equipovirtual1.dtos.CulturalEventBudgetDTO;
import pe.edu.upc.equipovirtual1.entities.CulturalCategory;
import pe.edu.upc.equipovirtual1.servicesInterfaces.ICategoryService;
import pe.edu.upc.equipovirtual1.servicesInterfaces.IEventService;

@RestController
@RequestMapping("/api/events")
public class CulturalEventController {
    private final ICategoryService categoryService;
    private final IEventService eventService;

    public CulturalEventController(ICategoryService categoryService, IEventService eventService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @PostMapping("/news")
    public ResponseEntity<CulturalCategoryDTO> registerCategory(
            @Valid @RequestBody CulturalCategoryDTO e2_dto) {
        CulturalCategory e2_category = new CulturalCategory(null, e2_dto.getName(),
                e2_dto.getDescription(), e2_dto.getType(), e2_dto.getTargetAudience(), e2_dto.getActive());
        CulturalCategory e2_saved = categoryService.e2_registrarCategoria(e2_category);
        CulturalCategoryDTO e2_response = new CulturalCategoryDTO(e2_saved.getName(),
                e2_saved.getDescription(), e2_saved.getType(), e2_saved.getTargetAudience(), e2_saved.isActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(e2_response);
    }

    @GetMapping("/cultural")
    public ResponseEntity<List<CulturalEventBudgetDTO>> culturalEventBudgets() {
        return ResponseEntity.ok(eventService.e2_findCulturalEventBudgets());
    }
}
