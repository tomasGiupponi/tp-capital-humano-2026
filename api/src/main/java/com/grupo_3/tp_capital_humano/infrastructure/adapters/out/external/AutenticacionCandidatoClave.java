package com.grupo_3.tp_capital_humano.infrastructure.adapters.out.external;

import com.grupo_3.tp_capital_humano.application.dto.CredencialesLoginDTO;
import com.grupo_3.tp_capital_humano.application.dto.ResultadoLoginDTO;
import com.grupo_3.tp_capital_humano.application.ports.in.EstrategiaAutenticacion;
import com.grupo_3.tp_capital_humano.domain.model.enums.TipoAcceso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutenticacionCandidatoClave implements EstrategiaAutenticacion {

    // Nota: Se comunicará con GestionCuestionarios o CuestionarioDAO
    @Override
    public ResultadoLoginDTO autenticar(CredencialesLoginDTO credenciales) {
        // Valida que venga la clave de 8 caracteres y el documento
        if (credenciales.getClave() == null || credenciales.getClave().trim().isEmpty()) {
            return ResultadoLoginDTO.builder()
                    .autenticado(false)
                    .tipoAcceso(TipoAcceso.CANDIDATO)
                    .build();
        }

        // Simulación de éxito de candidato (se conectará al DAO en Fase 6)
        return ResultadoLoginDTO.builder()
                .autenticado(true)
                .tipoAcceso(TipoAcceso.CANDIDATO)
                .cuestionarioId(UUID.randomUUID())
                .claveCuestionario(credenciales.getClave().toUpperCase())
                .build();
    }
}