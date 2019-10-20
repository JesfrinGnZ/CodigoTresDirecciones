/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeDatos;


/**
 *
 * @author jesfrin
 */
public enum Operacion {
    
    MAS("+"),MENOS("-"),POR("*"),DIV("/"),AND,OR,NOT,RESTO("%"),MAYOR(">"),MENOR("<"),MAYORIGUAL(">="),
    MENORIGUAL("<="),IGUALA("=="),DISTINTODE("!=");
    
    private String signo;
    
    private Operacion(){
        
    }

    private Operacion(String signo){
        this.signo=signo;
    }

    public static Operacion getMAS() {
        return MAS;
    }

    public static Operacion getMENOS() {
        return MENOS;
    }

    public static Operacion getPOR() {
        return POR;
    }

    public static Operacion getDIV() {
        return DIV;
    }

    public static Operacion getAND() {
        return AND;
    }

    public static Operacion getOR() {
        return OR;
    }

    public static Operacion getNOT() {
        return NOT;
    }

    public static Operacion getRESTO() {
        return RESTO;
    }

    public String getSigno() {
        return signo;
    }
    
    
}
