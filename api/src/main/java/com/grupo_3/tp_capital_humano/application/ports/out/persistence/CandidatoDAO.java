package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.util.List;
import java.util.Optional;

import com.grupo_3.tp_capital_humano.domain.model.entities.Candidato;

public interface CandidatoDAO {
    void importarMasivo(List<Candidato> candidatos);

    List<Candidato> buscarPorFiltros(String apellido, String nombre, Integer nroCandidato);

    Optional<Candidato> buscarPorNroCandidato(Integer nroCandidato);
}
