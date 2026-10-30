package com.grupo_3.tp_capital_humano.domain.patterns.state;

import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.enums.NombreEstadoCuestionario;
import java.time.LocalDateTime;

public class EstadoActivo extends EstadoCuestionario {

    @Override
    public NombreEstadoCuestionario getNombre() {
        return NombreEstadoCuestionario.ACTIVO;
    }

    @Override
    public boolean permitirIngreso(Cuestionario cuestionario) {
        // Marca inicio y primer acceso
        cuestionario.setFechaHoraInicio(LocalDateTime.now());
        cuestionario.incrementarAcceso();

        // Transición 1: Comienza el examen
        this.cambiarEstado(cuestionario, new EstadoEnProceso());
        return true;
    }

    @Override
    public void vencer(Cuestionario cuestionario) {
        // Transición 2: El proceso batch detectó que expiró sin haber ingresado
        this.cambiarEstado(cuestionario, new EstadoSinContestar());
    }
}