/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observador;

/**
 *
 * @author PC
 */
import java.util.ArrayList;
import java.util.List;

public class SoporteObservable {
    private final List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) { if (o != null && !observadores.contains(o)) observadores.add(o); }
    public void removerObservador(Observador o) { observadores.remove(o); }

    protected void notificarObservadores(Evento e) {
        for (Observador o : List.copyOf(observadores)) {
            try { o.actualizar(e); } catch (Exception ignore) {}
        }
    }
}
