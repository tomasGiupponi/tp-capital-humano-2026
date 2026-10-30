package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa un bloque de preguntas dentro de un cuestionario para su
 * resolución secuencial y paginada.
 */
@Entity
@Table(name = "bloque_cuestionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloqueCuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuestionario", nullable = false)
    private Cuestionario cuestionario;

    @Column(name = "nro_bloque", nullable = false)
    private int nroBloque;

    @Column(name = "cantidad_preguntas", nullable = false)
    private int cantidadPreguntas;

    @Builder.Default
    @Column(name = "contestado", nullable = false)
    private boolean contestado = false;

    @OneToMany(mappedBy = "bloque", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PreguntaSeleccionada> preguntas = new ArrayList<>();

    public boolean estaContestado() {
        return this.contestado;
    }

    public void marcarContestado() {
        this.contestado = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BloqueCuestionario that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
