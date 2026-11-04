package com.grupo_3.tp_capital_humano.domain.patterns.audit;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//es lo que permite a las entidades tener una lista de obervadores y notificarlos
@MappedSuperclass
public abstract class SujetoAuditable {

    @Transient // Hibernate ignora esta lista en memoria RAM
    private List<ObservadorAuditoria> observadores = new ArrayList<>();

    public void registrarObservador(ObservadorAuditoria observador) {
        if (this.observadores == null) {
            this.observadores = new ArrayList<>();
        }
        this.observadores.add(observador);
    }

    public void eliminarObservador(ObservadorAuditoria observador) {
        if (this.observadores != null) {
            this.observadores.remove(observador);
        }
    }

    public void notificarObservadores(RegistroAuditoria registro) {
        if (this.observadores != null) {
            for (ObservadorAuditoria obs : this.observadores) {
                obs.actualizar(registro);
            }
        }
    }

    protected RegistroAuditoria construirRegistro(String usuario, String operacion) {
        return new RegistroAuditoria(
                usuario,
                LocalDateTime.now(),
                operacion,
                obtenerNombreEntidad(),
                obtenerIdEntidad());
    }

    // Métodos que cada clase hija implementa diciendo quién es
    protected abstract String obtenerNombreEntidad(); // ej: Puesto devuelve "PUESTO"

    protected abstract String obtenerIdEntidad(); // ej: Puesto devuelve this.codigo

    // Método que ejecuta la baja lógica
    public abstract void darDeBaja(String usuario);
}