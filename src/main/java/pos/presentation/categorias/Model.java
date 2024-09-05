package pos.presentation.categorias;


import pos.Application;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Categoria filter;
    List<Categoria> list;
    Categoria current;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {
    }

    public void init(List<Categoria> list){
        this.list = list;
        this.current = new Categoria();
        this.filter = new Categoria();
        this.mode= Application.MODE_CREATE;
    }

    public List<Categoria> getList() {
        return list;
    }

    public void setList(List<Categoria> list){
        this.list = list;
        firePropertyChange(LIST);
    }

    public Categoria getCurrent() {
        return current;
    }

    public void setCurrent(Categoria current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Categoria getFilter() {
        return filter;
    }

    public void setFilter(Categoria filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
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
