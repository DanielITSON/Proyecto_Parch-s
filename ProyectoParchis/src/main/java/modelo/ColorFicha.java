/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PC
 */
import java.util.Arrays;
import java.util.stream.Collectors;

public enum ColorFicha {
    AZUL, ROJO, VERDE, AMARILLO;

    public static String lista() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.joining(", "));
    }
}