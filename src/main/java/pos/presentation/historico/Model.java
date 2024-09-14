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
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);

        this.mode = Application.MODE_CREATE;
    }

    public Model() {
    }

    public void init(List<Factura> fact, List<Linea> linea){
       // this.currentFactura =
    }

    public List<Cliente> getList() {
        return list;
    }

    public void setList(List<Cliente> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Cliente getCurrent() {
        return current;
    }

    public void setCurrent(Cliente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Cliente getFilter() {
        return filter;
    }

    public void setFilter(Cliente filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
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
