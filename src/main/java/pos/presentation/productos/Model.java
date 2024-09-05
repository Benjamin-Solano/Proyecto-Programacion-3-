package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;

import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import pos.logic.Service;

public class Model extends AbstractModel {
    Producto filter;
    List<Producto> list;
    Producto current;
    List<Categoria> categorias;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        this.categorias = categorias; // Cargar las categorías
        this.mode = Application.MODE_CREATE;
        firePropertyChange(LIST);
        firePropertyChange(CATEGORIAS);
    }

    public Model() {
    }

    public void init(List<Producto> list, List<Categoria> categorias) {
        this.list = list;
        this.current = new Producto();
        this.filter = new Producto();
        this.categorias = categorias; // Cargar las categorías
        this.mode = Application.MODE_CREATE;
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
        this.filter = filter;
        firePropertyChange(FILTER);
    }
    public List<Categoria> getCategorias(){return categorias;}

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(CATEGORIAS); // Notificar cambios
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
    public static final String CATEGORIAS="categorias";
}
