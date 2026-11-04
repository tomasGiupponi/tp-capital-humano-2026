package com.grupo_3.tp_capital_humano.application.dto;

import com.grupo_3.tp_capital_humano.domain.model.enums.TipoAcceso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoLoginDTO {

    private boolean autenticado;
    private TipoAcceso tipoAcceso;

    // Datos de sesión si es Consultor
    private UUID consultorId;
    private String nombreUsuario;

    // Datos de sesión si es Candidato
    private UUID cuestionarioId;
    private String claveCuestionario;
}