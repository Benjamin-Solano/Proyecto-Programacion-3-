package pos.presentation.historico;

import pos.Application;
import pos.logic.Cliente;
import pos.data.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View {
    private JLabel clienteLbl;
    private JTextField clienteTxt;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listFacturas;
    private JPanel panel;
    private JTable listLineas;
    private Data data;

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
    }

    private boolean validate() {
        boolean valid = true;
        if (clienteTxt.getText().isEmpty()||clienteTxt.getText().equals(" ")) {
            valid = false;
            clienteLbl.setBorder(Application.BORDER_ERROR);
            clienteLbl.setToolTipText("Nombre requerido");
        } else {
            clienteLbl.setBorder(null);
            clienteLbl.setToolTipText(null);
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


