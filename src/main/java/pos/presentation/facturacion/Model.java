package pos.presentation.facturacion;

import pos.Application;
import pos.logic.*;

import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Model  extends AbstractModel {

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

        this.cajeros = cajeros; // Cargar las cajeros en sistema
        this.mode = Application.MODE_CREATE;
        firePropertyChange(CAJEROS);
        this.clientes = clientes; // Cargar las clientes en sistema
        this.mode = Application.MODE_CREATE;
        firePropertyChange(CLIENTES);

    }
    public void init(List<Linea> list, List<Cajero> cajeros, List<Cliente> clientes) {
        this.list = list;
        this.current = new Linea();
        this.filter = new Linea();
        this.clientes = clientes; // Cargar las clientes
        this.cajeros=cajeros;
        this.mode = Application.MODE_CREATE;
        firePropertyChange(LIST);
        this.mode= Application.MODE_CREATE;
    }
    public Model() {
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

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
    public static final String CAJEROS="cajeros";
    public static final String CLIENTES="clientes";
}
