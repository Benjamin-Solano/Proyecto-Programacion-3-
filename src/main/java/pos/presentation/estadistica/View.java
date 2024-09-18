package pos.presentation.estadistica;

//import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import pos.logic.Categoria;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class View implements PropertyChangeListener {

    private JComboBox<Integer> AnioHasta;
    private JComboBox<Integer> AnioDesde;
    private JComboBox<String> mesDesde;
    private JComboBox<String> mesHasta;
    JComboBox<Categoria> comboBox5;
    private JTable list;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JPanel panel;
    private JPanel grafico;
    private JPanel datosPanel;


    private Model model;
    private Controller controller;

    public JPanel getPanel() {
        return panel;
    }

    public View() {

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.agregarTodasLasCategorias();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

    });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = list.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String categoriaNombre = (String) list.getValueAt(filaSeleccionada, 0); // Asegúrate de que el índice 0 es el de la categoría
                    controller.eliminarLineaACategoria(categoriaNombre);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getRowCount() > 0) {
                    model.clearData();
                }
            }
        });

        ActionListener comboBoxActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    controller.actualizarRango(
                            Integer.parseInt((String) AnioDesde.getSelectedItem()),
                            mesDesde.getSelectedIndex() + 1,
                            Integer.parseInt((String) AnioHasta.getSelectedItem()),
                            mesHasta.getSelectedIndex() + 1

                    );

                }
            }
        };

        mesDesde.addActionListener(comboBoxActionListener);
        mesHasta.addActionListener(comboBoxActionListener);
        AnioDesde.addActionListener(comboBoxActionListener);
        AnioHasta.addActionListener(comboBoxActionListener);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Categoria c = (Categoria) comboBox5.getSelectedItem();
                try {
                    controller.agregarCategoria(c);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    private boolean validar() {
        int anioDesde = Integer.parseInt((String) AnioDesde.getSelectedItem());
        int anioHasta = Integer.parseInt((String) AnioHasta.getSelectedItem());
        int mesInicio = mesDesde.getSelectedIndex() + 1;
        int mesFin = mesHasta.getSelectedIndex() + 1;

        resetComboBoxBorders();

        if (anioDesde > anioHasta) {
            setErrorBorder(AnioDesde, AnioHasta);
            return false;
        } else if (anioDesde == anioHasta && mesInicio > mesFin) {
            setErrorBorder(mesDesde, mesHasta);
            return false;
        }

        return true;
    }

    private void setErrorBorder(JComboBox<?>... comboBoxes) {
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        for (JComboBox<?> comboBox : comboBoxes) {
            comboBox.setBorder(redBorder);
        }
    }

    private void resetComboBoxBorders() {
        Border defaultBorder = BorderFactory.createEmptyBorder();
        AnioDesde.setBorder(defaultBorder);
        AnioHasta.setBorder(defaultBorder);
        mesDesde.setBorder(defaultBorder);
        mesHasta.setBorder(defaultBorder);
    }

    private Categoria obtenerCategoriaSeleccionada() {
        return (Categoria) comboBox5.getSelectedItem();
    }

    public void setController(Controller controller) {
        this.controller = controller;

    }

    public JTable getTable() {
        return list;

    }

    public JPanel getGraficoPanel(){
        return grafico;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CATEGORIES_ALL:

                for(Categoria categoria : model.getCategoriasAll()) {
                    comboBox5.addItem(categoria);
                }

                break;
            case Model.DATA:
                List<String> listRows = model.getRows();
                String[] rows = listRows.toArray(new String[0]);
                list.setModel(new EstadisticaTableModel(rows, model.getCols(),model.getData()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();

                if (model.getCols().length > 2) {
                    list.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    for (int i = 0; i < columnModel.getColumnCount(); i++) {
                        if (i == 0) {
                            columnModel.getColumn(i).setPreferredWidth(150);
                        } else {
                            columnModel.getColumn(i).setPreferredWidth(100);
                        }
                    }
                }
                else {

                    list.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                Float[][] data = model.getData();
                if (data != null && data.length > 0) {
                    for (int i = 0; i < model.getRows().size(); i++) {
                        for (int j = 0; j < model.getCols().length; j++) {
                            if (i < data.length && j < data[i].length) {
                                dataset.addValue(data[i][j], model.getRows().get(i), model.getCols()[j]);
                            }
                        }
                    }
                }

                JFreeChart lineChart = ChartFactory.createLineChart(
                        "Ventas por mes",
                        "Categorías",
                        "Valores",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );


                ChartPanel chartPanel = new ChartPanel(lineChart);


                grafico.removeAll();
                grafico.add(chartPanel);
                break;
            case Model.RANGE:
                controller.actualizarDatos();
                actualizarVista();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + evt.getPropertyName());
        }
        this.panel.revalidate();
    }

    private DefaultCategoryDataset getDefaultCategoryDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Float[][] data = model.getData();
        String[] rows = model.getRows().toArray(new String[0]);
        String[] cols = model.getCols();
        if (rows.length > 0 && cols.length > 0 && data.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                for (int j = 0; j < cols.length; j++) {
                    dataset.addValue(data[i][j].intValue(), rows[i], cols[j]);
                }
            }
        }
        return dataset;
    }

    public void fillCategoriaComboBox() {
        List<Categoria> categorias = model.getCategoriasAll();
        comboBox5.removeAllItems();
        for (Categoria categoria : categorias) {
            comboBox5.addItem(categoria);
        }
    }


    public void actualizarVista() {
        if (model != null) {
            //list.setModel(model.getTableModel());
            list.revalidate();
            list.repaint();
        }
    }

}