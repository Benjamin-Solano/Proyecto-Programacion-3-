package pos.presentation.estadistica;

import pos.logic.Rango;
import pos.logic.Categoria;
import pos.logic.Service;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model extends AbstractModel {

    private Rango rango;
    private List<Categoria> categorias;
    private List<Categoria> categoriasAll;

    private List<String> rows = new ArrayList<>();
    private String[] cols;
    private Float[][] data;

    public Model() {
        categoriasAll = Service.instance().getCategorias();
        categorias = new ArrayList<>(categoriasAll);
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(DATA);
    }

    public void setCategoriasAll(List<Categoria> categoriasAll) {
        this.categoriasAll = categoriasAll;
        firePropertyChange(DATA);
    }

    public Float[][] getData() { return data; }

    public String[] getColumns() { return cols; }

    public List<String> getRows() { return rows; }

    public void setRows(List<String> rows) {
        this.rows = rows;
        firePropertyChange(DATA);
    }


    public void addTotalWeekly(Float[] total) {
        String totalCate = "Total";
        Float[] dataT = new Float[getColumns().length];
        if (total.length != cols.length) {
            System.err.print("ERROR: El largo de los totales y las columnas no coinciden...");
        }
        agregarLinea(totalCate,total);
    }

    public void agregarLinea(String categoria, Float[] dato) {
        if(categoria == null || dato == null || dato.length != cols.length) {
            System.err.println("ERROR: Datos invalidos, Intente Nuevamente.");
        }

        int indice = rows.indexOf(categoria);
        if(indice == -1) {
            rows.add(categoria);
            indice = rows.size() - 1;
        }

        if(data == null || data.length < rows.size()) {
            Float[][] newData = new Float[rows.size()][cols.length];
            if(data != null) {
                System.arraycopy(data, 0, newData, 0, data.length);
                data = newData;
            }
            data = newData;
        }
        data[indice] = dato;
        firePropertyChange(DATA);
    }

    public void setColumns(String[] c) {
        this.cols = c;
        firePropertyChange(DATA);
    }

    public void setData(Float[][] d) {
        this.data = d;
        firePropertyChange(DATA);
    }


    public void agregarCategoria(Categoria categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
            agregarLinea(categoria.getNombre(), new Float[cols.length]);
            firePropertyChange(DATA);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        super.addPropertyChangeListener(pcl);
        firePropertyChange(DATA);
        firePropertyChange(CATEGORIES_ALL);
    }

    public List<Categoria> getCategorias() { return this.categorias; }
    public List<Categoria> getAllCategorias() { return this.categoriasAll; }

    public Float[] getDatosCategoria(String categoria) {
        int indice = rows.indexOf(categoria);
        if (indice == -1) {
            return data[indice];
        }
        return new Float[0];
    }


    public void init(List<Categoria> Categorias) {
        categoriasAll = Categorias;
        this.categorias = new ArrayList<>();
        this.rango = new Rango(0,0,0,0);
        rows = new ArrayList<>();
        cols = new String[0];
        data = new Float[0][0];
    }

    public void resetData(){
        data = new Float[0][0];
        cols = new String[0];
        rows.clear();
        firePropertyChange(DATA);
    }

    public Rango getRango() { return this.rango; }
    public void setRango(Rango rango) {
        this.rango = rango;
        firePropertyChange(RANGE);
    }

    public void eliminarLinea(int index) {

        if(index >= 0 && index < rows.size()) {
            rows.remove(index);

            Float[][] data = new Float[rows.size()][cols.length];
            int indice = 0;

            for(int i = 0; i < this.data.length; i++) {
                if(i != index) {
                    data[indice++] = this.data[i];
                }
            }

            this.data = data;
            firePropertyChange(DATA);

        }
    }


    public void eliminarCategoria(String nombre) {
        int indice = -1;

        for(int i = 0; i < categorias.size(); i++) {
            if(categorias.get(i).getNombre().equals(nombre)) {
                indice = i;
                break;
            }
        }
        if(indice != -1) {
            categorias.remove(indice);
            eliminarLinea(indice);
            firePropertyChange(DATA);
        }

    }


    public static final String DATA = "data";
    public static final String RANGE = "rangO";
    public static final String CATEGORIES_ALL = "CATEGORIES";

}
