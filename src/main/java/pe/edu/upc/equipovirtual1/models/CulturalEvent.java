package pe.edu.upc.equipovirtual1.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cultural_event")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CulturalEvent {
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

    @NotBlank(message = "El campo location es obligatorio")
    @Size(max = 200, message = "El campo location admite máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String location;

    @NotBlank(message = "El campo modality es obligatorio")
    @Size(max = 50, message = "El campo modality admite máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String modality;

    @NotNull(message = "El campo eventDate es obligatorio")
    @Column(nullable = false)
    private LocalDate eventDate;

    @NotNull(message = "El campo capacity es obligatorio")
    @Column(nullable = false)
    private Integer capacity;

    @NotBlank(message = "El campo status es obligatorio")
    @Size(max = 50, message = "El campo status admite máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String status;

    @NotNull(message = "El campo budget es obligatorio")
    @Column(nullable = false)
    private BigDecimal budget;
}
