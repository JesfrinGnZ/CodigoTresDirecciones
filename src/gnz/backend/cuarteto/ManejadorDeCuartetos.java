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
public class ManejadorDeCuartetos {
    
    private ArrayList<Cuarteto> listaDeCuartetos;
    private boolean existioErrorSemantico;
    private int numeroDeTemporal;
    
    public ManejadorDeCuartetos(){
        this.listaDeCuartetos= new ArrayList<>();
        this.existioErrorSemantico=false;
        this.numeroDeTemporal=1;
    }

    public String crearCuarteto(Operacion operando,String operador1,String operador2){
        String temporal="t"+this.numeroDeTemporal;
        numeroDeTemporal++;
        Cuarteto cuarteto = new Cuarteto(operando, operador1, operador2, temporal);
        this.listaDeCuartetos.add(cuarteto);
        return temporal;
    }
    
    public ArrayList<Cuarteto> getListaDeCuartetos() {
        return listaDeCuartetos;
    }

    public void setListaDeCuartetos(ArrayList<Cuarteto> listaDeCuartetos) {
        this.listaDeCuartetos = listaDeCuartetos;
    }

    public boolean isExistioErrorSemantico() {
        return existioErrorSemantico;
    }

    public void setExistioErrorSemantico(boolean existioErrorSemantico) {
        this.existioErrorSemantico = existioErrorSemantico;
    }
    
    
    
}
