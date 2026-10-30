package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un conjunto o escala de opciones de respuesta aplicable a
 * preguntas de evaluación.
 */

@Entity
@Table(name = "opcion_respuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpcionDeRespuesta extends SujetoAuditable {

    @Id
    @Column(name = "codigo", length = 20, nullable = false, updatable = false)
    private String codigo;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    @OneToMany(mappedBy = "opcionDeRespuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ValorOpcion> valores = new ArrayList<>();

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
            RegistroAuditoria reg = construirRegistro(consultor, "BAJA_LOGICA", "OpcionDeRespuesta", this.codigo);
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
    private void normalizar() {
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
        if (!(o instanceof OpcionDeRespuesta that))
            return false;
        return codigo != null && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}