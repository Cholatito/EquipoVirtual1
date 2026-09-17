package pe.edu.upc.equipovirtual1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "CulturalCategory")
public class CulturalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",length = 30,nullable = false)
    private String name;
    @Column(name = "description",length = 28,nullable = false)
    private String description;
    @Column(name = "type",length = 28, nullable = false)
    private String type;
    @Column(name = "targetAudience",length = 28,nullable = false)
    private String targetAudience;
    @Column(name = "active",nullable = false)
    private boolean active;

    public CulturalCategory() {
    }

    public CulturalCategory(Long id, String name, String description, String type, String targetAudience, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.targetAudience = targetAudience;
        this.active = active;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
