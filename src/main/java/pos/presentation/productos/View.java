package pos.presentation.productos;
import pos.Application;
import pos.logic.Cliente;
import pos.logic.Producto;
import pos.presentation.clientes.Controller;
import pos.presentation.clientes.Model;
import pos.presentation.clientes.TableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JComboBox categoriaComboBox;
    private JTextField nombreTxtField;
    private JLabel nombrelbl;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable list;
    private JPanel panel;


    public JPanel getPanel() {
        return panel;
    }

    public View() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente filter = new Cliente();
                    filter.setNombre(nombreTxtField.getText());
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
                    Cliente n = take();
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

        if (nombreTxtField.getText().isEmpty()) {
            valid = false;
            nombrelbl.setBorder(Application.BORDER_ERROR);
            nombrelbl.setToolTipText("Nombre requerido");
        } else {
            nombrelbl.setBorder(null);
            nombrelbl.setToolTipText(null);
        }

        if (unidadTxtField.getText().isEmpty()) {
            valid = false;
            unidadTxtField.setBorder(Application.BORDER_ERROR);
            unidadTxtField.setToolTipText("Telefono requerido");
        } else {
            unidadTxtField.setBorder(null);
            unidadTxtField.setToolTipText(null);
        }

        if (PrecioTxtField.getText().isEmpty()) {
            valid = false;
            Preciolbl.setBorder(Application.BORDER_ERROR);
            Preciolbl.setToolTipText("Unidad requerida");
        } else {
            Preciolbl.setBorder(null);
            Preciolbl.setToolTipText(null);
        }
        //hay que hacer el validar del comboBox de categoria
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
        return e;
    }
//continuar aca----------------------------------------------
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
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.EMAIL, TableModel.DESCUENTO};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case Model.CURRENT:
                id.setText(model.getCurrent().getId());
                nombre.setText(model.getCurrent().getNombre());
                telefono.setText(model.getCurrent().getTelefono());
                email.setText(model.getCurrent().getEmail());
                descuento.setText("" + model.getCurrent().getDescuento());

                if (model.getMode() == Application.MODE_EDIT) {
                    id.setEnabled(false);
                    delete.setEnabled(true);
                } else {
                    id.setEnabled(true);
                    delete.setEnabled(false);
                }

                idLbl.setBorder(null);
                idLbl.setToolTipText(null);
                nombreLbl.setBorder(null);
                nombreLbl.setToolTipText(null);
                emailLbl.setBorder(null);
                emailLbl.setToolTipText(null);
                telefonoLbl.setBorder(null);
                telefonoLbl.setToolTipText(null);
                descuentoLbl.setBorder(null);
                descuentoLbl.setToolTipText(null);
                break;
            case Model.FILTER:
                searchNombre.setText(model.getFilter().getNombre());
                break;
        }

        this.panel.revalidate();
    }

}
