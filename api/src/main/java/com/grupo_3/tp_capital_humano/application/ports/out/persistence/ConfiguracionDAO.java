package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.util.Optional;

import com.grupo_3.tp_capital_humano.domain.model.entities.ConfiguracionCuestionario;

public interface ConfiguracionDAO {
    Optional<ConfiguracionCuestionario> obtenerConfiguracionCuestionario();
}
