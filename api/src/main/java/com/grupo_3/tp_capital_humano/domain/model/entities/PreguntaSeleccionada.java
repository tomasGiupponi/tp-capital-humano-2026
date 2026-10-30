package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una pregunta asignada a un cuestionario, registrando la opción seleccionada por el candidato.
 */
@Entity
@Table(name = "pregunta_seleccionada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaSeleccionada {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuestionario", nullable = false)
    private Cuestionario cuestionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bloque", nullable = false)
    private BloqueCuestionario bloque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valor_elegido")
    private ValorOpcion valorElegido;

    @Column(name = "nro_orden", nullable = false)
    private int orden;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PreguntaSeleccionada that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}