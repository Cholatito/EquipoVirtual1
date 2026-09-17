package pe.edu.upc.equipovirtual1.controllers;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import pe.edu.upc.equipovirtual1.dto.*;
import pe.edu.upc.equipovirtual1.services.CulturalEventService;
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class CulturalEventController {
    private final CulturalEventService service;
    @Operation(summary = "HUB01: registrar una categoría cultural")
    @PostMapping(value = "/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalCategoryResponse> registerCategory(@Valid @RequestBody CulturalCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerCategory(request));
    }
    @Operation(summary = "HUB02: presupuesto de actividades por evento y categoría")
    @GetMapping(value = "/cultural", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CulturalBudgetResponse> findCulturalBudgets() {
        return service.findCulturalBudgets();
    }
}
