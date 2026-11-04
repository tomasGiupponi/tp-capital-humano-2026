package com.grupo_3.tp_capital_humano.application.service;

import com.grupo_3.tp_capital_humano.application.dto.CredencialesLoginDTO;
import com.grupo_3.tp_capital_humano.application.dto.ResultadoLoginDTO;
import com.grupo_3.tp_capital_humano.application.ports.in.EstrategiaAutenticacion;
import com.grupo_3.tp_capital_humano.domain.model.enums.TipoAcceso;
import com.grupo_3.tp_capital_humano.infrastructure.adapters.out.external.AutenticacionCandidatoClave;
import com.grupo_3.tp_capital_humano.infrastructure.adapters.out.external.AutenticacionConsultorLDAP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManejadorAutenticacion {

    private final AutenticacionConsultorLDAP estrategiaConsultor;
    private final AutenticacionCandidatoClave estrategiaCandidato;

    private EstrategiaAutenticacion estrategia;

    // Método explícito del diagrama DDS
    public void seleccionarEstrategia(EstrategiaAutenticacion estrategia) {
        this.estrategia = estrategia;
    }

    // Ejecuta el login delegando en la estrategia seleccionada
    public ResultadoLoginDTO autenticar(CredencialesLoginDTO credenciales) {
        // Si no se seteó manualmente, se autoselecciona según el tipo de acceso del
        // request
        if (this.estrategia == null) {
            if (credenciales.getTipoAcceso() == TipoAcceso.CONSULTOR) {
                this.estrategia = estrategiaConsultor;
            } else {
                this.estrategia = estrategiaCandidato;
            }
        }

        ResultadoLoginDTO resultado = this.estrategia.autenticar(credenciales);
        this.estrategia = null; // Limpia para el siguiente request
        return resultado;
    }
}