package com.grupo_3.tp_capital_humano.application.dto;

import com.grupo_3.tp_capital_humano.domain.model.enums.TipoAcceso;
import com.grupo_3.tp_capital_humano.domain.model.enums.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredencialesLoginDTO {

    // CONSULTOR o CANDIDATO (define la estrategia a usar)
    private TipoAcceso tipoAcceso;

    // Campos para el login del Consultor (LDAP)
    private String usuario;
    private String password;

    // Campos para el login del Candidato (Cuestionario)
    private TipoDocumento tipoDocumento;
    private String nroDocumento;
    private String clave; // La clave alfanumérica de 8 caracteres
}