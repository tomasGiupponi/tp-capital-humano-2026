package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import java.util.List;

import com.grupo_3.tp_capital_humano.domain.model.entities.Competencia;
import com.grupo_3.tp_capital_humano.domain.model.entities.Factor;
import com.grupo_3.tp_capital_humano.domain.model.entities.OpcionDeRespuesta;
import com.grupo_3.tp_capital_humano.domain.model.entities.Pregunta;

public interface PreguntaDAO {
    void guardar(Pregunta pregunta);

    void actualizar(Pregunta pregunta);

    void eliminarLogico(Pregunta pregunta);

    List<Pregunta> buscar(Competencia competencia, Factor factor, String nombrePregunta);

    List<Pregunta> obtenerPreguntasActivasPorFactor(Factor factor);

    void guardarOpcionDeRespuesta(OpcionDeRespuesta opcionDeRespuesta);

    void actualizarOpcionDeRespuesta(OpcionDeRespuesta opcionDeRespuesta);

    void eliminarLogicoOpcionDeRespuesta(OpcionDeRespuesta opcionDeRespuesta);

    List<OpcionDeRespuesta> buscarOpcionesDeRespuesta(String nombre);
}
