package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa la ponderación asignada a un valor de opción dentro del contexto de una pregunta.
 */
@Entity
@Table(name = "pregunta_valor_opcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaValorOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valor_opcion", nullable = false)
    private ValorOpcion valorOpcion;

    @Column(name = "ponderacion", nullable = false)
    private double ponderacion;

    public void setPonderacion(double valor) {
        if (valor < 0.0) {
            throw new IllegalArgumentException("La ponderación de la opción de respuesta no puede ser negativa.");
        }
        this.ponderacion = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PreguntaValorOpcion that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
