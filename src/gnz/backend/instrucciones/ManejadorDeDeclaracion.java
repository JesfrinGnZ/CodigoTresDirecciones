/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.instrucciones;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.manejoDeDatos.Dato;
import gnz.backend.manejoDeDatos.DatoCodigo;
import gnz.backend.manejoDeDatos.DatoNumerico;
import gnz.backend.manejoDeDatos.TipoDeDato;
import gnz.backend.tablaDeSimbolos.ManejadorDeTablaDeSimbolos;
import gnz.frontend.editorDeTexto.EditorDeTextoFrame;
import java.util.ArrayList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeDeclaracion {

    private ArrayList<DeclaracionDeVariable> listaDeDeclaraciones;
    private final ManejadorDeCuartetos manCuarteto;
    private ManejadorDeTablaDeSimbolos manTabalDeSimbolos;
    private final EditorDeTextoFrame editor;

    public ManejadorDeDeclaracion(ArrayList<DeclaracionDeVariable> listaDeDeclaraciones, ManejadorDeCuartetos manCuarteto, ManejadorDeTablaDeSimbolos manTabalDeSimbolos, EditorDeTextoFrame editor) {
        this.listaDeDeclaraciones = listaDeDeclaraciones;
        this.manCuarteto = manCuarteto;
        this.editor = editor;
        this.manTabalDeSimbolos=manTabalDeSimbolos;
    }

    public void crearVariableComoCuarteto(int linea, int columna, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum) {
        String tFinal;
        for (DeclaracionDeVariable declaracion : listaDeDeclaraciones) {
            boolean seDebeSeguir = verificarYCrearTipoDeVariable(linea, columna, declaracion, tipoDeDatoDeclarado, datoNum, declaracion.getDatoCodigo());
            if (seDebeSeguir) {
                tFinal = declaracion.getFinCuarteto();//De esta forma habra asignacion
                if (tFinal != null) {
                    for (Cuarteto cuarteto : this.manCuarteto.getListaDeCuartetos()) {
                        if (cuarteto.getResultado().equals(tFinal)) {
                            int poscionFinal = this.manCuarteto.getListaDeCuartetos().indexOf(cuarteto);
                            this.manCuarteto.getListaDeCuartetos().add(poscionFinal + 1, new Cuarteto(null, null, tFinal, declaracion.getNombreDeVariable()));
                            break;
                        }
                    }
                }
            } else {
                break;
            }
        }
    }

    /**
     *
     * @param linea
     * @param columna
     * @param declaracion
     * @param tipoDeDatoDeclarado//int n<-
     * @param datoNum//SI es que es numerico si no sera null
     * @param datoAsignacion//El tipo de dato en la asignacion
     * @return
     */
    public boolean verificarYCrearTipoDeVariable(int linea, int columna, DeclaracionDeVariable declaracion, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum, DatoCodigo datoAsignacion) {
        //Verificar que la variable no haya sido creada
        if (manTabalDeSimbolos.buscarElemento(declaracion.getNombreDeVariable()) == null) {//La variable aun no existe
            if (datoAsignacion == null) {//No hubo asignacion
                //Ya solo se crea la variable
                manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado,datoNum, 1);
                return true;
            } else {
                if (tipoDeDatoDeclarado == TipoDeDato.CADENA && datoAsignacion.getDato() == TipoDeDato.CADENA) {
                    //Ya solo se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado,datoNum, 1);
                    return true;
                } else if (tipoDeDatoDeclarado == TipoDeDato.BOOLEANO && datoAsignacion.getDato() == TipoDeDato.BOOLEANO) {
                    //Ya solo se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado,datoNum, 1);
                    return true;
                } else if (tipoDeDatoDeclarado == TipoDeDato.NUMERICO && datoAsignacion.getDato() == TipoDeDato.NUMERICO) {//Es numerico
                    int n=datoNum.ordinal();
                    int n1=datoAsignacion.getDatoNumerico().ordinal();
                    if (datoNum.ordinal() >= datoAsignacion.getDatoNumerico().ordinal()) {//POs esta bien
                        //Ya solo se crea la variable
                        manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado,datoNum, 1);
                        return true;
                    } else {
                        //Error semantico, no se puede transformar
                        String mensaje = "No se puede transformar " + datoNum + " en " + tipoDeDatoDeclarado;
                        ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                    }
                } else {
                    //Error semantico,no se puede transformar
                    String mensaje = "No se puede transformar " + datoAsignacion + " en " + tipoDeDatoDeclarado;
                    ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                }
            }
        } else {
            //Error la variable ya ahasido declarada
            String mensaje = "La variable " + declaracion.getNombreDeVariable() + " ya ha sido declarada";
            ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);

        }
        return false;
    }

    public ArrayList<DeclaracionDeVariable> getListaDeDeclaraciones() {
        return listaDeDeclaraciones;
    }

    public void setListaDeDeclaraciones(ArrayList<DeclaracionDeVariable> listaDeDeclaraciones) {
        this.listaDeDeclaraciones = listaDeDeclaraciones;
    }

}
