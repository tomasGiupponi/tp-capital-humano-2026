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
public class AutenticacionConsultorLDAP implements EstrategiaAutenticacion {

    private final AdaptadorLDAP directorio;

    @Override
    public ResultadoLoginDTO autenticar(CredencialesLoginDTO credenciales) {
        boolean esValido = directorio.validarCredenciales(
                credenciales.getUsuario(),
                credenciales.getPassword());

        if (!esValido) {
            return ResultadoLoginDTO.builder()
                    .autenticado(false)
                    .tipoAcceso(TipoAcceso.CONSULTOR)
                    .build();
        }

        return ResultadoLoginDTO.builder()
                .autenticado(true)
                .tipoAcceso(TipoAcceso.CONSULTOR)
                .consultorId(UUID.randomUUID()) // ID del consultor autenticado
                .nombreUsuario(credenciales.getUsuario())
                .build();
    }
}