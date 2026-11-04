package com.grupo_3.tp_capital_humano.domain.exception;

/**
 * Excepción de dominio que se lanza cuando un candidato intenta acceder o
 * avanzar en un cuestionario (CU 01 / CU 26) que no permite el ingreso,
 * ya sea porque su estado lo impide (COMPLETO, INCOMPLETO, SIN_CONTESTAR),
 * porque se superó el tiempo máximo de resolución o porque se alcanzó
 * el límite máximo de accesos permitidos.
 */
public class CuestionarioBloqueadoException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO =
            "El cuestionario se encuentra bloqueado. No se permite el ingreso por estado inválido, tiempo agotado o límite de accesos superado.";

    /**
     * Crea la excepción con un mensaje descriptivo por defecto.
     */
    public CuestionarioBloqueadoException() {
        super(MENSAJE_POR_DEFECTO);
    }

    /**
     * Crea la excepción con un mensaje personalizado indicando el motivo del bloqueo.
     *
     * @param mensaje descripción específica del motivo por el cual el cuestionario está bloqueado.
     */
    public CuestionarioBloqueadoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje personalizado y la causa original del error.
     *
     * @param mensaje descripción específica del motivo del bloqueo.
     * @param causa   excepción original que provocó este error.
     */
    public CuestionarioBloqueadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

