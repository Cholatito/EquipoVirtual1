package pe.edu.upc.equipovirtual1.servicesImplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.equipovirtual1.entities.CulturalActivity;
import pe.edu.upc.equipovirtual1.repositories.IActivityRepository;
import pe.edu.upc.equipovirtual1.servicesInterfaces.IActivityService;

@Service
public class ActivityServiceImplement implements IActivityService {
    private final IActivityRepository activityRepository;

    public ActivityServiceImplement(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public CulturalActivity save(CulturalActivity activity) {
        return activityRepository.save(activity);
    }
}
