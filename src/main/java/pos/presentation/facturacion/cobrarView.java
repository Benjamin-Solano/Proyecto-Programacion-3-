package pos.presentation.facturacion;

import javax.swing.*;
import java.awt.event.*;

public class cobrarView extends JDialog {
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

    public cobrarView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        cobrarView dialog = new cobrarView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
