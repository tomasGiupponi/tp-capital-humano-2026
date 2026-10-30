package com.grupo_3.tp_capital_humano.domain.model.entities;

import com.grupo_3.tp_capital_humano.domain.patterns.audit.RegistroAuditoria;
import com.grupo_3.tp_capital_humano.domain.patterns.audit.SujetoAuditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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

    @Override
    protected String obtenerNombreEntidad() {
        return "OPCION_RESPUESTA";
    }

    @Override
    protected String obtenerIdEntidad() {
        return this.codigo;
    }

    /**
     * Coloca el campo activa en false, registra este cambio en RegistroAuditoria
     * asignando el usuario que hizo el cambio y notifica a los observadores.
     * 
     * @param usuario nombre de usuario que generó la acción.
     */
    @Override
    public void darDeBaja(String usuario) {
        if (this.activa) {
            this.activa = false;
            RegistroAuditoria reg = construirRegistro(usuario, "BAJA_LOGICA");
            notificarObservadores(reg);
        }
    }

    /**
     * Sobrecarga que permite dar de baja pasando la instancia del consultor.
     * 
     * @param consultor consultor que generó la acción.
     */
    public void darDeBaja(Consultor consultor) {
        darDeBaja(consultor != null ? consultor.getNombreUsuario() : null);
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
