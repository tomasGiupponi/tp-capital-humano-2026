package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.util.List;

import com.grupo_3.tp_capital_humano.domain.model.entities.Competencia;
import com.grupo_3.tp_capital_humano.domain.model.entities.Factor;

public interface CompetenciaDAO {
    void guardar(Competencia competencia);

    void actualizar(Competencia competencia);

    void eliminarLogico(Competencia competencia);

    List<Competencia> buscarPorNombre(String nombre);

    List<Factor> obtenerFactores(Competencia competencia);

    void guardarFactor(Factor factor);

    void actualizarFactor(Factor factor);

    void eliminarLogicoFactor(Factor factor);
}
