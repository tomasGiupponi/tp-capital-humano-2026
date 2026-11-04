package com.grupo_3.tp_capital_humano.application.ports.in;

import com.grupo_3.tp_capital_humano.application.dto.CredencialesLoginDTO;
import com.grupo_3.tp_capital_humano.application.dto.ResultadoLoginDTO;

public interface EstrategiaAutenticacion {
    ResultadoLoginDTO autenticar(CredencialesLoginDTO credenciales);
}