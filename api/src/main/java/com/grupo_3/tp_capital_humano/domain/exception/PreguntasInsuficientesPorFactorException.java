package com.grupo_3.tp_capital_humano.domain.exception;

/**
 * Excepción de dominio que se lanza al intentar evaluar candidatos para un puesto (CU 25)
 * cuando alguno de los factores asociados a las competencias del puesto cuenta con menos
 * de la cantidad mínima de preguntas activas requeridas (mínimo 2 preguntas por factor).
 */
public class PreguntasInsuficientesPorFactorException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO =
            "No hay suficientes preguntas activas para evaluar todos los factores del puesto (se requieren al menos 2 preguntas por factor).";

    /**
     * Crea la excepción con un mensaje descriptivo por defecto.
     */
    public PreguntasInsuficientesPorFactorException() {
        super(MENSAJE_POR_DEFECTO);
    }

    /**
     * Crea la excepción con un mensaje personalizado indicando el factor sin preguntas suficientes.
     *
     * @param mensaje descripción específica del error.
     */
    public PreguntasInsuficientesPorFactorException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción detallando el código del factor y la cantidad actual de preguntas activas encontradas.
     *
     * @param codigoFactor   código o nombre del factor que no alcanza el mínimo de preguntas.
     * @param cantidadActual cantidad de preguntas activas encontradas para dicho factor.
     */
    public PreguntasInsuficientesPorFactorException(String codigoFactor, int cantidadActual) {
        super("El factor '" + codigoFactor + "' posee solo " + cantidadActual
                + " pregunta(s) activa(s). Se requieren al menos 2 para generar la evaluación.");
    }

    /**
     * Crea la excepción con un mensaje personalizado y la causa original del error.
     *
     * @param mensaje descripción específica del error.
     * @param causa   excepción original que provocó este error.
     */
    public PreguntasInsuficientesPorFactorException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

