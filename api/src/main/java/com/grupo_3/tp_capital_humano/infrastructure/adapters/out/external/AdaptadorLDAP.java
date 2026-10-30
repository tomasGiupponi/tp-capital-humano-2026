package com.grupo_3.tp_capital_humano.infrastructure.adapters.out.external;

import org.springframework.stereotype.Component;

@Component
public class AdaptadorLDAP {

    // Simula la autenticación contra Active Directory / LDAP corporativo
    public boolean validarCredenciales(String usuario, String password) {
        if (usuario == null || password == null) {
            return false;
        }
        // Para desarrollo/pruebas: acepta usuario no vacío con contraseña válida
        return !usuario.trim().isEmpty() && password.length() >= 4;
    }
}