package pos.presentation.estadistica;
import pos.logic.Rango;
import pos.Application;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;
import pos.presentation.AbstractTableModel;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Model extends AbstractModel {

    List<Categoria> categorias;
    List<Categoria> categoriaAll;

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
    }

    public AbstractTableModel getTableModel() {
        return new AbstractTableModel() {

            public int getRowCount() { return rowss.length; }

            public int getColumnCount() { return cols.length; }


            @Override
            public Object getValueAt(int rowIndex, int colIndex) {
                if(colIndex == 0){
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

            }

            @Override
            public String getColumnName(int column) {
                if(column == 0){
                    return "Categoria";
                }
                return colss[column - 1];
            }

            public String getRowName(int row) {
                if(row == 0){
                    return "Categoria";
                } else {
                    return rowss[row];
                }
            }
        };
    }


    public List<Categoria> getList() {
        return categorias;
    }

    public void setList(List<Categoria> list){
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

}
