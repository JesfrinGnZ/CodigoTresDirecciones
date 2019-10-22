package gnz.backend.cuarteto;

import gnz.backend.manejoDeDatos.DatoCodigo;
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
    private int numeroDeEtiqueta;
    private int inicioDeExpresion;
    private Cuarteto ultimoGoto;
    private Cuarteto ultimoGotoParaIf;

    public ManejadorDeCuartetos() {
        this.listaDeCuartetos = new ArrayList<>();
        this.existioError = false;
        this.numeroDeTemporal = 1;
        this.numeroDeEtiqueta = 1;
        this.inicioDeExpresion = 0;
    }

    public String crearCuarteto(Operacion operando, String operador1, String operador2) {
        String temporal = "t" + generarTemporal();
        Cuarteto cuarteto = new Cuarteto(operando, operador1, operador2, temporal, TipoDeCuarteto.EXPRESION);
        this.listaDeCuartetos.add(cuarteto);
        return temporal;
    }

    public void crearCuartetoParaTrue() {

    }

    public void crearCuartetoParaFalse() {

    }

    public void crearCuartetoPrimeroParaComparacion(Operacion op, DatoCodigo t1, DatoCodigo t2, Boolean valor) {
        Cuarteto c;
        Cuarteto nuevoGoto;
        // if (t1 != null && t2 != null) {
        if (op == null) {//Viene un true o false
            if (valor) {
                c = crearCuartetoIf(null, t1, t2, "L" + generarEtiqueta(), valor);//>>>>>>>>>>>>>>>>>>1
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2
            } else {
                int e1 = generarEtiqueta();
                int e2 = generarEtiqueta();
                c = crearCuartetoIf(op, t1, t2, "L" + e2, valor);//>>>>>>>>>>>>>>>>>>1
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + e1, TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2
            }
        } else {
            c = crearCuartetoIf(op, t1, t2, "L" + generarEtiqueta(), false);//>>>>>>>>>>>>>>>>>>1
            nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2
        }
        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
        //}

    }

    public int[] generarGoto() {
        String numero = this.ultimoGoto.getResultado().substring(1, this.ultimoGoto.getResultado().length());
        String numero2 = this.ultimoGotoParaIf.getResultado().substring(1, this.ultimoGotoParaIf.getResultado().length());
        int arr[] = {Integer.valueOf(numero), Integer.valueOf(numero2)};
        return arr;
    }

    public void crearSegundoCuartetoParaAnd(Operacion op, DatoCodigo t1, DatoCodigo t2, boolean valor) {
        Cuarteto cuartetoLabel;
        Cuarteto c;
        Cuarteto nuevoGoto;
        // if (t1 != null && t2 != null) {
        int numeroDeLabelAlInicio = generarGoto()[1];
        int numeroDeLableFInal = generarGoto()[0];
        if (op == null) {
            if (valor) {
                cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
                c = crearCuartetoIf(null, t1, t2, "L" + generarEtiqueta(), valor);//>>>>>>>>>>>>>>>>>>1
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + numeroDeLableFInal, TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2

            } else {
                cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
                c = crearCuartetoIf(op, t1, t2, "L" + numeroDeLableFInal, false);//>>>>>>>>>>>>>>>>>>>2 
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
            }
            this.inicioDeExpresion = this.listaDeCuartetos.size();
        } else {
            cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
            c = crearCuartetoIf(op, t1, t2, "L" + generarEtiqueta(), false);//>>>>>>>>>>>>>>>>>>>2 
            nuevoGoto = new Cuarteto(null, "goto", null, "L" + numeroDeLableFInal, TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
        }

        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        this.listaDeCuartetos.add(this.inicioDeExpresion, cuartetoLabel);
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
        //}

    }

    public void crearSegundoCuartetoParaOr(Operacion op, DatoCodigo t1, DatoCodigo t2, boolean valor) {
        Cuarteto cuartetoLabel;
        Cuarteto c;
        Cuarteto nuevoGoto;
        // if (t1 != null && t2 != null) {
        int numeroDeLabelAlInicio = generarGoto()[0];
        int labelDeIf = generarGoto()[1];
        if (op == null) {
            if (valor) {
                cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
                c = crearCuartetoIf(op, t1, t2, "L" + labelDeIf, false);//>>>>>>>>>>>>>>>>>>>2 
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3 
            } else {
                cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
                c = crearCuartetoIf(op, t1, t2, "L" + labelDeIf, false);//>>>>>>>>>>>>>>>>>>>2 
                nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
            }
            this.inicioDeExpresion = this.listaDeCuartetos.size();
        } else {
            cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
            c = crearCuartetoIf(op, t1, t2, "L" + labelDeIf, false);//>>>>>>>>>>>>>>>>>>>2 
            nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
        }
        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        this.listaDeCuartetos.add(this.inicioDeExpresion, cuartetoLabel);
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
        //}

    }

    private Cuarteto crearCuartetoIf(Operacion op, DatoCodigo t1, DatoCodigo t2, String numParaLabel, boolean valB) {
        Cuarteto c;
        if (op == null) {//Solo true o false
            if (valB) {
                c = new Cuarteto(null, "TRUE", null, numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
            } else {
                c = new Cuarteto(null, "FALSE", null, numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
            }
        } else if (t1.getTemporal() == null && t2.getTemporal() == null) {
            c = new Cuarteto(op, t1.getValor(), t2.getValor(), numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
        } else if (t1.getTemporal() == null) {//Si su operando es del tipo >,<,>=,<= etc, se generara el if
            c = new Cuarteto(op, t1.getValor(), t2.getTemporal(), numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
        } else if (t2.getTemporal() == null) {
            c = new Cuarteto(op, t1.getTemporal(), t2.getValor(), numParaLabel, TipoDeCuarteto.IF);
        } else {
            c = new Cuarteto(op, t1.getTemporal(), t2.getTemporal(), numParaLabel, TipoDeCuarteto.IF);
        }
        return c;
    }

    public void escribirCuartetos(JTextArea txt) {
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.EXPRESION) {
                if (cuarteto.getOperando() == null && cuarteto.getOperador1() == null) {
                    txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador2() + "\n");
                } else {
                    txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + " " + cuarteto.getOperando().getSigno() + " " + cuarteto.getOperador2() + "\n");
                }
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.IF) {
                if (cuarteto.getOperando() == null) {
                    txt.append("IF (" + cuarteto.getOperador1() + ") " + "goto" + " " + cuarteto.getResultado() + "\n");

                } else {
                    txt.append("IF (" + cuarteto.getOperador1() + " " + cuarteto.getOperando().getSigno() + " " + cuarteto.getOperador2() + ") " + "goto" + " " + cuarteto.getResultado() + "\n");
                }
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.GOTOETIQUETA) {//Cuarteto ETIQUETA
                txt.append("goto" + " " + cuarteto.getResultado() + "\n");
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.VALORARREGLO) {
                txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + "[" + cuarteto.getOperador2() + "]" + "\n");
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.ASIGARREGLO) {
                txt.append(cuarteto.getOperador1() + "[" + cuarteto.getOperador2() + "] =" + cuarteto.getResultado() + "\n");
            } else {//Solo es Etiqueta
                txt.append("  " + cuarteto.getResultado() + ":" + "\n");
            }

        }
        this.listaDeCuartetos.clear();
        this.numeroDeTemporal = 1;
        this.numeroDeEtiqueta = 1;
    }

    public int generarTemporal() {
        return numeroDeTemporal++;
    }

    public int generarEtiqueta() {
        return numeroDeEtiqueta++;
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

    public int getNumeroDeEtiqueta() {
        return numeroDeEtiqueta;
    }

    public void setNumeroDeEtiqueta(int numeroDeEtiqueta) {
        this.numeroDeEtiqueta = numeroDeEtiqueta;
    }

    public int getInicioDeExpresion() {
        return inicioDeExpresion;
    }

    public void setInicioDeExpresion() {
        this.inicioDeExpresion = this.listaDeCuartetos.size() - 1;
    }


}
