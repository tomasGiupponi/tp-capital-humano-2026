package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa el resultado y puntaje intermedio obtenido por un candidato en un factor evaluado.
 */
@Entity
@Table(name = "resultado_factor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resultado_competencia", nullable = false)
    private ResultadoCompetencia resultadoCompetencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_factor", nullable = false)
    private Factor factor;

    @Column(name = "puntaje_obtenido", nullable = false)
    private float puntajeObtenido;

    /**
     * @return Retorna el puntaje obtenido en el factor.
     */
    public float getPuntaje() {
        return this.puntajeObtenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ResultadoFactor that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
