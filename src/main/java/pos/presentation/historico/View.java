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
    private JTable list;
    private JTable table1;
    private JPanel panel;
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
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
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
    }

    private boolean validate() {
        boolean valid = true;
        if (clienteTxt.getText().isEmpty()) {
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
}
