package com.grupo_3.tp_capital_humano.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa los parámetros globales de configuración de los cuestionarios (instrucciones, tiempo y límites de acceso).
 */
@Entity
@Table(name = "configuracion_cuestionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionCuestionario {

    @Id
    private UUID id;

    @Column(name = "instrucciones", columnDefinition = "TEXT", nullable = false)
    private String instrucciones;

    @Column(name = "cantidad_preguntas_por_bloque", nullable = false)
    private int cantidadPreguntasPorBloque;

    @Column(name = "maximo_accesos", nullable = false)
    private int maximoAccesos;

    @Column(name = "tiempo_maximo", nullable = false)
    private long tiempoMaximo; // en minutos

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConfiguracionCuestionario that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
