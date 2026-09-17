package pe.edu.upc.equipovirtual1.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CulturalCategoryRequest {
    @NotBlank(message = "El campo name es obligatorio")
    @Size(max = 100, message = "El campo name admite máximo 100 caracteres")
    private String name;

    @NotBlank(message = "El campo description es obligatorio")
    @Size(max = 500, message = "El campo description admite máximo 500 caracteres")
    private String description;

    @NotBlank(message = "El campo type es obligatorio")
    @Size(max = 50, message = "El campo type admite máximo 50 caracteres")
    private String type;

    @NotBlank(message = "El campo targetAudience es obligatorio")
    @Size(max = 100, message = "El campo targetAudience admite máximo 100 caracteres")
    private String targetAudience;

    @NotNull(message = "El campo active es obligatorio")
    private Boolean active;
}
