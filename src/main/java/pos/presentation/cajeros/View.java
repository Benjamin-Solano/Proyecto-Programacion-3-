package pos.presentation.cajeros;

import pos.Application;
import pos.logic.Cajero;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class View implements PropertyChangeListener {
    private JTextField idCajero;
    private JTextField nombreCajero;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField searchNom;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listCajero;
    private JTabbedPane tabbedPane1;
    private JPanel panel;
    private JLabel idLbl;
    private JLabel nombreLbl;

    public JPanel getPanel() {
        return panel;
    }

    public View() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cajero filter = new Cajero();
                    filter.setNombre(searchNom.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
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

        listCajero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listCajero.getSelectedRow();
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
        if (idCajero.getText().isEmpty()) {
            valid = false;
            idLbl.setBorder(Application.BORDER_ERROR);
            idLbl.setToolTipText("Codigo requerido");
        } else {
            idLbl.setBorder(null);
            idLbl.setToolTipText(null);
        }

        if (nombreCajero.getText().isEmpty()) {
            valid = false;
            nombreLbl.setBorder(Application.BORDER_ERROR);
            nombreLbl.setToolTipText("Nombre requerido");
        } else {
            nombreLbl.setBorder(null);
            nombreLbl.setToolTipText(null);
        }
        return valid;
    }
    public Cajero take() {
        Cajero e = new Cajero();
        e.setId(idCajero.getText());
        e.setNombre(nombreCajero.getText());
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
    public void propertyChange(PropertyChangeEvent evt) { //esto especificamente es de la implemetacion de clase
        switch (evt.getPropertyName()) {
            case pos.presentation.cajeros.Model.LIST:
                int[] cols = {pos.presentation.cajeros.TableModel.ID, pos.presentation.cajeros.TableModel.NOMBRE};
                listCajero.setModel(new TableModel(cols,model.getList()));
                listCajero.setRowHeight(30);
                TableColumnModel columnModel = listCajero.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                break;
            case pos.presentation.cajeros.Model.CURRENT:
                idCajero.setText(model.getCurrent().getId());
                nombreCajero.setText(model.getCurrent().getNombre());


                if (model.getMode() == Application.MODE_EDIT) {
                    idCajero.setEnabled(false);
                    borrarButton.setEnabled(true);
                } else {
                    idCajero.setEnabled(true);
                    borrarButton.setEnabled(false);
                }

                idLbl.setBorder(null);
                idLbl.setToolTipText(null);
                nombreLbl.setBorder(null);
                nombreLbl.setToolTipText(null);
                break;
            case pos.presentation.clientes.Model.FILTER:
                searchNom.setText(model.getFilter().getNombre());
                break;
        }

        this.panel.revalidate();
    }
}


