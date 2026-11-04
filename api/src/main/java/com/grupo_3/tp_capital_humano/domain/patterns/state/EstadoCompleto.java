package com.grupo_3.tp_capital_humano.domain.patterns.state;

import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.enums.NombreEstadoCuestionario;

public class EstadoCompleto extends EstadoCuestionario {

    @Override
    public NombreEstadoCuestionario getNombre() {
        return NombreEstadoCuestionario.COMPLETO;
    }

    @Override
    public boolean permitirIngreso(Cuestionario cuestionario) {
        return false; // Bloqueado: examen ya entregado
    }
}