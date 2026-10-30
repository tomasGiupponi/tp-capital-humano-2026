package com.grupo_3.tp_capital_humano.domain.patterns.state;

import com.grupo_3.tp_capital_humano.domain.model.entities.Cuestionario;
import com.grupo_3.tp_capital_humano.domain.model.enums.NombreEstadoCuestionario;
import java.time.Duration;
import java.time.LocalDateTime;

public class EstadoEnProceso extends EstadoCuestionario {

    @Override
    public NombreEstadoCuestionario getNombre() {
        return NombreEstadoCuestionario.EN_PROCESO;
    }

    @Override
    public boolean permitirIngreso(Cuestionario cuestionario) {
        // 1. Validar límite de accesos / cortes de conexión
        if (cuestionario.getCantidadAccesos() >= cuestionario.getMaximoAccesosPermitidos()) {
            this.cambiarEstado(cuestionario, new EstadoIncompleto());
            return false;
        }

        // 2. Validar tiempo disponible
        if (!validarTiempo(cuestionario)) {
            return false;
        }

        cuestionario.incrementarAcceso();
        return true;
    }

    @Override
    public void finalizar(Cuestionario cuestionario) {
        // Transición 4: El candidato entregó el cuestionario exitosamente
        cuestionario.setFechaFin(LocalDateTime.now());
        this.cambiarEstado(cuestionario, new EstadoCompleto());
    }

    @Override
    public void vencer(Cuestionario cuestionario) {
        // Transición 6: El proceso batch lo encontró abierto pero con plazo vencido
        this.cambiarEstado(cuestionario, new EstadoIncompleto());
    }

    // Valida si sigue dentro de la ventana de minutos permitida
    public boolean validarTiempo(Cuestionario cuestionario) {
        if (cuestionario.getFechaHoraInicio() == null) {
            return false;
        }
        long minutosTranscurridos = Duration.between(cuestionario.getFechaHoraInicio(), LocalDateTime.now())
                .toMinutes();
        if (minutosTranscurridos > cuestionario.getTiempoMaximoMinutos()) {
            // Transición 5: Tiempo agotado
            this.cambiarEstado(cuestionario, new EstadoIncompleto());
            return false;
        }
        return true;
    }
}