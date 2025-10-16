/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PC
 */

import Observador.Evento;
import Observador.SoporteObservable;
import Observador.TipoEvento;


import java.util.HashMap;
import java.util.Map;

public class Vestibulo extends SoporteObservable {
    private final Map<String, Jugador> jugadores = new HashMap<>();
    private final Map<String, Sala> salas = new HashMap<>();

    public void registrarJugador(String usuario, String password) {
        if (usuario == null || usuario.isBlank() || password == null || password.isBlank()) {
            emitir(Evento.error("Campos faltantes"));
            return;
        }
        if (jugadores.containsKey(usuario)) {
            emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Usuario ya existe", "usuario", usuario));
            return;
        }
        Jugador j = new Jugador(usuario, password);
        jugadores.put(usuario, j);
        emitir(Evento.de(TipoEvento.JUGADOR_REGISTRADO, "usuario", usuario));
    }

   
    public void crearSala(String codigo, int capacidad, int segundosTurno, String usuarioCreador) {
        if (!jugadores.containsKey(usuarioCreador)) {
            emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Solo usuario registrado puede crear sala"));
            return;
        }
        if (salas.containsKey(codigo)) {
            emitir(Evento.error("Codigo de sala ya existe"));
            return;
        }
        try {
            Sala s = new Sala(codigo, capacidad, segundosTurno);
            salas.put(codigo, s);
            emitir(Evento.de(TipoEvento.SALA_CREADA, "codigo", codigo, "capacidad", capacidad));

            // Auto-asignar primer color disponible al creador y unirlo
            ColorFicha colorAuto = primerColorLibre(s);
            if (colorAuto == null) {
                emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "No hay color disponible para el creador"));
                return;
            }
            boolean ok = s.agregarJugador(usuarioCreador, colorAuto);
            if (!ok) {
                emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "No fue posible unir al creador"));
                return;
            }

            
            emitir(Evento.de(
                    TipoEvento.UNION_ACEPTADA,
                    "codigo", codigo,
                    "usuario", usuarioCreador,
                    "color", colorAuto.name(),
                    "cantidad", s.cantidadJugadores(),
                    "capacidad", s.getCapacidad()
            ));

            
            if (s.cantidadJugadores() >= 2) {
                emitir(Evento.de(TipoEvento.SALA_LISTA, "codigo", codigo, "cantidad", s.cantidadJugadores()));
            }
        } catch (IllegalArgumentException ex) {
            emitir(Evento.error(ex.getMessage()));
        }
    }

    public void unirseSala(String codigo, String usuario, ColorFicha color) {
        Sala sala = salas.get(codigo);
        if (sala == null) { emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Codigo invalido")); return; }
        if (sala.estaIniciada()) { emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Sala ya iniciada")); return; }

        Jugador j = jugadores.get(usuario);
        if (j == null) { emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Usuario no registrado")); return; }

        if (!sala.hayEspacio()) { emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Sala llena")); return; }
        if (!sala.colorDisponible(color)) { emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Color tomado")); return; }

        boolean ok = sala.agregarJugador(usuario, color);
        if (ok) {
            emitir(Evento.de(
                    TipoEvento.UNION_ACEPTADA,
                    "codigo", codigo,
                    "usuario", usuario,
                    "color", color.name(),
                    "cantidad", sala.cantidadJugadores(),
                    "capacidad", sala.getCapacidad()
            ));
            if (sala.cantidadJugadores() >= 2) {
                emitir(Evento.de(TipoEvento.SALA_LISTA, "codigo", codigo, "cantidad", sala.cantidadJugadores()));
            }
        } else {
            emitir(Evento.de(TipoEvento.UNION_RECHAZADA, "razon", "Fallo al unirse"));
        }
    }

    private ColorFicha primerColorLibre(Sala sala) {
        for (ColorFicha c : ColorFicha.values()) {
            if (sala.colorDisponible(c)) return c;
        }
        return null;
    }

    private void emitir(Evento e) { notificarObservadores(e); }
}