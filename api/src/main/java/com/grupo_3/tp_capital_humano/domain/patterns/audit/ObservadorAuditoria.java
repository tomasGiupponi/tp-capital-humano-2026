package com.grupo_3.tp_capital_humano.domain.patterns.audit;

//todos los que quieran escuchar tienen que implementar esta interfaz.
public interface ObservadorAuditoria {
    void actualizar(RegistroAuditoria registro);
}