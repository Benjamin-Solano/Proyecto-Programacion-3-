package pos.logic;

public class Rango {
    private int mesInicio;
    private int anioInicio;
    private int mesFin;
    private int anioFin;

    public Rango() {
        mesInicio = 0;
        anioInicio = 0;
        mesFin = 0;
        anioFin = 0;
    }

    public Rango(int mesInicio, int anioInicio, int mesFin, int anioFin) {
        this.mesInicio = mesInicio;
        this.anioInicio = anioInicio;
        this.mesFin = mesFin;
        this.anioFin = anioFin;
    }

    public void setMesInicio(int mesInicio) { this.mesInicio = mesInicio; }
    public void setMesFin(int mesFin) { this.mesFin = mesFin; }
    public void setAnioFin(int anioFin) { this.anioFin = anioFin; }
    public void setAnioInicio(int anioInicio) { this.anioInicio = anioInicio; }

    public int getMesInicio() { return mesInicio; }
    public int getAnioInicio() { return anioInicio; }
    public int getMesFin() { return mesFin; }
    public int getAnioFin() { return anioFin; }

}
