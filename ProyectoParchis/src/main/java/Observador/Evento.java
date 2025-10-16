/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observador;

/**
 *
 * @author PC
 */
import java.util.HashMap;
import java.util.Map;

public class Evento {
    private final TipoEvento tipo;
    private final Map<String, Object> datos = new HashMap<>();

    private Evento(TipoEvento tipo) { this.tipo = tipo; }

    public static Evento de(TipoEvento tipo, Object... kv) {
        Evento e = new Evento(tipo);
        for (int i = 0; i + 1 < kv.length; i += 2) {
            e.datos.put(String.valueOf(kv[i]), kv[i + 1]);
        }
        return e;
    }

    public static Evento error(String msg) { return de(TipoEvento.ERROR, "razon", msg); }

    public TipoEvento getTipo() { return tipo; }
    public Map<String, Object> getDatos() { return datos; }
    public String getString(String clave) { Object v = datos.get(clave); return v == null ? null : v.toString(); }
}