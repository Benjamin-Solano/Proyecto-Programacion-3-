package pos.presentation.historico;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Factura;
import pos.logic.Linea;
import pos.data.Data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View {
    private JLabel clienteLbl;
    private JTextField clienteTxt;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listFacturas;
    private JPanel panel;
    private JTable listLineas;
    private Data data;

    private TableModel facturaTableModel;
    private TableModelLinea lineaTableModel;

    public JPanel getPanel() {
        return panel;
    }

    public View(){
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente filter = new Cliente();
                    filter.setNombre(clienteTxt.getText());
                    //search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        listFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listFacturas.getSelectedRow();
                controller.editFacturas(row);
            }
        });

        listLineas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listLineas.getSelectedRow();
                controller.editLineas(row);
            }
        });

        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.print();//pdf
                    JOptionPane.showMessageDialog(panel, "Reporte PDF generado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Inicializar modelos de tabla
        initTableModels();
    }

    private void initTableModels() {
        // Modelo de la tabla de facturas
        int[] facturaCols = {TableModel.Numero, TableModel.Cliente, TableModel.Cajero, TableModel.Fecha, TableModel.Importe};
        facturaTableModel = new TableModel(facturaCols, List.of()); // Inicialmente vacío
        listFacturas.setModel(facturaTableModel);

        // Modelo de la tabla de lineas
        int[] lineaCols = {TableModelLinea.Codigo, TableModelLinea.Articulo, TableModelLinea.Categoria, TableModelLinea.Cantidad, TableModelLinea.PrecioUnitario, TableModelLinea.Descuento, TableModelLinea.PrecioNeto, TableModelLinea.Importe};
        lineaTableModel = new TableModelLinea(lineaCols, List.of()); // Inicialmente vacío
        listLineas.setModel(lineaTableModel);

        // Listener para actualizar la tabla de lineas cuando se selecciona una factura
        listFacturas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = listFacturas.getSelectedRow();
                    if (selectedRow >= 0) {
                        Factura selectedFactura = model.getListFacturas().get(selectedRow);
                        updateLineasTable(selectedFactura.getLineas());
                    }
                }
            }
        });
    }

    private void updateLineasTable(List<Linea> lineas) {
        // Limpiar la tabla de líneas
        for (int i = 0; i < lineaTableModel.getRowCount(); i++) {
            for (int j = 0; j < lineaTableModel.getColumnCount(); j++) {
                lineaTableModel.setValueAt(null, i, j);
            }
        }

        // Actualizar la tabla de lineas con los nuevos datos
        for (int i = 0; i < lineas.size(); i++) {
            Linea linea = lineas.get(i);
            lineaTableModel.setValueAt(linea.getProducto().getCodigo(), i, TableModelLinea.Codigo);
            lineaTableModel.setValueAt(linea.getProducto().getDescripcion(), i, TableModelLinea.Articulo);
            lineaTableModel.setValueAt(linea.getProducto().getCategoria().getNombre(), i, TableModelLinea.Categoria);
            lineaTableModel.setValueAt(linea.getCantidad(), i, TableModelLinea.Cantidad);
            lineaTableModel.setValueAt(linea.getProducto().getPrecioUnitario(), i, TableModelLinea.PrecioUnitario);
            lineaTableModel.setValueAt(linea.getDescuento(), i, TableModelLinea.Descuento);
            lineaTableModel.setValueAt(linea.getProducto().getPrecioUnitario() - linea.getDescuento(), i, TableModelLinea.PrecioNeto);
            lineaTableModel.setValueAt((linea.getProducto().getPrecioUnitario() - linea.getDescuento()) * linea.getCantidad(), i, TableModelLinea.Importe);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (clienteTxt.getText().trim().isEmpty()) {
            valid = false;
            clienteTxt.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            clienteTxt.setToolTipText("Nombre requerido");
        } else {
            clienteTxt.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            clienteTxt.setToolTipText(null);
        }
        return valid;
    }

    public Cliente take() {
        Cliente e = new Cliente();
        e.setNombre(clienteTxt.getText());
        return e;
    }

    // MVC
    Model model;
    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        // model.addPropertyChangeListener(this); Esto es para las actualizaciones, falta por hacer
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
