package com.grupo_3.tp_capital_humano.application.ports.out.persistence;

import com.grupo_3.tp_capital_humano.domain.patterns.audit.RegistroAuditoria;
import com.grupo_3.tp_capital_humano.domain.patterns.audit.RegistroEjecucion;

public interface AuditoriaDAO {
    void registrar(RegistroAuditoria registroAuditoria);

    void registrarEjecucion(RegistroEjecucion registroEjecucion);
}
