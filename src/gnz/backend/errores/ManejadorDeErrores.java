package gnz.backend.errores;

import javax.swing.JTextArea;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeErrores {

    public static void mostrarErrorLexico(JTextArea txt, String lexema, int linea, int columna) {
        txt.append("Error Lexico, no entiendo:" + lexema + " en" + " " + linea + ":" + columna + "\n");
    }

    public static void mostrarErrorSintactico(JTextArea txt, String estructura, int linea, int columna, boolean esRecuperado) {
        if (esRecuperado) {
            txt.append("Error Sintactico recuperado, no entiendo:" + estructura + " en" + " " + linea + ":" + columna + "\n");
        } else {
            txt.append("Error Sintactico no recuperado, no entiendo:" + estructura + " en" + " " + linea + ":" + columna + "\n");
        }
    }

    public static void mostrarErrorSemantico(JTextArea txt, String mensaje, int linea, int columna) {
        txt.append("Error Semantico:" + mensaje + " en" + " " + linea + ":" + columna + "\n");
    }

}
