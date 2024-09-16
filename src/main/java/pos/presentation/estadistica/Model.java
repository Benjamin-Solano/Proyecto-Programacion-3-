package pos.presentation.estadistica;
import pos.data.Data;
import pos.logic.Producto;
import pos.logic.Rango;
import pos.Application;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;
import pos.presentation.AbstractTableModel;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;

public class Model extends AbstractModel {
/*
    List<Categoria> categorias;
    List<Categoria> categoriaAll;
    Categoria filter;
    Categoria current;
    Rango rango;  // Hay que darle uso al rango...
    String[] rowss;
    String[] colss;
    float[][] data;
    int mode;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        this.mode = Application.MODE_CREATE;
    }

    public Model() {
    }

    public void init(List<Categoria> list){
        this.categorias = list;
        this.categoriaAll = list;
        this.mode= Application.MODE_CREATE;
        firePropertyChange(LIST);
    }

    public void setRango(Rango rango) {
        this.rango = rango;
        firePropertyChange("rango");
    }

    public Rango getRango() {
        return rango;
    }
    public AbstractTableModel getTableModel() {
        return new AbstractTableModel() {

            public int getRowCount() { return rowss.length; }

            public int getColumnCount() { return colss.length; }

            @Override
            public Object getValueAt(int rowIndex, int colIndex) {
                if (rowIndex < 0 || rowIndex >= rowss.length || colIndex < 0 || colIndex >= colss.length + 1) {
                    return null; // O lanza una excepción
                }
                if (colIndex == 0) {
                    return rowss[rowIndex];
                } else {
                    return data[rowIndex][colIndex - 1];
                }
            }

            @Override
            protected Object getPropetyAt(Object o, int col) {
                return null;
            }

            @Override
            protected void initColNames() {
                // Inicializar el array colss con el tamaño de la lista de categorias
                colss = new String[categorias.size()];

                // Llenar el array colss con los nombres de las categorias
                for (int i = 0; i < categorias.size(); i++) {
                    colss[i] = categorias.get(i).getNombre();
                }
            }

            @Override
            public String getColumnName(int column) {
                if (column == 0) {
                    return "Categoria";
                }
                return colss[column - 1];
            }

            public String getRowName(int row) {
                if (row == 0) {
                    return "Categoria";
                } else {
                    return rowss[row];
                }
            }
        };
    }


    public void setFilter(Categoria filter) {
        this.filter = filter; firePropertyChange(FILTER);
    }
    public List<Categoria> getList() {
        return categorias;
    }

    public void setCategorias(List<Categoria> list){
        this.categorias = list;
        firePropertyChange(LIST);
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

    public void setCurrent(Categoria categoria) {
        this.current = categoria;
        firePropertyChange(CURRENT);
    }

    public Categoria getCurrent() {
        return current;
    }

    public List<Categoria> getCategorias() {return categorias; }

 */
}
