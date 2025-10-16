/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PC
 */
import java.util.Objects;

public class Jugador {
    private final String usuario;
    private final String hashPassword;

    public Jugador(String usuario, String passwordPlano) {
        this.usuario = Objects.requireNonNull(usuario);
        this.hashPassword = "h:" + passwordPlano;
    }

    public String getUsuario() { return usuario; }
    public String getHashPassword() { return hashPassword; }
}
