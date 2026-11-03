package com.grupo_3.tp_capital_humano.domain.exception;

/**
 * Excepción de dominio que se lanza cuando las ponderaciones asignadas
 * a las opciones de respuesta de una pregunta (CU 21 / CU 22) o a las
 * competencias de un puesto (CU 09) no cumplen con las reglas de negocio
 * (por ejemplo, valores fuera del rango 0 a 10, o suma distinta de 10).
 */
public class PonderacionInvalidaException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO =
            "La ponderación ingresada es inválida. Los valores deben estar entre 0 y 10, y la suma para una pregunta debe ser exactamente 10.";

    /**
     * Crea la excepción con un mensaje descriptivo por defecto.
     */
    public PonderacionInvalidaException() {
        super(MENSAJE_POR_DEFECTO);
    }

    /**
     * Crea la excepción con un mensaje personalizado indicando el detalle del error.
     *
     * @param mensaje descripción específica del error de ponderación.
     */
    public PonderacionInvalidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje personalizado y la causa original del error.
     *
     * @param mensaje descripción específica del error de ponderación.
     * @param causa   excepción original que provocó este error.
     */
    public PonderacionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

