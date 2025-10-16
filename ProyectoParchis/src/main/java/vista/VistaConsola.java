/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Observador.Evento;
import Observador.Observador;
import Observador.TipoEvento;

/**
 *
 * @author PC
 */

public class VistaConsola implements Observador {

    public void mostrarMenu() {
        println("\n--- Menu ---");
        println("1) Registrar usuario");
        println("2) Unirse a sala por codigo");
        println("3) Crear sala");
        println("0) Salir");
        print("Opcion: ");
    }

   
    public void println(String s) { System.out.println(s); }
    public void print(String s) { System.out.print(s); }

    @Override
    public void actualizar(Evento e) {
        TipoEvento t = e.getTipo();
        if (t == TipoEvento.JUGADOR_REGISTRADO) {
            println("[OK] Usuario registrado: " + e.getString("usuario"));
        } else if (t == TipoEvento.SALA_CREADA) {
            println("[OK] Sala creada codigo=" + e.getString("codigo")
                    + " capacidad=" + e.getDatos().get("capacidad"));
        } else if (t == TipoEvento.UNION_ACEPTADA) {
            
            Object n = e.getDatos().get("cantidad");
            Object cap = e.getDatos().get("capacidad");
            println("[OK] Union a sala " + e.getString("codigo")
                    + " usuario=" + e.getString("usuario")
                    + " color=" + e.getString("color")
                    + " jugadores=(" + n + "/" + cap + ")");
        } else if (t == TipoEvento.SALA_LISTA) {
            println("[INFO] Sala lista para iniciar. codigo=" + e.getString("codigo")
                    + " jugadores=" + e.getDatos().get("cantidad"));
        } else if (t == TipoEvento.UNION_RECHAZADA || t == TipoEvento.ERROR) {
            println("[ERROR] " + e.getString("razon"));
        } else {
            println("[INFO] " + t);
        }
    }
}