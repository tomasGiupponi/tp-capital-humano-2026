package com.grupo_3.tp_capital_humano.domain.patterns.factory;

import com.grupo_3.tp_capital_humano.domain.model.entities.Competencia;
import com.grupo_3.tp_capital_humano.domain.model.entities.Factor;
import com.grupo_3.tp_capital_humano.domain.model.entities.OpcionDeRespuesta;
import com.grupo_3.tp_capital_humano.domain.model.entities.Pregunta;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlantillaCuestionario implements Cloneable {

    private final List<Competencia> competencias;
    private final List<Factor> factores;
    private final List<Pregunta> preguntas;
    private final List<OpcionDeRespuesta> opciones;

    public PlantillaCuestionario(List<Competencia> competencias, List<Factor> factores, 
                                 List<Pregunta> preguntas, List<OpcionDeRespuesta> opciones) {
        this.competencias = competencias != null ? competencias : new ArrayList<>();
        this.factores = factores != null ? factores : new ArrayList<>();
        this.preguntas = preguntas != null ? preguntas : new ArrayList<>();
        this.opciones = opciones != null ? opciones : new ArrayList<>();
    }

    // EL PATRÓN PROTOTYPE: Duplica las listas en memoria RAM en microsegundos
    @Override
    public PlantillaCuestionario clonar() {
        return new PlantillaCuestionario(
            new ArrayList<>(this.competencias),
            new ArrayList<>(this.factores),
            new ArrayList<>(this.preguntas), // Copia superficial de la lista para aislar el sorteo
            new ArrayList<>(this.opciones)
        );
    }

    // Método helper para que el Builder obtenga las preguntas de un factor específico
    public List<Pregunta> obtenerPreguntasDeFactor(String codigoFactor) {
        return this.preguntas.stream()
            .filter(p -> p.getFactor() != null && codigoFactor.equals(p.getFactor().getCodigo()))
            .collect(Collectors.toList());
    }
}
