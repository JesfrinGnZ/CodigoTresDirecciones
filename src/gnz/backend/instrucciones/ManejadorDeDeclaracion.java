/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.instrucciones;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.cuarteto.TipoDeCuarteto;
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

    private final ManejadorDeCuartetos manCuarteto;
    private final ManejadorDeTablaDeSimbolos manTabalDeSimbolos;
    private final EditorDeTextoFrame editor;

    public ManejadorDeDeclaracion(ManejadorDeCuartetos manCuarteto, ManejadorDeTablaDeSimbolos manTabalDeSimbolos, EditorDeTextoFrame editor) {
        this.manCuarteto = manCuarteto;
        this.editor = editor;
        this.manTabalDeSimbolos = manTabalDeSimbolos;
    }

    public void asignarValorABooleano(String nombreDeVariable, int linea, int columna) {//Verificar existencia de variable, para luego ver si se asigna el valor
        if (manTabalDeSimbolos.buscarElemento(nombreDeVariable) == null) {
            int tamanoDeLista = manCuarteto.getListaDeCuartetos().size();
            String label1 = manCuarteto.getListaDeCuartetos().get(tamanoDeLista - 2).getResultado();
            String label0 = manCuarteto.getListaDeCuartetos().get(tamanoDeLista - 1).getResultado();
            String labelrestoDeCodigo="L"+manCuarteto.generarEtiqueta();
            Cuarteto asig1 = new Cuarteto(null, null, null, label1, TipoDeCuarteto.ETIQUETA);
            Cuarteto asignacionBooleana1 = new Cuarteto(null, null, "1", nombreDeVariable, TipoDeCuarteto.EXPRESION);
            //Goto
            Cuarteto nuevaGoto = new Cuarteto(null, null, null, labelrestoDeCodigo, TipoDeCuarteto.GOTOETIQUETA);
            Cuarteto asig0 = new Cuarteto(null, null, null, label0, TipoDeCuarteto.ETIQUETA);
            Cuarteto asignacionBooleana0 = new Cuarteto(null, null, "0", nombreDeVariable, TipoDeCuarteto.EXPRESION);
            Cuarteto restoDeCodigo = new Cuarteto(null, null, null,labelrestoDeCodigo, TipoDeCuarteto.ETIQUETA);

            this.manCuarteto.getListaDeCuartetos().add(asig1);
            this.manCuarteto.getListaDeCuartetos().add(asignacionBooleana1);
            this.manCuarteto.getListaDeCuartetos().add(nuevaGoto);
            this.manCuarteto.getListaDeCuartetos().add(asig0);
            this.manCuarteto.getListaDeCuartetos().add(asignacionBooleana0);
            this.manCuarteto.getListaDeCuartetos().add(restoDeCodigo);
            manTabalDeSimbolos.crearElemento(linea, columna, nombreDeVariable, TipoDeDato.BOOLEANO, null, 1);

        } else {
            String mensaje = "La variable " + nombreDeVariable + " ya ha sido declarada";
            ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
        }

    }

    public void crearVariableComoCuarteto(DeclaracionDeVariable declaracion, int linea, int columna, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum) {
        String tFinal;
        boolean seDebeSeguir = verificarYCrearTipoDeVariable(linea, columna, declaracion, tipoDeDatoDeclarado, datoNum, declaracion.getDatoCodigo());
        if (seDebeSeguir) {
            tFinal = declaracion.getFinCuarteto();//De esta forma habra asignacion
            if (declaracion.getDatoCodigo() != null) {//De esta forma habra asignacion
                if (tFinal == null) {
                    this.manCuarteto.getListaDeCuartetos().add(new Cuarteto(null, null, declaracion.getDatoCodigo().getValor(), declaracion.getNombreDeVariable(), TipoDeCuarteto.EXPRESION));
                } else {
                    this.manCuarteto.getListaDeCuartetos().add(new Cuarteto(null, null, tFinal, declaracion.getNombreDeVariable(), TipoDeCuarteto.EXPRESION));
                }

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
                manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, 1);
                return true;
            } else {
                if (tipoDeDatoDeclarado == TipoDeDato.CADENA && datoAsignacion.getDato() == TipoDeDato.CADENA) {
                    //Ya solo se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, 1);
                    return true;
                } else if (tipoDeDatoDeclarado == TipoDeDato.BOOLEANO && datoAsignacion.getDato() == TipoDeDato.BOOLEANO) {
                    //Ya solo se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, 1);
                    return true;
                } else if (tipoDeDatoDeclarado == TipoDeDato.NUMERICO && datoAsignacion.getDato() == TipoDeDato.NUMERICO) {//Es numerico
                    int n = datoNum.ordinal();
                    int n1 = datoAsignacion.getDatoNumerico().ordinal();
                    if (datoNum.ordinal() >= datoAsignacion.getDatoNumerico().ordinal()) {//POs esta bien
                        //Ya solo se crea la variable
                        manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, 1);
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

}
