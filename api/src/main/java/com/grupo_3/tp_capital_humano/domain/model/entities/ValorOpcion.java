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
 * Representa una opción de respuesta individual o valor de escala con su orden
 * y descripción.
 */

@Entity
@Table(name = "valor_opcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValorOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_opcion", nullable = false)
    private OpcionDeRespuesta opcionDeRespuesta;

    @Column(name = "nro_orden", nullable = false)
    private int orden;

    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ValorOpcion that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}