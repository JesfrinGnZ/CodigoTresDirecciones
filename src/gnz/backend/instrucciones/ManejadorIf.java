/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.instrucciones;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.cuarteto.TipoDeCuarteto;

/**
 *
 * @author jesfrin
 */
public class ManejadorIf {
    
    public static void arreglarIf(ManejadorDeCuartetos manCuarteto,Cuarteto cuarteto){
        //Se elimina la etiqueta que va solo en un IF
        int posicion=manCuarteto.getListaDeCuartetos().indexOf(cuarteto);
        manCuarteto.getListaDeCuartetos().remove(posicion+2);
        //Cambiar las etiquetas donde el resultado sea cambiar
        for (Cuarteto cuart : manCuarteto.getListaDeCuartetos()) {
            if(cuart.getResultado().equalsIgnoreCase("CAMBIAR")){
                cuart.setResultado(cuarteto.getResultado());
            }
        }
        //Se agrega la ultima etiqueta
        Cuarteto fin = new Cuarteto(null, null, null,cuarteto.getResultado(), TipoDeCuarteto.ETIQUETA);
        manCuarteto.getListaDeCuartetos().add(fin);
        
    }
    
}
