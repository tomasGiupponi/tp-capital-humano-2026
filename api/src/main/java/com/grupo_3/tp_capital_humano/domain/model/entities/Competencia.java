package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.Objects;

import com.grupo_3.tp_capital_humano.domain.model.enums.TipoCompetencia;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una competencia laboral o habilidad requerida y evaluable en los puestos de trabajo.
 */
@Entity
@Table(name = "competencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Competencia extends SujetoAuditable {
    @Id
    @Column(name = "codigo", length = 20, nullable = false, updatable = false)
    private String codigo;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private TipoCompetencia tipo;

    @Builder.Default
    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    /**
     * Coloca el campo activa en false, registra este cambio en RegistroAuditoria
     * asignando el consultor que
     * hizo el cambio y notifica a todos las instancias que formen parte del
     * Observer.
     * 
     * @param consultor instancia que se persiste como el consultor que generó la
     *                  acción.
     */
    @Override
    public void darDeBaja(Consultor consultor) {
        if (this.activa) {
            this.activa = false;
            RegistroAuditoria reg = construirRegistro(consultor, "BAJA_LOGICA", "Competencia", this.codigo);
            notificarObservadores(reg);
        }
    }

    /**
     * transforma codigo y nombre al formato correspondiente.
     * - codigo queda todo junto y en mayúsculas.
     * - nombre queda todo junto.
     */
    @PrePersist
    @PreUpdate
    private void normalizarDatos() {
        if (this.codigo != null) {
            this.codigo = this.codigo.trim().toUpperCase();
        }
        if (this.nombre != null) {
            this.nombre = this.nombre.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Competencia that))
            return false;
        return codigo != null && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
