package com.grupo_3.tp_capital_humano.domain.exception;

/**
 * Excepción de dominio que se lanza durante la generación de una evaluación (CU 25)
 * cuando alguno de los candidatos seleccionados ya posee un cuestionario en estado
 * ACTIVO o EN_PROCESO pendiente de finalización.
 */
public class CandidatoConCuestionarioActivoException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO =
            "El candidato seleccionado ya posee un cuestionario activo o en proceso.";

    /**
     * Crea la excepción con un mensaje descriptivo por defecto.
     */
    public CandidatoConCuestionarioActivoException() {
        super(MENSAJE_POR_DEFECTO);
    }

    /**
     * Crea la excepción con un mensaje personalizado indicando el detalle del conflicto.
     *
     * @param mensaje descripción específica del error.
     */
    public CandidatoConCuestionarioActivoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción indicando el número de candidato que ya posee un cuestionario activo.
     *
     * @param nroCandidato identificador numérico del candidato en conflicto.
     */
    public CandidatoConCuestionarioActivoException(int nroCandidato) {
        super("El candidato N° " + nroCandidato + " ya posee un cuestionario activo o en proceso.");
    }

    /**
     * Crea la excepción con un mensaje personalizado y la causa original del error.
     *
     * @param mensaje descripción específica del error.
     * @param causa   excepción original que provocó este error.
     */
    public CandidatoConCuestionarioActivoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

