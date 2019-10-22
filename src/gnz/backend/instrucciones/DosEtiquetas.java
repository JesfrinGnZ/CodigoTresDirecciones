/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.instrucciones;

import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.manejoDeDatos.DatoCodigo;
import gnz.backend.manejoDeDatos.Operacion;
import gnz.backend.manejoDeDatos.TipoDeDato;
import gnz.frontend.editorDeTexto.EditorDeTextoFrame;

/**
 *
 * @author jesfrin
 */
public class DosEtiquetas {

    private DatoCodigo dato1;
    private DatoCodigo dato2;
    private Boolean tipoDeBoolean;
    private Operacion operacion;

 

   
   
    
    public DosEtiquetas(DatoCodigo dato1, DatoCodigo dato2, Boolean tipoDeBoolean, Operacion operacion, boolean seDebeVerificarQueSeaNumerico,EditorDeTextoFrame editor,ManejadorDeCuartetos manCuarteto) {
        if (seDebeVerificarQueSeaNumerico) {
            if (dato1.getDato() != TipoDeDato.NUMERICO || dato2.getDato() != TipoDeDato.NUMERICO) {
                String mensaje="No se pueden comparar tipos que no sean numericos";
                ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, dato1.getNumeroDeLinea(), dato1.getNumeroDeColumna(), manCuarteto);
            }
        }
        this.dato1 = dato1;
        this.dato2 = dato2;
        this.tipoDeBoolean = tipoDeBoolean;
        this.operacion = operacion;
    }

     
    
    public DatoCodigo getDato1() {
        return dato1;
    }

    public void setDato1(DatoCodigo dato1) {
        this.dato1 = dato1;
    }

    public DatoCodigo getDato2() {
        return dato2;
    }

    public void setDato2(DatoCodigo dato2) {
        this.dato2 = dato2;
    }

    public boolean isTipoDeBoolean() {
        return tipoDeBoolean;
    }

    public void setTipoDeBoolean(boolean tipoDeBoolean) {
        this.tipoDeBoolean = tipoDeBoolean;
    }

    public Boolean getTipoDeBoolean() {
        return tipoDeBoolean;
    }

    public void setTipoDeBoolean(Boolean tipoDeBoolean) {
        this.tipoDeBoolean = tipoDeBoolean;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    
}
