package pos.presentation.historico;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Factura;
import pos.logic.Linea;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Factura filterFactura;
    Linea filterLinea;
    List<Factura> listFacturas;
    List<Linea> listLineas;
    Factura currentFactura;
    Linea currentLinea;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LISTAFACTURA);
        firePropertyChange(CURRENTFACTURA);
        firePropertyChange(FILTERFACTURA);

        firePropertyChange(LISTLINEA);
        firePropertyChange(CURRENTLINEA);
        firePropertyChange(FILTERLINEA);
        this.mode = Application.MODE_CREATE;
    }

    public Model() {
    }

    public void init(List<Factura> fact, List<Linea> linea){
        this.currentFactura = new Factura();
        this.currentLinea = new Linea();
        this.listFacturas = fact;
        this.listLineas = linea;
        this.filterFactura = new Factura();
        this.filterLinea = new Linea();
        this.mode= Application.MODE_CREATE;
        firePropertyChange(LISTLINEA);
        firePropertyChange(LISTAFACTURA);
    }

    public List<Factura> getListFacturas() {
        return listFacturas;
    }

    public void setListFacturas(List<Factura> list){
        this.listFacturas = list;
        firePropertyChange(LISTAFACTURA);
    }

    public List<Linea> getListLineas() {
        return listLineas;
    }

    public void setListLineas(List<Factura> list){
        this.listFacturas = list;
        firePropertyChange(LISTLINEA);
    }

    public Linea getCurrentLinea() {
        return currentLinea;
    }
    //--
    public Factura getCurrentFactura() { return currentFactura; }

    public void setCurrentFactura(Factura current) {
        this.currentFactura = current;
        firePropertyChange(CURRENTFACTURA);
    }

    public Factura getFilterFactura() {
        return filterFactura;
    }

    public void setFilterFactura(Factura filter) {
        this.filterFactura = filter;
        firePropertyChange(FILTERFACTURA);
    }
    //--
    public void setCurrentLinea(Linea current) {
        this.currentLinea = current;
        firePropertyChange(CURRENTLINEA);
    }

    public Linea getFilterLinea() {
        return filterLinea;
    }

    public void setFilterLinea(Linea filter) {
        this.filterLinea = filter;
        firePropertyChange(FILTERLINEA);
    }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public static final String LISTAFACTURA="listFactura";
    public static final String LISTLINEA ="listLinea";
    public static final String CURRENTFACTURA="currentFactura";
    public static final String CURRENTLINEA="currentLinea";
    public static final String FILTERFACTURA="filterFactura";
    public static final String FILTERLINEA="filterLinea";

}
