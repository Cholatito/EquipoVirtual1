package pe.edu.upc.equipovirtual1.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cultural_activity")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CulturalActivity {
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

    @NotNull(message = "El campo quantity es obligatorio")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "El campo activityDate es obligatorio")
    @Column(nullable = false)
    private LocalDate activityDate;

    @NotNull(message = "El campo startTime es obligatorio")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotNull(message = "El campo duration es obligatorio")
    @Column(nullable = false)
    private Integer duration;

    @NotNull(message = "El campo cost es obligatorio")
    @Column(nullable = false)
    private BigDecimal cost;

    @NotBlank(message = "El campo responsible es obligatorio")
    @Size(max = 100, message = "El campo responsible admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String responsible;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private CulturalEvent event;
    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CulturalCategory category;
}
