package com.grupo_3.tp_capital_humano.infrastructure.adapters.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grupo_3.tp_capital_humano.domain.exception.CandidatoConCuestionarioActivoException;
import com.grupo_3.tp_capital_humano.domain.exception.CuestionarioBloqueadoException;
import com.grupo_3.tp_capital_humano.domain.exception.PonderacionInvalidaException;
import com.grupo_3.tp_capital_humano.domain.exception.PreguntasInsuficientesPorFactorException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Manejador global de excepciones para los controladores REST de la aplicación.
 * Intercepta las excepciones de reglas de negocio y de validación para devolver
 * respuestas JSON uniformes con los códigos de estado HTTP correspondientes
 * (400 Bad Request, 404 Not Found, 409 Conflict, 500 Internal Server Error).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Estructura estándar de respuesta JSON ante cualquier error en la API REST.
     *
     * @param timestamp          fecha y hora en que se produjo el error.
     * @param status             código numérico de estado HTTP (400, 404, 409, 500).
     * @param error              descripción estándar del estado HTTP.
     * @param mensaje            mensaje descriptivo del error para mostrar al usuario.
     * @param path               ruta del endpoint donde ocurrió el error.
     * @param erroresValidacion  detalle de errores por campo (solo presente en fallos de validación @Valid).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String mensaje,
            String path,
            Map<String, String> erroresValidacion
    ) {
    }

    /**
     * Captura errores de validación de DTOs anotados con {@code @Valid}
     * (por ejemplo {@code @NotNull}, {@code @NotBlank}, {@code @Size}, {@code @Min}, {@code @Max}).
     *
     * @param ex      excepción lanzada por Spring MVC al fallar la validación de argumentos.
     * @param request petición HTTP original.
     * @return respuesta HTTP 400 (Bad Request) con el detalle de los campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacionArgumentos(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> erroresPorCampo = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erroresPorCampo.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación en los datos enviados. Revise los campos informados.",
                request.getRequestURI(),
                erroresPorCampo
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    /**
     * Captura errores de ponderación inválida (CU 09, CU 21, CU 22) y argumentos inválidos
     * de dominio (por ejemplo ponderaciones negativas o fuera del rango 0 a 10).
     *
     * @param ex      excepción de ponderación o argumento inválido.
     * @param request petición HTTP original.
     * @return respuesta HTTP 400 (Bad Request).
     */
    @ExceptionHandler({
            PonderacionInvalidaException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> manejarDatosInvalidos(
            RuntimeException ex,
            HttpServletRequest request) {

        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Captura errores cuando no se encuentra un recurso solicitado en la base de datos.
     *
     * @param ex      excepción de entidad o elemento no encontrado.
     * @param request petición HTTP original.
     * @return respuesta HTTP 404 (Not Found).
     */
    @ExceptionHandler({
            EntityNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<ErrorResponse> manejarRecursoNoEncontrado(
            RuntimeException ex,
            HttpServletRequest request) {

        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Captura conflictos con las reglas de negocio y el estado actual de las entidades:
     * <ul>
     *   <li>{@link CuestionarioBloqueadoException}: intento de ingreso o avance en cuestionario bloqueado/vencido (CU 01, CU 26).</li>
     *   <li>{@link CandidatoConCuestionarioActivoException}: candidato ya posee evaluación activa o en proceso (CU 25).</li>
     *   <li>{@link PreguntasInsuficientesPorFactorException}: factor con menos de 2 preguntas activas al evaluar puesto (CU 25).</li>
     *   <li>{@link IllegalStateException}: transición de estado inválida en el ciclo de vida del cuestionario.</li>
     * </ul>
     *
     * @param ex      excepción de conflicto de negocio o estado.
     * @param request petición HTTP original.
     * @return respuesta HTTP 409 (Conflict).
     */
    @ExceptionHandler({
            CuestionarioBloqueadoException.class,
            CandidatoConCuestionarioActivoException.class,
            PreguntasInsuficientesPorFactorException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponse> manejarConflictoDeNegocio(
            RuntimeException ex,
            HttpServletRequest request) {

        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Captura cualquier otra excepción no prevista para evitar exponer trazas internas
     * y mantener el formato JSON uniforme en toda la API.
     *
     * @param ex      excepción inesperada.
     * @param request petición HTTP original.
     * @return respuesta HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(
            Exception ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno inesperado en el servidor: " + ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Método auxiliar para construir la respuesta JSON estandarizada.
     */
    private ResponseEntity<ErrorResponse> construirRespuesta(
            HttpStatus status,
            String mensaje,
            String path) {

        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                path,
                null
        );
        return ResponseEntity.status(status).body(cuerpo);
    }
}

