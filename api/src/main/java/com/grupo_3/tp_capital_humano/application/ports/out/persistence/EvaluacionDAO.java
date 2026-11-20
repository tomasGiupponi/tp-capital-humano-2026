package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.grupo_3.tp_capital_humano.domain.model.entities.BloqueCuestionario;
import com.grupo_3.tp_capital_humano.domain.model.entities.Candidato;
import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.entities.Evaluacion;
import com.grupo_3.tp_capital_humano.domain.model.entities.Puesto;
import com.grupo_3.tp_capital_humano.domain.model.entities.ResultadoCompetencia;

public interface EvaluacionDAO {
    void crearEvaluacion(Evaluacion evaluacion, List<Cuestionario> cuestionarios);

    Optional<Cuestionario> obtenerCuestionarioPorClave(String clave);

    List<Cuestionario> obtenerCuestionariosActivosOEnProceso(Candidato candidato);

    void guardarBloque(Cuestionario cuestionario, BloqueCuestionario bloque);

    void guardarResultados(Cuestionario cuestionario, List<ResultadoCompetencia> resultados);

    List<Cuestionario> obtenerVencidos(LocalDateTime fechaHora);

    void actualizarEstado(Cuestionario cuestionario);

    List<Evaluacion> obtenerEvaluacionesPorPuesto(Puesto puesto);
}
