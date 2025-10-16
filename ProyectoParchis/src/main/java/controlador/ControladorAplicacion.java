/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author PC
 */

import java.util.Scanner;
import modelo.ColorFicha;
import modelo.Vestibulo;
import vista.VistaConsola;

public class ControladorAplicacion {
    private final Vestibulo vestibulo;
    private final VistaConsola vista;
    private final Scanner entrada = new Scanner(System.in);

    public ControladorAplicacion(Vestibulo vestibulo, VistaConsola vista) {
        this.vestibulo = vestibulo;
        this.vista = vista;
        this.vestibulo.agregarObservador(vista);
        
    }

    public void ejecutar() {
        boolean on = true;
        while (on) {
            vista.mostrarMenu();
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1" -> registrar();
                case "2" -> unirseSala();
                case "3" -> crearSala();          
                case "0" -> on = false;
                default -> vista.println("Opcion invalida.");
            }
        }
        vista.println("Saliendo...");
    }

    private void registrar() {
        vista.print("Usuario: ");
        String usuario = entrada.nextLine().trim();
        vista.print("Password: ");
        String pass = entrada.nextLine().trim();
        vestibulo.registrarJugador(usuario, pass);
    }

    private void unirseSala() {
        vista.print("Codigo de sala: ");
        String codigo = entrada.nextLine().trim();
        vista.println("Colores disponibles: " + ColorFicha.lista());
        vista.print("Elige color: ");
        String colorStr = entrada.nextLine().trim().toUpperCase();
        ColorFicha color;
        try {
            color = ColorFicha.valueOf(colorStr);
        } catch (IllegalArgumentException e) {
            vista.println("Color invalido.");
            return;
        }
        vista.print("Usuario (debe estar registrado): ");
        String usuario = entrada.nextLine().trim();
        vestibulo.unirseSala(codigo, usuario, color);
    }

    private void crearSala() {
        vista.print("Usuario creador (debe estar registrado): ");
        String usuarioCreador = entrada.nextLine().trim();

        vista.print("Nuevo codigo de sala: ");
        String codigo = entrada.nextLine().trim();

        vista.print("Capacidad (2-4): ");
        int capacidad = parseIntSeguro(entrada.nextLine().trim(), 4);

        vista.print("Tiempo por turno (seg): ");
        int segs = parseIntSeguro(entrada.nextLine().trim(), 60);

        
        vestibulo.crearSala(codigo, capacidad, segs, usuarioCreador);
    }

    private int parseIntSeguro(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}