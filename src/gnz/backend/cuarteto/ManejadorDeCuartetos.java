/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.cuarteto;

import gnz.backend.manejoDeDatos.Operacion;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeCuartetos {

    private ArrayList<Cuarteto> listaDeCuartetos;
    private boolean existioError;
    private int numeroDeTemporal;

    public ManejadorDeCuartetos() {
        this.listaDeCuartetos = new ArrayList<>();
        this.existioError = false;
        this.numeroDeTemporal = 1;
    }

    public String crearCuarteto(Operacion operando, String operador1, String operador2) {
        String temporal = "t" + this.numeroDeTemporal;
        numeroDeTemporal++;
        Cuarteto cuarteto = new Cuarteto(operando, operador1, operador2, temporal);
        this.listaDeCuartetos.add(cuarteto);
        return temporal;
    }

    public void escribirCuartetos(JTextArea txt) {
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (cuarteto.getOperando() == null && cuarteto.getOperador1() == null) {
                txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador2() + "\n");
            } else {
                txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + " " + cuarteto.getOperando().getSigno() + " " + cuarteto.getOperador2() + "\n");
            }
        }
        this.listaDeCuartetos.clear();
        this.numeroDeTemporal = 1;
    }

    public ArrayList<Cuarteto> getListaDeCuartetos() {
        return listaDeCuartetos;
    }

    public void setListaDeCuartetos(ArrayList<Cuarteto> listaDeCuartetos) {
        this.listaDeCuartetos = listaDeCuartetos;
    }

    public boolean existioError() {
        return existioError;
    }

    public void setExistioError(boolean existioErrorSemantico) {
        this.existioError = existioErrorSemantico;
    }

    public int getNumeroDeTemporal() {
        return numeroDeTemporal;
    }

    public void setNumeroDeTemporal(int numeroDeTemporal) {
        this.numeroDeTemporal = numeroDeTemporal;
    }

}
