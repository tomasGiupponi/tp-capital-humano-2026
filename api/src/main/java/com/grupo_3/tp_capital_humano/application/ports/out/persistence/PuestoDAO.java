package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.util.List;
import java.util.Optional;

import com.grupo_3.tp_capital_humano.domain.model.entities.Puesto;

public interface PuestoDAO {
    void guardar(Puesto puesto);

    void actualizar(Puesto puesto);

    void eliminarLogico(Puesto puesto);

    List<Puesto> buscarPorFiltros(String codigo, String nombre, String empresa);

    Optional<Puesto> obtenerConCompetencias(String codigo);
}
