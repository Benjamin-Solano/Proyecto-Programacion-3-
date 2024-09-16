package pos.presentation.facturacion.cobrarPanel;


import pos.logic.SubPanelFacturaCobrar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class cobrarView extends JDialog implements SubPanelFacturaCobrar {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField TarjetaTextField;
    private JTextField EfectivoTextField;
    private JTextField ChequeTextField;
    private JTextField SinpeTextField;
    private JTextField ImporteTextField;
    private JLabel EfectivoLbl;
    private JLabel TarjetaLbl;
    private JLabel ChequeLbl;
    private JLabel SinpeLbl;
    private Double precioTotal;
    private double restante;
    public cobrarView(double precioTotal) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.precioTotal =precioTotal;

        ImporteTextField.setText(String.format("%.2f", precioTotal));

        addTextFieldListeners();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public void totalCompra(double total){
            this.precioTotal =total;
    }
    public double getPrecioRestante(){return this.restante;}
    private void addTextFieldListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarImporteRestante();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarImporteRestante();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarImporteRestante();
            }
        };

        EfectivoTextField.getDocument().addDocumentListener(listener);
        TarjetaTextField.getDocument().addDocumentListener(listener);
        ChequeTextField.getDocument().addDocumentListener(listener);
        SinpeTextField.getDocument().addDocumentListener(listener);

    }
    private void actualizarImporteRestante() {
        // Obtén los valores de los campos de texto y conviértelos a números
        double efectivo = parseDoubleOrZero(EfectivoTextField.getText());
        double tarjeta = parseDoubleOrZero(TarjetaTextField.getText());
        double cheque = parseDoubleOrZero(ChequeTextField.getText());
        double sinpe = parseDoubleOrZero(SinpeTextField.getText());

        double totalPagado = efectivo + tarjeta + cheque + sinpe;
        restante = precioTotal - totalPagado;

        if (restante >= 0) {
            ImporteTextField.setText(String.format("%.2f", restante)); // Muestra el importe restante
        } else {
            ImporteTextField.setText(String.format("Vuelto: %.2f", Math.abs(restante))); // Muestra el vuelto si hay exceso
        }
    }
    private void onOK() {
        //Esto deberia ser un try o algo asi con panel
        if(this.getPrecioRestante()==0){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(null, "Saldo pendiente: $" + this.getPrecioRestante());
        }
    }
    private double parseDoubleOrZero(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0; // Devuelve 0 si no se puede convertir
        }
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
