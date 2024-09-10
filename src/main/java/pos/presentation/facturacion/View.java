package pos.presentation.facturacion;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Linea;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JComboBox ClienteComboBox;
    private JComboBox CajeroComboBox;
    private JButton agregarButton;
    private JButton cobrarButton;
    private JButton buscarButton;
    private JButton cantidadButton;
    private JButton quitarButton;
    private JButton descuentoButton;
    private JButton cancelarButton;
    private JTextField codigoProductoTxtfield;
    private JTable list;
    private JLabel ProductoLbl;

    private XmlPersister xmlPersister;

    public JPanel getPanel() {
        return panel1;
    }

    public View() {
        //desarrollo de botones
    }

    private boolean validate() {
        boolean valid = true;
        // las condiciones para las entradas de datos

        return valid;
    }

    public Linea take() {
    Linea e = new Linea();
//cambios con setters
    return e;
    }

    // MVC
   Model model;
    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.ARTICULO, TableModel.CATEGORIA,TableModel.CANTIDAD, TableModel.PRECIO, TableModel.DESCUENTO, TableModel.NETO, TableModel.IMPORTE };
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case Model.CURRENT:

                CajeroComboBox.setSelectedItem(model.getCajeros());  // Mostrar cajeros en el ComboBox
                ClienteComboBox.setSelectedItem(model.getClientes());

                if (model.getMode() == Application.MODE_EDIT) {
                    codigoProductoTxtfield.setEnabled(false);
                   // borrarButton.setEnabled(true);
                } else {
                    codigoProductoTxtfield.setEnabled(true);
                  //  borrarButton.setEnabled(false);
                }

                ProductoLbl.setBorder(null);
                ProductoLbl.setToolTipText(null);

                break;
            case Model.CAJEROS:
                CajeroComboBox.setModel(new DefaultComboBoxModel<>(model.getCajeros().toArray(new Cajero[0])));
                break;
            case Model.CLIENTES:
                ClienteComboBox.setModel(new DefaultComboBoxModel<>(model.getClientes().toArray(new Cliente[0])));
                break;

            case Model.FILTER:
                codigoProductoTxtfield.setText(model.getFilter().getProducto().getCodigo());
                break;
        }

        this.panel1.revalidate();
    }
}
