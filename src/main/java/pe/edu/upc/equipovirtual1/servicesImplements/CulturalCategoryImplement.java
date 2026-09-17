package pe.edu.upc.equipovirtual1.servicesImplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.equipovirtual1.entities.CulturalCategory;
import pe.edu.upc.equipovirtual1.repositories.CulturalCategoryRepository;
import pe.edu.upc.equipovirtual1.servicesInterfaces.ICategoryService;

@Service
public class CulturalCategoryImplement implements ICategoryService {
    private final CulturalCategoryRepository e2_repository;

    public CulturalCategoryImplement(CulturalCategoryRepository e2_repository) {
        this.e2_repository = e2_repository;
    }

    @Override
    public CulturalCategory e2_registrarCategoria(CulturalCategory e2_category) {
        return e2_repository.save(e2_category);
    }
}
