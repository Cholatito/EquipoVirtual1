package pe.edu.upc.equipovirtual1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CulturalActivity")
public class CulturalActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @NotNull
    @Positive
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "activityDate", nullable = false)
    private LocalDate activityDate;

    @NotNull
    @Column(name = "startTime", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Positive
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Positive
    @Column(name = "cost", precision = 18, scale = 2, nullable = false)
    private BigDecimal cost;

    @NotBlank
    @Column(name = "responsible", length = 100, nullable = false)
    private String responsible;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private CulturalEvent event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CulturalCategory category;

    public CulturalActivity() {
    }

    public CulturalActivity(
            Long id,
            String name,
            String description,
            Integer quantity,
            LocalDate activityDate,
            LocalTime startTime,
            Integer duration,
            BigDecimal cost,
            String responsible,
            CulturalEvent event,
            CulturalCategory category) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.duration = duration;
        this.cost = cost;
        this.responsible = responsible;
        this.event = event;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public CulturalEvent getEvent() {
        return event;
    }

    public void setEvent(CulturalEvent event) {
        this.event = event;
    }

    public CulturalCategory getCategory() {
        return category;
    }

    public void setCategory(CulturalCategory category) {
        this.category = category;
    }
}
