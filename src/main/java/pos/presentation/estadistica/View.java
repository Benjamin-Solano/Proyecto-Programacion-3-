package pos.presentation.estadistica;

import pos.logic.Categoria;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener{
    private JPanel panel;
    private JComboBox<Integer> DesdeAno;
    private JComboBox<Integer> DesdeMes;
    private JComboBox<Integer> HastaAno;
    private JComboBox<Integer> HastaMes;
    private JComboBox<Categoria> comboCategoria;
    private JButton buttonCheck1;
    private JButton buttonCheck2;
    private JTable datosLista;
    private JButton clear1Button;
    private JButton clearAllButton;
    private JPanel JPanelGrafico;

    private Model model;
    private Controller controller;

    public JPanel getPanel() {
        return panel;
    }

    public View()  {

        buttonCheck2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.agregarAllCategories();
                } catch (Exception exce) {
                    throw new RuntimeException(exce);
                }
            }
        });

        clear1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = datosLista.getSelectedRow();
                if(fila != -1){
                    String nomCategoria = (String) datosLista.getModel().getValueAt(fila, 0);
                    controller.eliminarLineaCategoria(categoriaNombre);
                }
            }
        });


        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(datosLista.getRowCount() > 0){
                    model.resetData();
                }

            }
        });

        ActionListener comboListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validar()) {
                    controller.actualizarRango(
                            Integer.parseInt((String) DesdeAno.getSelectedItem()), DesdeMes.getSelectedIndex() + 1, Integer.parseInt((String) HastaAno.getSelectedItem()), HastaMes.getSelectedIndex() + 1);
                    )
                }
            }
        };

        DesdeMes.addActionListener(comboListener);
        HastaMes.addActionListener(comboListener);
        DesdeAno.addActionListener(comboListener);
        HastaAno.addActionListener(comboListener);

        buttonCheck1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categoria cat = (Categoria) comboCategoria.getSelectedItem();
                try {
                    controller.agregarCategoria(cat);
                } catch (Exception exe) {
                    throw new RuntimeException(exe);
                }
            }
        });



    }


    private void setErrorBorder(JComboBox<?>... comboBoxes) {

        Border borde = BorderFactory.createLineBorder(Color.RED,2);
        for (JComboBox<?> comboBox : comboBoxes) {
            comboBox.setBorder(borde);
        }

    }

    private boolean validar() {

        int DesdeAnio = Integer.parseInt((String) DesdeAno.getSelectedItem());
        int HastaAnio = Integer.parseInt((String) HastaAno.getSelectedItem());
        int HastaMe = HastaMes.getSelectedIndex() + 1;
        int DesdeMe = DesdeMes.getSelectedIndex() + 1;

        resetComboBoxBorders();

        if (DesdeAnio > HastaAnio) {
            setErrorBorder(DesdeAno, HastaAno);
            return false;
        } else if(DesdeAnio == HastaAnio && DesdeMe > HastaMe) {
            setErrorBorder(DesdeMes, HastaMes);
            return false;
        }

        return true;


    }



    private void resetComboBoxBorders() {
        Border bordeStandar = BorderFactory.createEmptyBorder();
        DesdeAno.setBorder(bordeStandar);
        DesdeMes.setBorder(bordeStandar);
        HastaAno.setBorder(bordeStandar);
        HastaMes.setBorder(bordeStandar);
    }

    private Categoria getCategoriaActual() {
        return (Categoria) comboCategoria.getSelectedItem();
    }

    private JTable getTableDatos() {
        return datosLista;
    }

    public JPanel getGrafico() {
        return JPanelGrafico;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }


    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CATEGORIES_ALL:
                for(Categoria categoria : model.getAllCategorias()) {
                    comboCategoria.addItem(categoria);
                }
            break;
            case Model.DATA:
                List<String> listLineas = model.getRows();
                String[] rows = listLineas.toArray(new String[0]);
                datosLista.setModel(new EstadisticaTableModel(rows, model.getColumns(), model.getData()));
                datosLista.setRowHeight(30);
                TableColumnModel columnModel = datosLista.getColumnModel();

                if(model.getColumns().length > 2) {
                    datosLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    for(int i = 0; i < columnModel.getColumnCount(); i++) {
                        if(i == 0){
                            columnModel.getColumn(i).setPreferredWidth(170);
                        } else {
                            columnModel.getColumn(i).setPreferredWidth(120);
                        }
                    }
                } else {
                    datosLista.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                Float[][] data = model.getData();
                if(data != null && data.length > 0) {
                    for(int i = 0; i < model.getRows().size(); i++) {
                        for(int j = 0; j < model.getColumns().length; j++) {
                            if(i < data.length && j < data[i].length) {
                                dataSet.addValue(data[i][j], model.getRows(), get(i), model.getColumns()[j]);
                            }
                        }
                    }
                }

                JFreeChart lineChart = ChartFactory.createLineChart("Ventas por Mes", "Categoria", "Valores", dataset, PlotOrientation.VERTICAL, true,true,false);
                ChartPanel chartPanel = new ChartPanel(lineChart);

                JPanelGrafico.removeALL();
                JPanelGrafico.add(chartPanel);
                break;
            case Model.RANGE:
                Controller.actualizarD();
                actualizarV();
                break;
        }
        this.panel.revalidate();
    }

    private DefaultCategoryDataset getDefaultCatgoryDataset() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        Float[][] data = model.getData();
        String[] rows = model.getRows().toArray(new String[0]);
        String[] cols = model.getColumns();
        if (rows.length > 0 && cols.length > 0 && data.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                for (int j = 0; j < cols.length; j++) {
                    dataSet.addValue(data[i][j].intValue(), rows[i], cols[j]);
                }
            }
        }
        return dataSet;
    }

    public void actualizarV() {
        if(model != null) {
            datosLista.revalidate();
            datosLista.repaint();
        }
    }

}
