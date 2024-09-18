package pos.presentation.facturacion;

import pos.Application;
import pos.logic.*;

import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Model  extends AbstractModel {
    Factura filterFactura;
    Factura currentFactura;
    Linea filter;
    List<Linea> list; //Estos son los productos que ha comprado
    Linea current;;
    List<Cajero> cajeros; //Estos los cargamos de xml
    List<Cliente> clientes; //Estos los cargamos de xml
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(CAJEROS);
        firePropertyChange(CLIENTES);
        //Factura general
        firePropertyChange(LISTFAC);
        firePropertyChange(CURRENTFAC);
        firePropertyChange(FILTERFAC);

    }
    public void init(List<Linea> list, List<Cajero> cajeros, List<Cliente> clientes) {
        this.list = list;
        this.current = new Linea();
        this.filter = new Linea();
        this.filterFactura=new Factura();
        this.clientes = clientes; // Cargar las clientes
        this.cajeros=cajeros;

        //Factura general
        this.currentFactura=new Factura();
        this.filterFactura=new Factura();

        firePropertyChange(LIST);
        firePropertyChange(CAJEROS);
        firePropertyChange(CLIENTES);
        this.mode= Application.MODE_CREATE;
    }
    public Model() {
    }
    public void notificarCambioLista() {
        firePropertyChange(LIST);
    }

    public List<Linea> getList(){
        return list;
    }
    public void setList(List<Linea> list){this.list=list; firePropertyChange(LIST);}

    public Linea getCurrent(){return current;}
    public void setCurrent(Linea current){this.current=current; firePropertyChange(CURRENT);}

    public Linea getFilter(){return filter;}
    public void setFilter(Linea filter){this.filter=filter; firePropertyChange(FILTER);}

    public List<Cajero> getCajeros(){
        return cajeros;
    }
    public void setCajeros(List<Cajero> cajeros){this.cajeros=cajeros; firePropertyChange(CAJEROS);}

    public List<Cliente> getClientes(){return clientes;}
    public void setClientes(List<Cliente> clientes){this.clientes=clientes; firePropertyChange(CLIENTES); }// Notificar cambios

    public int getMode() {return mode;}
    public void setMode(int mode) {this.mode = mode;}

    public Factura getFilterFactura(){return filterFactura;}
    public void setFilter(Factura filter){this.filterFactura=filter; firePropertyChange(FILTERFAC);}

    public void setCurrentFactura(Factura current) {this.currentFactura=current; firePropertyChange(CURRENTFAC);}
    public Factura getCurrentFactura() {return currentFactura;}


    //Lista interna
    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
    public static final String CAJEROS="cajeros";
    public static final String CLIENTES="clientes";
    //Lista general
    public static final String CURRENTFAC="currentFactura";
    public static final String LISTFAC="listFactura";
    public static final String FILTERFAC="filterFactura";
}
