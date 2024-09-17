package pos.logic;

public class Rango {
    private int mesInicio;
    private int anioInicio;
    private int mesFin;
    private int anioFin;

    public Rango(int anioInicio, int mesInicio, int anioFin, int mesFin) {
        mesInicio = 0;
        anioInicio = 0;
        mesFin = 0;
        anioFin = 0;
    }

    public void setMesInicio(int mesInicio) { this.mesInicio = mesInicio; }
    public void setMesFin(int mesFin) { this.mesFin = mesFin; }
    public void setAnioFin(int anioFin) { this.anioFin = anioFin; }
    public void setAnioInicio(int anioInicio) { this.anioInicio = anioInicio; }

    public int getMesInicio() { return mesInicio; }
    public int getAnioInicio() { return anioInicio; }
    public int getMesFin() { return mesFin; }
    public int getAnioFin() { return anioFin; }

    @Override
    public String toString() {
        return "Intervalo de Fecha [" + mesInicio + ", " + anioInicio + "||" + mesFin + ", " + anioFin + "]";
    }

    public boolean validacion_Rango() {

        if(anioFin < anioInicio) {
            return false;
        } else if(anioFin == anioInicio && mesFin < mesInicio) {
            return false;
        } else if (mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12) {
            return false;
        }

        return true;

    }
}
