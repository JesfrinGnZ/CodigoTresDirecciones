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
import gnz.backend.tablaDeSimbolos.ElementoDeTablaDeSimbolos;
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

    public void darValorABooleano(String nombreDeVariable, int linea, int columna) {
        //String label1 = manCuarteto.getListaDeCuartetos().get(tamanoDeLista - 2).getResultado();
        //String label0 = manCuarteto.getListaDeCuartetos().get(tamanoDeLista - 1).getResultado();

        String label1 = "L" + this.manCuarteto.generarGoto()[1];
        String label0 = "L" + this.manCuarteto.generarGoto()[0];

        String labelrestoDeCodigo = "L" + manCuarteto.generarEtiqueta();
        Cuarteto asig1 = new Cuarteto(null, null, null, label1, TipoDeCuarteto.ETIQUETA);
        Cuarteto asignacionBooleana1 = new Cuarteto(null, null, "1", nombreDeVariable, TipoDeCuarteto.EXPRESION);
        //Goto
        Cuarteto nuevaGoto = new Cuarteto(null, null, null, labelrestoDeCodigo, TipoDeCuarteto.GOTOETIQUETA);
        Cuarteto asig0 = new Cuarteto(null, null, null, label0, TipoDeCuarteto.ETIQUETA);
        Cuarteto asignacionBooleana0 = new Cuarteto(null, null, "0", nombreDeVariable, TipoDeCuarteto.EXPRESION);
        Cuarteto restoDeCodigo = new Cuarteto(null, null, null, labelrestoDeCodigo, TipoDeCuarteto.ETIQUETA);

        this.manCuarteto.getListaDeCuartetos().add(asig1);
        this.manCuarteto.getListaDeCuartetos().add(asignacionBooleana1);
        this.manCuarteto.getListaDeCuartetos().add(nuevaGoto);
        this.manCuarteto.getListaDeCuartetos().add(asig0);
        this.manCuarteto.getListaDeCuartetos().add(asignacionBooleana0);
        this.manCuarteto.getListaDeCuartetos().add(restoDeCodigo);
        manTabalDeSimbolos.crearElemento(linea, columna, nombreDeVariable, TipoDeDato.BOOLEANO, null, "1", false);

    }

    public void asignarValorABooleano(boolean esDeclaracion, String nombreDeVariable, int linea, int columna) {//Verificar existencia de variable, para luego ver si se asigna el valor
        if (!manCuarteto.existioError()) {
            if (esDeclaracion) {
                if (manTabalDeSimbolos.buscarElemento(nombreDeVariable) == null) {
                    darValorABooleano(nombreDeVariable, linea, columna);
                } else {
                    String mensaje = "La variable " + nombreDeVariable + " ya ha sido declarada";
                    ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                }
            } else {
                darValorABooleano(nombreDeVariable, linea, columna);
            }

        }
    }

    public void crearVariableComoCuarteto(boolean esAsignacion, DeclaracionDeVariable declaracion, int linea, int columna, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum) {
        String tFinal;
        boolean seDebeSeguir = verificarYCrearTipoDeVariable(esAsignacion, linea, columna, declaracion, tipoDeDatoDeclarado, datoNum, declaracion.getDatoCodigo());
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
    public boolean verificarYCrearTipoDeVariable(boolean esDeclaracion, int linea, int columna, DeclaracionDeVariable declaracion, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum, DatoCodigo datoAsignacion) {
        //Verificar que la variable no haya sido creada
        if (esDeclaracion) {
            if (manTabalDeSimbolos.buscarElemento(declaracion.getNombreDeVariable()) == null) {//La variable aun no existe
                return crearVarificarTipo(linea, columna, declaracion, tipoDeDatoDeclarado, datoNum, datoAsignacion);
            } else {
                //Error la variable ya ahasido declarada
                String mensaje = "La variable " + declaracion.getNombreDeVariable() + " ya ha sido declarada";
                ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
            }
            return false;
        } else {
            return crearVarificarTipo(linea, columna, declaracion, tipoDeDatoDeclarado, datoNum, datoAsignacion);
        }
    }

    private boolean crearVarificarTipo(int linea, int columna, DeclaracionDeVariable declaracion, TipoDeDato tipoDeDatoDeclarado, DatoNumerico datoNum, DatoCodigo datoAsignacion) {
        if (datoAsignacion == null) {//No hubo asignacion
            //Ya solo se crea la variable
            manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, "1", false);
            return true;
        } else {
            if (tipoDeDatoDeclarado == TipoDeDato.CADENA && datoAsignacion.getDato() == TipoDeDato.CADENA) {
                //Ya solo se crea la variable
                manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, "1", false);
                return true;
            } else if (tipoDeDatoDeclarado == TipoDeDato.BOOLEANO && datoAsignacion.getDato() == TipoDeDato.BOOLEANO) {
                //Ya solo se crea la variable
                manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, "1", false);
                return true;
            } else if (tipoDeDatoDeclarado == TipoDeDato.NUMERICO && datoAsignacion.getDato() == TipoDeDato.NUMERICO) {//Es numerico
                int n = datoNum.ordinal();
                int n1 = datoAsignacion.getDatoNumerico().ordinal();
                if (datoNum.ordinal() >= datoAsignacion.getDatoNumerico().ordinal()) {//POs esta bien
                    //Ya solo se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, declaracion.getNombreDeVariable(), tipoDeDatoDeclarado, datoNum, "1", false);
                    return true;
                } else {
                    //Error semantico, no se puede transformar
                    String mensaje = "No se puede transformar " + datoNum.name() + " en " + tipoDeDatoDeclarado.name();
                    ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                }
            } else {
                //Error semantico,no se puede transformar
                String mensaje = "No se puede transformar " + datoAsignacion.getDato().name() + " en " + tipoDeDatoDeclarado;
                ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
            }
        }
        return false;
    }

    public void declararArreglo(ArrayList<String> nombresDeArreglos, int linea, int columna, TipoDeDato tipoDeDato, DatoNumerico datoNumerico, String dimension) {
        if (!manCuarteto.existioError()) {
            for (String nombreDeArreglo : nombresDeArreglos) {
                if (manTabalDeSimbolos.buscarElemento(nombreDeArreglo) != null) {//Ya existe la variable
                    String mensaje = "El arreglo ya ha sido declarado";
                    ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                    break;
                } else {//Se crea la variable
                    manTabalDeSimbolos.crearElemento(linea, columna, nombreDeArreglo, tipoDeDato, datoNumerico, dimension, true);
                }
            }
        }

    }

    public boolean asignarValorAArreglo(DatoCodigo asignacion, ElementoDeTablaDeSimbolos elemento, String nombreDeVariable, String posicionDeArreglo, TipoDeDato tipoDeDato, DatoNumerico datoNUm, int linea, int columna) {
        if (!this.manCuarteto.existioError()) {
            if (elemento.getTipoBase() == tipoDeDato) {
                String resultadoAsignacion;
                if (asignacion.getTemporal() != null) {
                    resultadoAsignacion = asignacion.getTemporal();
                } else {
                    resultadoAsignacion = asignacion.getValor();
                }

                if (elemento.getTipoBase() == asignacion.getDato()) {

                    if (elemento.getTipoBase() == TipoDeDato.NUMERICO) {
                        if (elemento.getTipoNumerico().ordinal() < asignacion.getDatoNumerico().ordinal()) {
                            String mensaje = "Tipos no comparaibles " + asignacion.getDatoNumerico().name() + " no se puede convertir en " + elemento.getTipoNumerico().name();
                            ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                            return false;
                        }
                    }

                    manTabalDeSimbolos.crearElemento(linea, columna, nombreDeVariable, tipoDeDato, datoNUm, nombreDeVariable, false);
                    Cuarteto c = new Cuarteto(null, nombreDeVariable, posicionDeArreglo, resultadoAsignacion, TipoDeCuarteto.ASIGARREGLO);
                    manCuarteto.getListaDeCuartetos().add(c);

                } else {
                    String mensaje = "Tipos no comparaibles " + elemento.getTipoBase().name() + " no se puede convertir en " + asignacion.getDato().name();
                    ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna, manCuarteto);
                    return false;
                }

            }
        }
        return true;
    }

}
