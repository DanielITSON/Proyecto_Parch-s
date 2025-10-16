/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PC
 */
import java.util.*;

public class Sala {
    private final String codigo;
    private final int capacidad;
    private final int segundosPorTurno;
    private final Map<String, ColorFicha> jugadores = new LinkedHashMap<>();
    private final Set<ColorFicha> coloresTomados = new HashSet<>();
    private boolean iniciada = false;

    public Sala(String codigo, int capacidad, int segundosPorTurno) {
        this.codigo = Objects.requireNonNull(codigo);
        if (capacidad < 2 || capacidad > 4) throw new IllegalArgumentException("Capacidad debe ser 2-4");
        this.capacidad = capacidad;
        this.segundosPorTurno = Math.max(10, segundosPorTurno);
    }

    public String getCodigo() { return codigo; }
    public int getCapacidad() { return capacidad; }
    public boolean estaIniciada() { return iniciada; }
    public void marcarIniciada() { this.iniciada = true; }

    public synchronized boolean hayEspacio() {
        return jugadores.size() < capacidad && !iniciada;
    }

    public synchronized boolean colorDisponible(ColorFicha color) {
        return !coloresTomados.contains(color);
    }

    public synchronized boolean agregarJugador(String usuario, ColorFicha color) {
        if (iniciada) return false;
        if (jugadores.containsKey(usuario)) return false;
        if (!hayEspacio()) return false;
        if (!colorDisponible(color)) return false;
        jugadores.put(usuario, color);
        coloresTomados.add(color);
        return true;
    }

    public synchronized int cantidadJugadores() {
        return jugadores.size();
    }

    public synchronized Map<String, ColorFicha> snapshotJugadores() {
        return new LinkedHashMap<>(jugadores);
    }
}
