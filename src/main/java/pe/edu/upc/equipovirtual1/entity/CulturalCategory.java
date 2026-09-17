package pe.edu.upc.equipovirtual1.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cultural_category")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CulturalCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El campo name es obligatorio")
    @Size(max = 100, message = "El campo name admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El campo description es obligatorio")
    @Size(max = 500, message = "El campo description admite máximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String description;

    @NotBlank(message = "El campo type es obligatorio")
    @Size(max = 50, message = "El campo type admite máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String type;

    @NotBlank(message = "El campo targetAudience es obligatorio")
    @Size(max = 100, message = "El campo targetAudience admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String targetAudience;

    @NotNull(message = "El campo active es obligatorio")
    @Column(nullable = false)
    private Boolean active;
}
