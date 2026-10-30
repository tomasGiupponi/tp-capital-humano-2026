package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un factor o componente específico perteneciente a una competencia evaluable.
 */
@Entity
@Table(name = "factor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factor extends SujetoAuditable {
    @Id
    @Column(name = "codigo", length = 20, nullable = false, updatable = false)
    private String codigo;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "nro_orden", nullable = false)
    private int nroOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_competencia", nullable = false)
    private Competencia competencia;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /**
     * Calcula el puntaje tomando las preguntas respondidas por el candidato, y por
     * cada
     * pregunta seleccionada, obtiene la ponderación para el valor seleccionado y
     * suma todas estas.
     * 
     * @param PreguntasSeleccionadas lista de las preguntas respondidas por el
     *                               candidato
     * @return retorna el valor casteado a float de la división entre la suma
     *         obtenida y 2.
     */
    public float calcularPuntaje(List<PreguntaSeleccionada> PreguntasSeleccionadas) {
        if (PreguntasSeleccionadas == null || PreguntasSeleccionadas.isEmpty()) {
            return 0.0f;
        }

        double sumaPonderaciones = PreguntasSeleccionadas.stream()
                .filter(ps -> ps.getValorElegido() != null)
                .mapToDouble(ps -> {
                    ValorOpcion valor = ps.getValorElegido();
                    return ps.getPregunta().obtenerPonderacionParaValor(valor);
                })
                .sum();

        return (float) (sumaPonderaciones / 2.0);
    }

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
        if (!(o instanceof Factor that))
            return false;
        return codigo != null && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
