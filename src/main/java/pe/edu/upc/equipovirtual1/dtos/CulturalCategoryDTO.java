package pe.edu.upc.equipovirtual1.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CulturalCategoryDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "El tipo es obligatorio")
    private String type;

    @NotBlank(message = "El público objetivo es obligatorio")
    private String targetAudience;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean active;

    public CulturalCategoryDTO() {
    }

    public CulturalCategoryDTO(String name, String description, String type,
                               String targetAudience, Boolean active) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.targetAudience = targetAudience;
        this.active = active;
    }

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
