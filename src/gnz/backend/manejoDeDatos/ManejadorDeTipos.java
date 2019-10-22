package gnz.backend.manejoDeDatos;

import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.frontend.editorDeTexto.EditorDeTextoFrame;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeTipos {

    private final ManejadorDeCuartetos manCuarteto;
    private final EditorDeTextoFrame editor;

    public ManejadorDeTipos(ManejadorDeCuartetos manCuarteto, EditorDeTextoFrame editor) {
        this.manCuarteto = manCuarteto;
        this.editor = editor;
    }

    /**
     * Se utiliza cuando se reconoce un dato numerico, y de esta forma asignar
     * su tipo
     *
     * @param s
     * @return
     */
    public DatoNumerico verificarDato(String s) {
        Double numero = Double.parseDouble(s);
        System.out.println(DatoNumerico.CHAR.getLimiteInferior());
        System.out.println(DatoNumerico.CHAR.getLimiteSuperior());
        if (numero >= DatoNumerico.CHAR.getLimiteInferior() && numero <= DatoNumerico.CHAR.getLimiteSuperior()) {
            return DatoNumerico.CHAR;
        } else if (numero >= DatoNumerico.BYTE.getLimiteInferior() && numero <= DatoNumerico.BYTE.getLimiteSuperior()) {
            return DatoNumerico.BYTE;
        } else if (numero >= DatoNumerico.INT.getLimiteInferior() && numero <= DatoNumerico.INT.getLimiteSuperior()) {
            return DatoNumerico.INT;
        } else {
            return DatoNumerico.LONG;
        }
    }

    /**
     * Busca el tipo de dato, y devuelve el mayor
     *
     * @param dato1
     * @param dato2
     * @return
     */
    public Dato buscarDatoMayor(DatoCodigo dato1, DatoCodigo dato2) {
        if (dato1 != null && dato2 != null) {
            if (dato1.getDato() == TipoDeDato.NUMERICO && dato2.getDato() == TipoDeDato.NUMERICO) {
                if (dato1.getDatoNumerico().ordinal() > dato2.getDatoNumerico().ordinal()) {
                    return dato1.getDatoNumerico();
                } else {
                    return dato2.getDatoNumerico();
                }
            } else if (dato1.getDato() == dato2.getDato()) {//Boolean y cadena
                return dato1.getDato();
            } else {
                return TipoDeDato.CADENA;
            }
        }
        return null;
    }

    /**
     * Asigna el tipo de dato mayor en la jerarquia
     *
     * @param dato
     * @param datoMayor
     */
    public void asignarTipoDeDato(DatoCodigo dato, Dato datoMayor) {
        if (dato != null && datoMayor != null) {
            if (datoMayor instanceof DatoNumerico) {
                dato.setDato(TipoDeDato.NUMERICO);
                dato.setDatoNumerico((DatoNumerico) datoMayor);
            } else {//Cadena o booleano
                TipoDeDato dat = ((TipoDeDato) datoMayor);
                if (dat == TipoDeDato.BOOLEANO) {
                    dato.setDato(TipoDeDato.BOOLEANO);
                    dato.setDatoNumerico(null);
                } else {
                    dato.setDato(TipoDeDato.CADENA);
                    dato.setDatoNumerico(null);
                }
            }
        }
    }

    /**
     * Crea los cuartetos,los datos ya deben tener el mismo tipo, de haber
     * incongruencias se devolvera null, o algun dato ya sera null si hubo
     * problemas en validaciones anteriores
     *
     * @param dato1
     * @param dato2
     * @param operacion
     * @return
     */
    public DatoCodigo operarDatos(DatoCodigo dato1, DatoCodigo dato2, Operacion operacion) {
        if (dato1 != null && dato2 != null && operacion != null) {
            String mensaje;
            String temporal;
            switch (dato1.getDato()) {
                case CADENA:
                    if (operacion == Operacion.MAS) {
                        //Se genera un nuevo dato
                        //Se genera un cuarteto
                        if (dato1.getTemporal() == null) {//El temporal simpre se asigna al dato1
                            temporal = this.manCuarteto.crearCuarteto(operacion, dato1.getValor(), dato2.getValor());
                        } else {
                            temporal = this.manCuarteto.crearCuarteto(operacion, dato1.getTemporal(), dato2.getValor());
                        }
                        return new DatoCodigo(dato2.getNumeroDeLinea(), dato2.getNumeroDeColumna(), dato1.getDato(), null, dato1.getValor() + dato2.getValor(), temporal);

                    } else {
                        mensaje = "LA OPERACION " + operacion.name() + " no es valida para cadenas";
                        ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, dato1.getNumeroDeLinea(), dato1.getNumeroDeColumna(), this.manCuarteto);
                        this.manCuarteto.setExistioError(true);
                    }
                    break;
                case BOOLEANO:
                    if (operacion == Operacion.AND || operacion == Operacion.OR || operacion == Operacion.NOT) {
                        //Se genera nuevo dato
                        //Se genera un cuarteto
                    } else {
                        mensaje = "LA OPERACION " + operacion.name() + " no es valida para booleanos";
                        ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, dato1.getNumeroDeLinea(), dato1.getNumeroDeColumna(), this.manCuarteto);
                        this.manCuarteto.setExistioError(true);

                    }
                    break;
                default://TipoDeDato.NUMERICO
                    if (operacion == Operacion.AND || operacion == Operacion.OR || operacion == Operacion.NOT) {
                        mensaje = "LA OPERACION " + operacion.name() + " no es valida para enteros";
                        ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, dato1.getNumeroDeLinea(), dato1.getNumeroDeColumna(), this.manCuarteto);
                        this.manCuarteto.setExistioError(true);
                    } else {
                        if (dato1.getTemporal() == null && dato2.getTemporal() == null) {
                            temporal = this.manCuarteto.crearCuarteto(operacion, dato1.getValor(), dato2.getValor());
                        } else if (dato1.getTemporal() != null && dato2.getTemporal() != null) {
                            temporal = this.manCuarteto.crearCuarteto(operacion, dato1.getTemporal(), dato2.getTemporal());
                        } else {
                            if (dato1.getTemporal() != null) {
                                temporal = this.manCuarteto.crearCuarteto(operacion, dato1.getTemporal(), dato2.getValor());
                            } else {
                                temporal = this.manCuarteto.crearCuarteto(operacion, dato2.getTemporal(), dato1.getValor());
                                // return new DatoCodigo(dato1.getNumeroDeLinea(), dato1.getNumeroDeColumna(), dato2.getDato(), dato2.getDatoNumerico(), dato2.getValor() + dato2.getValor(), temporal);
                            }
                        }
                        return new DatoCodigo(dato2.getNumeroDeLinea(), dato2.getNumeroDeColumna(), dato1.getDato(), dato1.getDatoNumerico(), dato1.getValor() + dato2.getValor(), temporal);
                    }
                    break;
            }
        }
        return null;
    }

}
