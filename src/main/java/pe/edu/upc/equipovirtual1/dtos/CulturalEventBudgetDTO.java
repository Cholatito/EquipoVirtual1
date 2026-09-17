package pe.edu.upc.equipovirtual1.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CulturalEventBudgetDTO {
    private String evento;
    private String categoria;
    @JsonProperty("presupuesto_total")
    private BigDecimal presupuestoTotal;

    public CulturalEventBudgetDTO() {
    }

    public CulturalEventBudgetDTO(String evento, String categoria, BigDecimal presupuestoTotal) {
        this.evento = evento;
        this.categoria = categoria;
        this.presupuestoTotal = presupuestoTotal;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public void setPresupuestoTotal(BigDecimal presupuestoTotal) {
        this.presupuestoTotal = presupuestoTotal;
    }
}
