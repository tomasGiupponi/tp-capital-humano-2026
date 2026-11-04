package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
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

/**
 * Representa la ponderación mínima requerida de una competencia para un determinado puesto.
 */
@Entity
@Table(name = "puesto_competencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PonderacionPuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "codigo_puesto", nullable = false)
    private Puesto puesto;

    @ManyToOne
    @JoinColumn(name = "ponderacion_minima", nullable = false)
    private double ponderacionMinima;

    public double getPonderacion() {
        return this.ponderacionMinima;
    }

    /**
     * Coloca el valor recibido como parámetro a la ponderación mínima del puesto.
     * 
     * @param valor parámetro con valor númerico que debe estar comprendido entre 0
     *              y 10.
     */
    public void setPonderacion(double valor) {
        if (valor < 0.0 || valor > 10.0) {
            throw new IllegalArgumentException("La ponderacion mínima del puesto debe estar comprendida entre 0 y 10.");
        }

        this.ponderacionMinima = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PonderacionPuesto that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
