package pos.presentation.facturacion.buscarPanel;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Service;
import pos.presentation.AbstractModel;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

public class buscarModel extends AbstractModel {
    Producto filter;
    List<Producto> list;
    Producto current;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        this.mode = Application.MODE_CREATE;
    }

    public buscarModel() {
    }

    public void init(List<Producto> list) {
        this.list = list;
        this.current = new Producto();
        this.filter = new Producto();
        firePropertyChange(LIST);
        this.mode= Application.MODE_CREATE;
    }

    public List<Producto> getList() {
        return list;
    }

    public void setList(List<Producto> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Producto getCurrent() {
        return current;
    }

    public void setCurrent(Producto current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Producto getFilter() {
        return filter;
    }

    public void setFilter(Producto filter) {
        this.filter = filter; firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
}

