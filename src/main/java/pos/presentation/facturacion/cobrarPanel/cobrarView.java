package pos.presentation.facturacion.cobrarPanel;

import pos.logic.SubPanelFacturaCobrar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

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
    private Double precioTotal;   // Total que debe pagar el usuario
    private double restante;      // El saldo pendiente o vuelto
    SubPanelFacturaCobrar subPanelFacturaCobrar; // Referencia al panel principal

    public cobrarView(double precioTotal) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.precioTotal = precioTotal;

        ImporteTextField.setText(String.format("%.2f", precioTotal)); // Muestra el total de la compra al inicio

        addTextFieldListeners(); // Añade los listeners para actualizar el saldo restante

        // Listener para el botón "OK"
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(); // Acción cuando se presiona OK
            }
        });

        // Listener para el botón "Cancel"
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(); // Acción cuando se presiona Cancel
            }
        });

        // Manejo de cierre de la ventana con la "X"
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(); // Cerrar la ventana si se presiona el ícono de cerrar
            }
        });

        // Manejo de la tecla Escape para cancelar
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(); // Cerrar la ventana con ESC
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // Implementación del método de la interfaz para actualizar el total
    @Override
    public void totalCompra(double total) {
        this.precioTotal = total; // Actualiza el precio total si es necesario
    }

    // Método para obtener el saldo restante
    public double getPrecioRestante() {
        return this.restante;
    }

    // Añade listeners a los campos de texto para actualizar el saldo restante cuando cambien
    private void addTextFieldListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarImporteRestante(); // Llamada cuando se inserta texto
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarImporteRestante(); // Llamada cuando se borra texto
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarImporteRestante(); // Llamada cuando cambia el texto
            }
        };

        // Agregamos los listeners a cada campo de texto
        EfectivoTextField.getDocument().addDocumentListener(listener);
        TarjetaTextField.getDocument().addDocumentListener(listener);
        ChequeTextField.getDocument().addDocumentListener(listener);
        SinpeTextField.getDocument().addDocumentListener(listener);
    }

    // Método para actualizar el saldo restante basado en los pagos ingresados
    private void actualizarImporteRestante() {
        // Obtén los valores de los campos de texto y conviértelos a números
        double efectivo = parseDoubleOrZero(EfectivoTextField.getText());
        double tarjeta = parseDoubleOrZero(TarjetaTextField.getText());
        double cheque = parseDoubleOrZero(ChequeTextField.getText());
        double sinpe = parseDoubleOrZero(SinpeTextField.getText());

        // Calcula el total pagado
        double totalPagado = efectivo + tarjeta + cheque + sinpe;
        restante = precioTotal - totalPagado; // Calcula el saldo restante

        if (restante >= 0) {
            ImporteTextField.setText(String.format("%.2f", restante)); // Muestra el saldo restante
        } else {
            ImporteTextField.setText(String.format("Vuelto: %.2f", Math.abs(restante))); // Muestra el vuelto si hay exceso
        }

        /* Llamamos al método en el panel principal para actualizar la información
        if (subPanelFacturaCobrar != null) {
            subPanelFacturaCobrar.totalCompra(restante); // Notificamos el saldo restante al panel
        } else {
            JOptionPane.showMessageDialog(null, "El panel de factura no está disponible.");
        }*/

        // Desactivar los campos de entrada si ya no hay saldo pendiente
        if (restante <= 0) {
            disableInputs(); // Desactiva los campos de pago
        }
    }

    // Desactiva los campos de entrada de pago cuando el saldo es 0 o menor
    private void disableInputs() {
        EfectivoTextField.setEnabled(false);
        TarjetaTextField.setEnabled(false);
        ChequeTextField.setEnabled(false);
        SinpeTextField.setEnabled(false);
    }

    // Método para asignar el subPanelFacturaCobrar (referencia al panel principal)
    public void setSubPanelFacturaCobrar(SubPanelFacturaCobrar subPanelFacturaCobrar) {
        this.subPanelFacturaCobrar = subPanelFacturaCobrar; // Asignamos la referencia al panel principal
    }
    // Acción cuando se presiona el botón OK
    private void onOK() {
        // Validar si el saldo restante es suficientemente cercano a cero (por precisión decimal)
        if (Math.abs(this.getPrecioRestante()) < 0.01) {
            JOptionPane.showMessageDialog(null, "Compra completada. Vuelto: $" + Math.abs(this.getPrecioRestante()));
            subPanelFacturaCobrar.totalCompra(restante); // Notificamos el saldo restante al panel
            dispose(); // Cierra la ventana si no hay saldo pendiente
        } else {
            JOptionPane.showMessageDialog(null, "Saldo pendiente: $" + this.getPrecioRestante());
        }
    }
    private double parseDoubleOrZero(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    private void onCancel() {
        dispose();
    }
}
