package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.grupo_3.tp_capital_humano.domain.model.enums.TipoPregunta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una pregunta de evaluación asociada a un factor y a una escala de opciones.
 */
@Entity
@Table(name = "pregunta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pregunta extends SujetoAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "enunciado", columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pregunta", length = 30, nullable = false)
    private TipoPregunta tipoPregunta;

    @Builder.Default
    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_opcion_respuesta", nullable = false)
    private OpcionDeRespuesta opcionDeRespuesta;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PreguntaValorOpcion> ponderacionesValores = new ArrayList<>();

    /**
     * Obtiene la ponderación específica asignada a un valor de opción dentro de
     * esta pregunta,
     * cumpliendo con la regla de que las ponderaciones de las opciones suman 10
     * puntos en total (CU 021).
     * 
     * @param ValorOpcion valor de la opción seleccionado por el candidato.
     * @return retorna la ponderación específica de la opción seleccionada.
     */
    public double obtenerPonderacionParaValor(ValorOpcion ValorOpcion) {
        if (this.ponderacionesValores == null || ValorOpcion == null) {
            return 0.0;
        }

        return this.ponderacionesValores.stream()
                .filter(pv -> pv.getValorOpcion() != null && pv.getValorOpcion.equals(ValorOpcion))
                .mapToDouble(PreguntaValorOpcion::getPonderacion)
                .findFirst()
                .orElse(0.0);
    }

    /**
     * Coloca el campo activa en false, registra este cambio en RegistroAuditoria
     * asignando el consultor que
     * hizo el cambio y notifica a todos las instancias que formen parte del
     * Observer.
     * 
     * @param consultor instancia que se persiste como el consultor que generó la
     *                  acción.
     */
    @Override
    public void darDeBaja(Consultor consultor) {
        if (this.activa) {
            this.activa = false;
            RegistroAuditoria reg = construirRegistro(consultor, "BAJA_LOGICA", "Pregunta",
                    this.id != null ? this.id.toString() : null);
            notificarObservadores(reg);
        }
    }

    /**
     * transforma nombre y enunciado al formato correspondiente.
     * - nombre queda todo junto.
     * - enunciado queda todo junto.
     */
    @PrePersist
    @PreUpdate
    private void normalizar() {
        if (this.nombre != null) {
            this.nombre = this.nombre.trim();
        }
        if (this.enunciado != null) {
            this.enunciado = this.enunciado.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pregunta that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
