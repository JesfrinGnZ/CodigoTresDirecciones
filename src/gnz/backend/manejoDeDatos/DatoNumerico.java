package gnz.backend.manejoDeDatos;

/**
 *
 * @author jesfrin
 */
public enum DatoNumerico implements Dato{

    //DOUBLE, FLOAT, LONG, INT(-1*Math.pow(2, 31),Math.pow(2, 31)), CHAR(-1*Math.pow(2, 16),Math.pow(2, 16)),BYTE(-1*Math.pow(2, 8),Math.pow(2, 8));
    CHAR(-1*Math.pow(2, 16),Math.pow(2, 16)),BYTE(-1*Math.pow(2, 8),Math.pow(2, 8)),INT(-1*Math.pow(2, 31),Math.pow(2, 31)),LONG,FLOAT,DOUBLE;
    
    private double limiteInferior;
    private double limiteSuperior;

    private DatoNumerico() {
    }

    private DatoNumerico(double limiteInferior, double limiteSuperior) {
        this.limiteInferior = (int)limiteInferior;
        this.limiteSuperior = (int)limiteSuperior;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }


}
