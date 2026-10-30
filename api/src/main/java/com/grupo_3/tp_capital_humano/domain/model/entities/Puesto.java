package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un puesto laboral dentro de una empresa que requiere un perfil de competencias.
 */
@Entity
@Table(name = "puesto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Puesto extends SujetoAuditable {
    @Id
    @Column(name = "codigo", length = 20, nullable = false, updatable = false)
    private String codigo;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "empresa", length = 100, nullable = false)
    private String empresa;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToMany 
    @JoinTable(
        name = "puesto_competencia",
        joinColumns = @JoinColumn(name = "codigo_puesto"),
        inverseJoinColumns = @JoinColumn(name = "codigo_competencia")
    )
    @Builder.Default
    private List<Competencia> competencias = new Arraylist<>();

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
        if (this.activo) {
            this.activo = false;
            ;
            RegistroAuditoria reg = construirRegistro(consultor, "BAJA_LOGICA", "Factor", this.codigo);
            notificarObservadores(reg);
        }
    }

    /**
     * transforma codigo, nombre y empresa al formato correspondiente.
     * - codigo queda todo junto y en mayúsculas.
     * - nombre queda todo junto.
     * - empresa queda todo junto.
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
        if (this.empresa != null) {
            this.empresa = this.empresa.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Puesto that))
            return false;
        return codigo != null && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
