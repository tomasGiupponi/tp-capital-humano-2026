package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa el resultado y puntaje final obtenido por un candidato en una competencia evaluada.
 */
@Entity
@Table(name = "resultado_competencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoCompetencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuestionario", nullable = false)
    private Cuestionario cuestionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_competencia", nullable = false)
    private Competencia competencia;

    @Column(name = "puntaje_obtenido", nullable = false)
    private float puntajeObtenido;

    @OneToMany(mappedBy = "resultadoCompetencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ResultadoFactor> resultadosFactores = new ArrayList<>();

    /**
     * @return Retorna el puntaje obtenido en la competencia.
     */
    public float getPuntaje() {
        return this.puntajeObtenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ResultadoCompetencia that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
