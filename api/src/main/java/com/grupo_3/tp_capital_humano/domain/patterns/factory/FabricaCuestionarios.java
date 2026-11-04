package com.grupo_3.tp_capital_humano.domain.patterns.factory;

import com.grupo_3.tp_capital_humano.domain.model.entities.Candidato;
import com.grupo_3.tp_capital_humano.domain.model.entities.ConfiguracionCuestionario;
import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.entities.Evaluacion;

public class FabricaCuestionarios {

    private final PlantillaCuestionario plantilla;
    private ConstructorCuestionario constructor;

    // Constructor que recibe la plantilla maestra (coincide con el diagrama DDS)
    public FabricaCuestionarios(PlantillaCuestionario plantilla) {
        this.plantilla = plantilla;
    }

    // EL MÉTODO FACTORY: Une al Prototype con el Builder
    public Cuestionario crearCuestionario(Candidato candidato, Evaluacion evaluacion,
            ConfiguracionCuestionario config) {
        // 1. Clona la plantilla maestra en RAM (Prototype)
        PlantillaCuestionario plantillaClonada = this.plantilla.clonar();

        // 2. Construye el cuestionario personalizado (Builder)
        this.constructor = new ConstructorCuestionario();
        return this.constructor
                .conCandidato(candidato)
                .conEvaluacion(evaluacion)
                .conPlantilla(plantillaClonada)
                .conConfiguracion(config)
                .construir();
    }
}
