package com.grupo_3.tp_capital_humano.domain.patterns.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

//es como un DTO
@Entity
@Table(name = "registro_auditoria") // sabe que es la tabla de SQL
@Getter
@NoArgsConstructor
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "operacion", nullable = false)
    private String operacion; // "BAJA_LOGICA"

    @Column(name = "entidad", nullable = false)
    private String entidad; // "PUESTO", "PREGUNTA", etc.

    @Column(name = "id_entidad", nullable = false)
    private String idEntidad; // El código o ID eliminado

    public RegistroAuditoria(String usuario, LocalDateTime fechaHora, String operacion, String entidad,
            String idEntidad) {
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.operacion = operacion;
        this.entidad = entidad;
        this.idEntidad = idEntidad;
    }
}