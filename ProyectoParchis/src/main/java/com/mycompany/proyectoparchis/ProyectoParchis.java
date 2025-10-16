/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoparchis;

import controlador.ControladorAplicacion;
import modelo.Vestibulo;
import vista.VistaConsola;

/**
 *
 * @author PC
 */
public class ProyectoParchis {

    public static void main(String[] args) {
        Vestibulo vestibulo = new Vestibulo();
        VistaConsola vista = new VistaConsola();
        ControladorAplicacion controlador = new ControladorAplicacion(vestibulo, vista);
        controlador.ejecutar();
    }
}
