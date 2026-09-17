package pe.edu.upc.equipovirtual1.services;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.equipovirtual1.dto.*;
import pe.edu.upc.equipovirtual1.models.CulturalCategory;
import pe.edu.upc.equipovirtual1.repositories.*;
@Service
@RequiredArgsConstructor
public class CulturalEventService {
    private final CulturalCategoryRepository categoryRepository;
    private final CulturalActivityRepository activityRepository;
    @Transactional
    public CulturalCategoryResponse registerCategory(CulturalCategoryRequest e2Request) {
        CulturalCategory e2Category = new CulturalCategory();
        e2Category.setName(e2Request.getName());
        e2Category.setDescription(e2Request.getDescription());
        e2Category.setType(e2Request.getType());
        e2Category.setTargetAudience(e2Request.getTargetAudience());
        e2Category.setActive(e2Request.getActive());
        CulturalCategory e2Saved = categoryRepository.save(e2Category);
        return new CulturalCategoryResponse(e2Saved.getId(), e2Saved.getName(), e2Saved.getDescription(),
                e2Saved.getType(), e2Saved.getTargetAudience(), e2Saved.getActive());
    }
    @Transactional(readOnly = true)
    public List<CulturalBudgetResponse> findCulturalBudgets() {
        return activityRepository.findCulturalBudgets().stream()
                .map(e2Row -> new CulturalBudgetResponse(e2Row.getEventName(), e2Row.getCategoryName(), e2Row.getTotalBudget()))
                .toList();
    }
}
