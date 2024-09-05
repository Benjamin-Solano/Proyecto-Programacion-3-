package pos.presentation.productos;
import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.data.XmlPersister;
import pos.data.Data;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JLabel Codigolbl;
    private JTextField CodigoTxtfield;
    private JLabel Preciolbl;
    private JTextField PrecioTxtField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JLabel Descripcionlbl;
    private JLabel Unidad;
    private JButton borrarButton;
    private JTextField descripcionTxtField;
    private JTextField unidadTxtField;
    private JLabel Categorialbl;
    private JTextField nombreTxtField;
    private JLabel nombrelbl;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable list;
    private JPanel panel;
    private JComboBox<Categoria> categoriaComboBox1;

    private XmlPersister xmlPersister;

    public JPanel getPanel() {
        return panel;
    }

    public View() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setCodigo(CodigoTxtfield.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Producto n = take();
                    try {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
        ///Categoria despliegue
// Método para cargar las categorías en el JComboBox

        categoriaComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Data data = XmlPersister.instance().load();  // Cargar datos desde XML
                    categoriaComboBox1.removeAllItems();          // Limpiar ComboBox antes de cargar nuevos datos
                    for (Categoria categoria : data.getCategorias()) {
                        categoriaComboBox1.addItem(categoria);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error al cargar categorías: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private boolean validate() {
        boolean valid = true;
        if (CodigoTxtfield.getText().isEmpty()) {
            valid = false;
            Codigolbl.setBorder(Application.BORDER_ERROR);
            Codigolbl.setToolTipText("Codigo requerido");
        } else {
            Codigolbl.setBorder(null);
            Codigolbl.setToolTipText(null);
        }

        if (descripcionTxtField.getText().isEmpty()) {
            valid = false;
            Descripcionlbl.setBorder(Application.BORDER_ERROR);
            Descripcionlbl.setToolTipText("Espacio requerido");
        } else {
            Descripcionlbl.setBorder(null);
            Descripcionlbl.setToolTipText(null);
        }

        if (unidadTxtField.getText().isEmpty()) {
            valid = false;
            Unidad.setBorder(Application.BORDER_ERROR);
            Unidad.setToolTipText("Unidad requerido");
        } else {
            Unidad.setBorder(null);
            Unidad.setToolTipText(null);
        }
//esto no sirve >:(
        if (PrecioTxtField.getText().equals("0.0")) { /*).isEmpty()*/
            valid = false;
            Preciolbl.setBorder(Application.BORDER_ERROR);
            Preciolbl.setToolTipText("Precio requerido");
        } else {
            Preciolbl.setBorder(null);
            Preciolbl.setToolTipText(null);
        }

        //validar del comboBox de categoria

        if (categoriaComboBox1.getSelectedItem() == null) {
            valid = false;
            Categorialbl.setBorder(Application.BORDER_ERROR);
            Categorialbl.setToolTipText("Categoría requerida");
        } else {
            Categorialbl.setBorder(null);
            Categorialbl.setToolTipText(null);
        }

        try {
            Float.parseFloat(PrecioTxtField.getText());
            Preciolbl.setBorder(null);
            Preciolbl.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            Preciolbl.setBorder(Application.BORDER_ERROR);
            Preciolbl.setToolTipText("Precio invalido");
        }

        return valid;
    }

    public Producto take() {
        Producto e = new Producto();
        e.setCodigo(CodigoTxtfield.getText());
        e.setDescripcion(descripcionTxtField.getText());
        e.setPrecioUnitario(Float.parseFloat(PrecioTxtField.getText())); //castear esto/parsearlo
        e.setUnidadDeMedida(unidadTxtField.getText());
        e.setCategoria((Categoria) categoriaComboBox1.getSelectedItem()); /*casteo*/
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
                int[] cols = {TableModel.codigo, TableModel.descripcion, TableModel.unidadDeMedida, TableModel.precioUnitario, TableModel.existencias, TableModel.categoria};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case Model.CURRENT:
                CodigoTxtfield.setText(model.getCurrent().getCodigo());
                descripcionTxtField.setText(model.getCurrent().getDescripcion());
                unidadTxtField.setText(model.getCurrent().getUnidadDeMedida());
                PrecioTxtField.setText(Float.toString(model.getCurrent().getPrecioUnitario())); //castear este porque es un float el precio
               // Existencia.setText("" + model.getCurrent().getDescuento());
                categoriaComboBox1.setSelectedItem(model.getCurrent().getCategoria());  // Mostrar categoría en el ComboBox

                if (model.getMode() == Application.MODE_EDIT) {
                    CodigoTxtfield.setEnabled(false);
                    borrarButton.setEnabled(true);
                } else {
                    CodigoTxtfield.setEnabled(true);
                    borrarButton.setEnabled(false);
                }

                Codigolbl.setBorder(null);
                Codigolbl.setToolTipText(null);
                nombrelbl.setBorder(null);
                nombrelbl.setToolTipText(null);
                Unidad.setBorder(null);
                Unidad.setToolTipText(null);
                Preciolbl.setBorder(null);
                Preciolbl.setToolTipText(null);
              //habria que ver aca que hay que hacer con categoria
                break;
            case Model.FILTER:
                CodigoTxtfield.setText(model.getFilter().getCodigo());
                break;
        }

        this.panel.revalidate();
    }

}
