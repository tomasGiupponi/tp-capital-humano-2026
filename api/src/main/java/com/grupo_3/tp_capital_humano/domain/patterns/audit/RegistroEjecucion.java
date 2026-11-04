package com.grupo_3.tp_capital_humano.domain.patterns.audit;

import com.grupo_3.tp_capital_humano.domain.model.enums.AccionEjecucion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registro_ejecucion")
@Getter
@NoArgsConstructor
public class RegistroEjecucion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fecha_hora", nullable = false) // guarda en la columa fecha_hora de la tabla "registro_ejecucion" lo
                                                   // que viene dentro de fechaHora
                                                   // si es null el valor, tira un error (no acepta valores nulos, por
                                                   // eso el nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion", nullable = false)
    private AccionEjecucion accion; // INICIO o FIN

    public RegistroEjecucion(LocalDateTime fechaHora, AccionEjecucion accion) {
        this.fechaHora = fechaHora;
        this.accion = accion;
    }
}