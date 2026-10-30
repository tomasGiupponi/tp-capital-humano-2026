package com.grupo_3.tp_capital_humano.domain.model.entities;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un usuario con perfil Consultor.
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario_consultor")
public class Consultor {
    private static final int MAX_LONGITUD_USUARIO = 15;
    private static final int MAX_LONGITUD_PASSWORD = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre_usuario", length = MAX_LONGITUD_USUARIO, nullable = false, unique = true)
    private String nombreUsuario;

    @Column(name = "password", nullable = false)
    private String password;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    /**
     * Valida que las credenciales del consultor cumplan las relgas del CU 01:
     * - nombreUsuario no nulo y <= 15 caracteres.
     * - password no nula y <= 20 caracteres.
     * 
     * @return true si el consultor posee credenciales válidas
     */
    public boolean autenticar() {
        boolean usuarioValido = this.nombreUsuario != null
                && !this.nombreUsuario.isBlank()
                && this.nombreUsuario.length() <= MAX_LONGITUD_USUARIO;

        boolean passwordValida = this.password != null
                && !this.password.isBlank()
                && this.password.length() <= MAX_LONGITUD_PASSWORD;

        return usuarioValido && passwordValida;
    }

    /**
     * Sobrecarga de método que valida si las credenciales ingresadas en el login
     * coinciden con las del consultor y cumplen las relgas de longitud de LDAP.
     * 
     * @param passwordIngresada string ingresado en el intento de login.
     * @return true si el estado del consultor es válido (autenticar()) y la
     *         contraseña coincide.
     */
    public boolean autenticar(String passwordIngresada) {
        if (passwordIngresada == null || passwordIngresada.length() > MAX_LONGITUD_PASSWORD) {
            return false;
        }

        return this.autenticar() && Objects.equals(this.password, passwordIngresada);
    }

    /**
     * Hook JPA para garantizar que los datos nombreUsuario y email
     * se persistan normalizados utilizando Builder
     */
    @PrePersist
    @PreUpdate
    private void normalizarDatos() {
        if (this.nombreUsuario != null) {
            this.nombreUsuario = this.nombreUsuario.trim().toUpperCase();
        }
        if (this.email != null) {
            this.email = this.email.trim().toLowerCase();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Consultor that))
            return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
