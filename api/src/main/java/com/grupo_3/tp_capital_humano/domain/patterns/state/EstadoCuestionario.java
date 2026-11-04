package com.grupo_3.tp_capital_humano.domain.patterns.state;

import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.enums.NombreEstadoCuestionario;

public abstract class EstadoCuestionario {

    // Devuelve el enum representativo para persistir en PostgreSQL
    public abstract NombreEstadoCuestionario getNombre();

    // Evalúa si el candidato puede ingresar en este momento
    public abstract boolean permitirIngreso(Cuestionario cuestionario);

    // Por defecto, nadie puede finalizar salvo EstadoEnProceso
    public void finalizar(Cuestionario cuestionario) {
        throw new IllegalStateException("No se puede finalizar un cuestionario en estado " + getNombre());
    }

    // Por defecto, los estados terminales (Completo, Incompleto, SinContestar) no
    // hacen nada al vencer
    public void vencer(Cuestionario cuestionario) {

    }

    // Ejecuta la mutación de estado en el cuestionario
    public void cambiarEstado(Cuestionario cuestionario, EstadoCuestionario nuevoEstado) {
        cuestionario.cambiarEstado(nuevoEstado);
    }
}