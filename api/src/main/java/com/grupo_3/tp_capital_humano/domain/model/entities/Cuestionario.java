package com.grupo_3.tp_capital_humano.domain.model.entities;

import com.grupo_3.tp_capital_humano.domain.model.enums.NombreEstadoCuestionario;
import com.grupo_3.tp_capital_humano.domain.patterns.state.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representa el cuestionario de evaluación asignado a un candidato, gestionando
 * su acceso, tiempo y ciclo de vida.
 */
@Entity
@Table(name = "cuestionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evaluacion", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nro_candidato", nullable = false)
    private Candidato candidato;

    @Column(name = "clave", length = 10, nullable = false, unique = true)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private NombreEstadoCuestionario nombreEstado = NombreEstadoCuestionario.ACTIVO;

    @Transient
    @Builder.Default
    private EstadoCuestionario estado = new EstadoActivo();

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "ultimo_ingreso")
    private LocalDateTime ultimoIngreso;

    @Builder.Default
    @Column(name = "cantidad_accesos", nullable = false)
    private int cantidadAccesos = 0;

    @OneToMany(mappedBy = "cuestionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BloqueCuestionario> bloques = new ArrayList<>();

    /**
     * Al obtener el cuestionario desde la base de datos,
     * es necesario instanciar el estado en el que se encuentra.
     * Hicimos esto para que se almacene en la base de datos únicamente una columna
     * `VARCHAR` con el enum `NombreEstadoCuestionario`
     * y no tener que crear una tabla aparte para los estados.
     */
    @PostLoad
    public void rehidratarEstado() {
        if (this.nombreEstado != null) {
            this.estado = switch (this.nombreEstado) {
                case ACTIVO -> new EstadoActivo();
                case EN_PROCESO -> new EstadoEnProceso();
                case COMPLETO -> new EstadoCompleto();
                case INCOMPLETO -> new EstadoIncompleto();
                case SIN_CONTESTAR -> new EstadoSinContestar();
            };
        }
    }

    /**
     * Genera una clave aleatoria alfanumérica de 8 caracteres (CU 025).
     * 
     * @return retorna la clave aleatoria generada.
     */
    public static String generarClave() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = ThreadLocalRandom.current().nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Valida el tiempo transcurrido respecto a la fecha de finalización o límite
     * establecido.
     * 
     * @return true si el cuestionario aún se encuentra dentro del plazo permitido.
     */
    public boolean validarTiempo() {
        if (this.fechaHoraInicio == null) {
            return true;
        }
        if (this.fechaFin != null) {
            return LocalDateTime.now().isBefore(this.fechaFin);
        }
        return true;
    }

    /**
     * Valida el tiempo transcurrido respecto al límite máximo permitido (CU 026).
     * 
     * @param minutosMaximos minutos máximos permitidos para resolver el
     *                       cuestionario.
     * @return true si el cuestionario aún se encuentra en el tiempo límite.
     */
    public boolean validarTiempo(long minutosMaximos) {
        if (this.fechaHoraInicio == null) {
            return true;
        }
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = java.time.Duration.between(this.fechaHoraInicio, ahora).toMinutes();
        return minutosTranscurridos <= minutosMaximos;
    }

    /**
     * Realiza la transición al nuevo estado y mantiene sincronizada
     * la columna relacional nombreEstado para su persistencia en base de datos.
     * 
     * @param nuevoEstado nueva instancia de EstadoCuestionario.
     */
    public void cambiarEstado(EstadoCuestionario nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado != null) {
            this.nombreEstado = nuevoEstado.getNombre();
        }
    }

    /**
     * Actualiza el enum de estado y rehidrata la instancia en memoria.
     * 
     * @param nombreEstado nuevo valor del enum de estado.
     */
    public void setNombreEstado(NombreEstadoCuestionario nombreEstado) {
        this.nombreEstado = nombreEstado;
        rehidratarEstado();
    }

    /**
     * Incrementa la cantidad de accesos al cuestionario y actualiza la fecha y hora
     * del último ingreso.
     * Si es el primer acceso, establece además la fecha y hora de inicio.
     */
    public void incrementarAcceso() {
        this.cantidadAccesos++;
        this.ultimoIngreso = LocalDateTime.now();
        if (this.fechaHoraInicio == null) {
            this.fechaHoraInicio = this.ultimoIngreso;
        }
    }

    /**
     * Delega en el estado actual la comprobación de si se permite el ingreso al
     * cuestionario.
     * 
     * @return true si el estado actual permite el acceso al candidato.
     */
    public boolean permitirIngreso() {
        if (this.estado == null) {
            rehidratarEstado();
        }
        return this.estado != null && this.estado.permitirIngreso(this);
    }

    /**
     * Delega en el estado actual la finalización del cuestionario (transición a
     * Completo).
     */
    public void finalizar() {
        if (this.estado == null) {
            rehidratarEstado();
        }
        if (this.estado != null) {
            this.estado.finalizar(this);
        }
    }

    /**
     * Delega en el estado actual la transición por vencimiento de tiempo o límite
     * de accesos
     * (transición a Incompleto o SinContestar según corresponda).
     */

    public void vencer() {
        if (this.estado == null) {
            rehidratarEstado();
        }
        if (this.estado != null) {
            this.estado.vencer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Cuestionario that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}