package pe.edu.upc.equipovirtual1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CulturalEvent")
public class CulturalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "location", length = 200, nullable = false)
    private String location;

    @Column(name = "modality", length = 50, nullable = false)
    private String modality;

    @Column(name = "eventDate", nullable = false)
    private LocalDate eventDate;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @Column(name = "budget", precision = 18, scale = 2, nullable = false)
    private BigDecimal budget;

    public CulturalEvent() {
    }

    public CulturalEvent(Long id, String name, String description, String location, String modality, LocalDate eventDate, Integer capacity, String status, BigDecimal budget) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.modality = modality;
        this.eventDate = eventDate;
        this.capacity = capacity;
        this.status = status;
        this.budget = budget;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
