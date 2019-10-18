/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.instrucciones;

import gnz.backend.manejoDeDatos.DatoCodigo;

/**
 *
 * @author jesfrin
 */
public class DeclaracionDeVariable {

    private String nombreDeVariable;
    private String finCuarteto;
    private DatoCodigo datoCodigo;
    
    public DeclaracionDeVariable(String nombreDeVariable, String finCuarteto,DatoCodigo datoCodigo) {
        this.nombreDeVariable = nombreDeVariable;
        this.finCuarteto = finCuarteto;
        this.datoCodigo=datoCodigo;
    }

    public String getNombreDeVariable() {
        return nombreDeVariable;
    }

    public void setNombreDeVariable(String nombreDeVariable) {
        this.nombreDeVariable = nombreDeVariable;
    }

    public String getFinCuarteto() {
        return finCuarteto;
    }

    public void setFinCuarteto(String finCuarteto) {
        this.finCuarteto = finCuarteto;
    }

    public DatoCodigo getDatoCodigo() {
        return datoCodigo;
    }

    public void setDatoCodigo(DatoCodigo datoCodigo) {
        this.datoCodigo = datoCodigo;
    }

}
