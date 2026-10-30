package com.grupo_3.tp_capital_humano.domain.patterns.factory;

import com.grupo_3.tp_capital_humano.domain.exception.PreguntasInsuficientesPorFactorException;
import com.grupo_3.tp_capital_humano.domain.model.entities.*;
import com.grupo_3.tp_capital_humano.domain.patterns.state.EstadoActivo;

import java.time.LocalDateTime;
import java.util.*;

public class ConstructorCuestionario {

    private Candidato candidato;
    private Evaluacion evaluacion;
    private PlantillaCuestionario plantilla;
    private ConfiguracionCuestionario configuracion;

    public ConstructorCuestionario conCandidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public ConstructorCuestionario conEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        return this;
    }

    public ConstructorCuestionario conPlantilla(PlantillaCuestionario plantilla) {
        this.plantilla = plantilla;
        return this;
    }

    public ConstructorCuestionario conConfiguracion(ConfiguracionCuestionario configuracion) {
        this.configuracion = configuracion;
        return this;
    }

    // EL MÉTODO BUILDER: Ejecuta todo el armado complejo
    public Cuestionario construir() {
        Cuestionario cuestionario = new Cuestionario();
        cuestionario.setCandidato(this.candidato);
        cuestionario.setEvaluacion(this.evaluacion);
        cuestionario.setClave(generarClaveAleatoria8Caracteres());
        cuestionario.setFechaCreacion(LocalDateTime.now());
        cuestionario.cambiarEstado(new EstadoActivo()); // Nace en Activo

        // PASO 1: Sorteo de exactamente 2 preguntas activas por cada factor (Regla CU
        // 025)
        List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
        for (Factor factor : this.plantilla.getFactores()) {
            List<Pregunta> preguntasDelFactor = this.plantilla.obtenerPreguntasDeFactor(factor.getCodigo());

            // Validación estricta de DESO: debe haber al menos 2 preguntas
            if (preguntasDelFactor.size() < 2) {
                throw new PreguntasInsuficientesPorFactorException(
                        "El factor " + factor.getNombre() + " no posee las 2 preguntas mínimas requeridas.");
            }

            Collections.shuffle(preguntasDelFactor); // Barajar preguntas del factor
            preguntasSeleccionadas.add(preguntasDelFactor.get(0)); // Pregunta 1
            preguntasSeleccionadas.add(preguntasDelFactor.get(1)); // Pregunta 2
        }

        // PASO 2: Mezclar todas las preguntas del examen para que el orden sea
        // aleatorio
        Collections.shuffle(preguntasSeleccionadas);

        // PASO 3: Agrupar en bloques según la configuración (ej: 2 preguntas por paso
        // de pantalla)
        int tamanoBloque = (this.configuracion != null && this.configuracion.getCantidadPreguntasPorBloque() > 0)
                ? this.configuracion.getCantidadPreguntasPorBloque()
                : 2;

        List<BloqueCuestionario> bloques = new ArrayList<>();
        int nroBloque = 1;

        for (int i = 0; i < preguntasSeleccionadas.size(); i += tamanoBloque) {
            BloqueCuestionario bloque = new BloqueCuestionario();
            bloque.setCuestionario(cuestionario);
            bloque.setNroBloque(nroBloque++);
            bloque.setContestado(false);

            List<Pregunta> subLista = preguntasSeleccionadas.subList(
                    i, Math.min(i + tamanoBloque, preguntasSeleccionadas.size()));

            List<PreguntaSeleccionada> preguntasDelBloque = new ArrayList<>();
            int orden = 1;
            for (Pregunta preg : subLista) {
                PreguntaSeleccionada ps = new PreguntaSeleccionada();
                ps.setCuestionario(cuestionario);
                ps.setBloque(bloque);
                ps.setPregunta(preg);
                ps.setOrden(orden++);
                preguntasDelBloque.add(ps);
            }

            bloque.setPreguntas(preguntasDelBloque);
            bloques.add(bloque);
        }

        cuestionario.setBloques(bloques);
        return cuestionario;
    }

    private String generarClaveAleatoria8Caracteres() {
        // Genera clave única de 8 caracteres alfanuméricos en mayúsculas (ej:
        // "A9F2K8Z1")
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}