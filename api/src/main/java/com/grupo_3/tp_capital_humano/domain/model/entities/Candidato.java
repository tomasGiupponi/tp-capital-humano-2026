package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.time.LocalDate;
import java.util.Objects;

import com.grupo_3.tp_capital_humano.domain.model.enums.Genero;
import com.grupo_3.tp_capital_humano.domain.model.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa al usuario con perfil candidato
 */

@Entity
@Table(name = "candidato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Candidato {
    @Id
    @Column(name = "nro_candidato", nullable = false, updatable = false)
    private Integer nroCandidato;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_doc", length = 10, nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "nro_doc", length = 20, nullable = false)
    private String nroDocumento;

    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 1, nullable = false)
    private Genero genero;

    @Column(name = "nacionalidad", length = 50, nullable = false)
    private String nacionalidad;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "escolaridad", length = 100, nullable = false)
    private String escolaridad;

    @Builder.Default
    @Column(name = "eliminado", nullable = false)
    private boolean eliminado = false;

    /**
     * Retorna el tipo y número de documento concatenados.
     * 
     * @return "tipo" + " " + "número"
     */
    public String getDocumentoCompleto() {
        if (this.tipoDocumento == null || this.nroDocumento == null) {
            return "";
        }

        return this.tipoDocumento.name() + " " + this.nroDocumento.trim();
    }

    /**
     * Comprueba si el tipo y número de documento ingresados en el login
     * pertenecen a este candidato activo.
     * 
     * @param tipoIngresado string con el tipo de documento ingresado en el intento
     *                      de login
     * @param nroIngresado  string con el número de documento ingresado en el
     *                      intento de login
     * @return true si el tipo y número de documento ingresados en el login
     *         pertenencen a este candidato activo
     *         y el número de documento almacenado no es nulo.
     */
    public boolean validarDocumento(TipoDocumento tipoIngresado, String nroIngresado) {
        if (this.eliminado || tipoIngresado == null || nroIngresado.isBlank()) {
            return false;
        }

        return this.tipoDocumento == tipoIngresado
                && this.nroDocumento != null
                && this.nroDocumento.trim().equalsIgnoreCase(nroIngresado.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Candidato that))
            return false;
        return nroCandidato != null && Objects.equals(nroCandidato, that.nroCandidato);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
