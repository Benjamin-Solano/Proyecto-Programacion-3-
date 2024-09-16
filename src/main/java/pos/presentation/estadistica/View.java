package pos.presentation.estadistica;

import pos.Application;
import pos.logic.Categoria;
import pos.presentation.productos.TableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View {
    private JPanel panel;
    private JComboBox comboBoxDesdeAno;
    private JComboBox comboBoxDesdeMes;
    private JComboBox comboBoxHastaAno;
    private JComboBox comboBoxHastaMes;
    private JComboBox comboBoxCategoria;
    private JButton buttonCheck1;
    private JButton buttonCheck2;
    private JTable list;
    private JButton clear1Button;
    private JButton clearAllButton;
/*
    public Component getPanel() {
        return panel;
    }

    public View(){

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
    }
    // MVC
    pos.presentation.estadistica.Model model;
    pos.presentation.estadistica.Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener((PropertyChangeListener) this);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.categorias.Model.LIST:
                int[] cols = {pos.presentation.productos.TableModel.codigo, pos.presentation.productos.TableModel.descripcion, pos.presentation.productos.TableModel.unidadDeMedida, pos.presentation.productos.TableModel.precioUnitario, pos.presentation.productos.TableModel.existencias, pos.presentation.productos.TableModel.categoria};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case pos.presentation.categorias.Model.CURRENT:
                comboBoxDesdeAno.setSelectedItem(model.getCurrent().get());  // Mostrar categoría en el ComboBox, actual seleccionada

                if (model.getMode() == Application.MODE_EDIT) {
                    CodigoTxtfield.setEnabled(false);
                    borrarButton.setEnabled(true);
                } else {
                    CodigoTxtfield.setEnabled(true);
                    borrarButton.setEnabled(false);
                }


                break;
            case pos.presentation.productos.Model.CATEGORIAS:
                comboBoxCategoria.setModel(new DefaultComboBoxModel<>(model.getCategorias().toArray(new Categoria[0])));
                comboBoxDesdeAno;
                private JComboBox comboBoxDesdeMes;
                private JComboBox comboBoxHastaAno;
                private JComboBox comboBoxHastaMes;
                private JComboBox comboBoxCategoria;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + evt.getPropertyName());
        }

        this.panel.revalidate();
    }
*/
}
