/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.cuarteto;

import gnz.backend.manejoDeDatos.Operacion;
import java.util.ArrayList;

/**
 *
 * @author jesfrin
 */
public class Cuarteto {

    private Operacion operando;
    private String operador1;
    private String operador2;
    private String resultado;

    public Cuarteto(Operacion operando, String operador1, String operador2, String resultado) {
        this.operando = operando;
        this.operador1 = operador1;
        this.operador2 = operador2;
        this.resultado = resultado;
    }

    public static void escribirCuartetos(ArrayList<Cuarteto> listaDeCuartetos) {
        System.out.println("Escribiendo 3D en base a cuartetos");
        for (Cuarteto cuarteto : listaDeCuartetos) {
            System.out.print(cuarteto.getResultado() + "=");
            System.out.print(cuarteto.getOperador1());
            if (cuarteto.getOperando() != null) {
                System.out.print(cuarteto.getOperando());
            }
            if (cuarteto.getOperador2() != null) {
                System.out.print(cuarteto.getOperador2());
            }
            System.out.println("");
        }
    }

    public Operacion getOperando() {
        return operando;
    }

    public void setOperando(Operacion operando) {
        this.operando = operando;
    }

    public String getOperador1() {
        return operador1;
    }

    public void setOperador1(String operador1) {
        this.operador1 = operador1;
    }

    

    public String getOperador2() {
        return operador2;
    }

    public String getResultado() {
        return resultado;
    }
}
