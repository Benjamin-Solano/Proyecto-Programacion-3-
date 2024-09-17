package pos.presentation.estadistica;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class Controller {

    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        model.init(new ArrayList<Categoria>(Service.instance().getCategorias()));
        view.setController(this);
        view.setModel(model);
    }

    public void actualizarDatos() {

        Rango range = model.getRango();
        int columns = (range.getAnioFin() - range.getAnioInicio())* 12 + range.getMesFin() - range.getMesInicio() + 1;
        int rows = model.getCategorias().size();

        String[] colsVec = new String[columns];
        String[] rowVec = new String[rows];

        int anno = range.getAnioInicio();
        int mes = range.getMesInicio();

        for(int i = 0; i < columns; i++) {
            colsVec[i] = anno + "/" + (mes < 10 ? "0" + mes : mes);
            mes++;
            if(mes > 12) {
                anno++;
                mes = 1;
            }
        }

        Float[][] data = new Float[rows][columns];

        if(!model.getCategorias().isEmpty()) {

            for(int i = 0; i < rows; i++) {
                Categoria categoria = model.getCategorias().get(i);
                mes = range.getMesInicio();
                anno = range.getAnioInicio();

                for(int j = 0; j < columns; j++) {
                    Float venta = Service.instance().getVentas(categoria,anno,mes);
                    data[i][j] = venta;

                    mes++;
                    if(mes > 12) {
                        mes = 1;
                        anno++;
                    }
                }
            }

            int i = 0;
            for(Categoria categoria : model.getCategorias()) {
                rowVec[i] = categoria.getNombre();
                i++;
            }

            model.setColumns(colsVec);
            model.setRows(List.of(rowVec));
            model.setData(data);

            return;
        }

    }



    public void agregarCategorias() {

        try {
            List<Categoria> categorias = Service.instance().getCategorias();
            for (Categoria c : categorias) {
                if(!model.getCategorias().contains(c)) {
                    model.getCategorias().add(c);
                }
            }
            actualizarDatos();
        } catch (Exception e) {
            System.err.print("Error al agregar categorias: " + e.getMessage());
        }

    }

    public void clear() {
        model.resetData();
        view.actualizarV();
    }


    public void agregarCategoria(Categoria cate) throws Exception {
        if(model.getCategorias().contains(cate)) {
            throw new Exception("Categoria ya existente");
        }
        model.getCategorias().add(cate);
        actualizarDatos();
    }

    public void CategoryBox() {
        List<Categoria> categorias = Service.instance().getCategorias();
        if(categorias != null && !categorias.isEmpty()) {
            for (Categoria c : categorias) {
                view.comboCategoria.addItem(c);
            }
            view.getPanel().revalidate();
        }
    }

    public void agregatLineaDeCATEGORIA(String categoria, Float[] dato) {
        if(categoria != null && dato != null) { // Ambos deberian ser validos...
            int indice = model.getRows().indexOf(categoria);
            if(indice != -1) {
                for(int i = 0; i < dato.length; i++) {
                    model.getData()[indice][i] = dato[i];
                }
            } else {
                model.agregarLinea(categoria, dato);
            }
            actualizarDatos();
        }
    }


    public void updateRange(int desdeAnno, int desdeMes, int HastaAnno, int HastaMes) {
        if(desdeAnno <= HastaAnno && (desdeAnno != HastaAnno || desdeMes <= HastaMes)) {
            model.setRango(new Rango(desdeAnno, desdeMes, HastaAnno, HastaMes));
            actualizarDatos();
        }
    }

    public List<Factura> getFacturas() {
        try {
            return Service.instance().getFacturas();
        } catch (Exception e) {
            System.err.print("Error al obtener facturas: " + e.getMessage());
            return null;
        }
    }

    public void eliminarCategoria(String nombre) {
        model.eliminarCategoria(nombre);
    }

    public void eliminarLinea(String nombre) {
        int indice = model.getRows().indexOf(nombre);
        if(indice != -1) {
            model.eliminarLinea(indice);
        }
    }



}
