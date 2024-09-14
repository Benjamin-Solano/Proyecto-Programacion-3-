package pos.presentation.facturacion;

import pos.logic.Linea;
import pos.logic.Producto;
import pos.presentation.productos.TableModel;
import pos.logic.Service;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;


public class buscarVIew extends javax.swing.JDialog {
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton buttonOK;
    private javax.swing.JButton buttonCancel;
    private JTextField DescripcionTextField;
    private JTable listSubPanel;
    private JLabel DescripciónLbl;
    private TableModel productoTableModel; //Reustilización de grafica

    // Controlador y Modelo
    Controller control;
    Model mod;

    public JPanel getPanel() {
        return contentPane;
    }

    public buscarVIew(Linea list){
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);
    Linea lineaT = new Linea();
    List<Producto> productos = Service.instance().getProductos(); // Obtener productos del servicio
        int[] cols = {TableModel.codigo, TableModel.descripcion, TableModel.unidadDeMedida, TableModel.precioUnitario, TableModel.existencias, TableModel.categoria};
        productoTableModel = new TableModel(cols, productos);
        listSubPanel.setModel(productoTableModel);

    buttonOK.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent e){
            onOK();
        }
    });

    buttonCancel.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent e){
            onCancel();
        }
    });

    listSubPanel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int row = listSubPanel.getSelectedRow();
            control.edit(row);
            //Se debe enviar la información al agregar del principal

        }
    });
     // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
       onCancel();
      }
    });

     // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(  new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            onCancel();
        }
        },  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0),  javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    private void onOK(){
     // add your code here
        try {
            Producto filter = new Producto();
            filter.setDescripcion(DescripcionTextField.getText()); // Filtrar por descripción
          // this.search(filter);  // Método del controlador que busca por descripción
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    dispose();
    }

    private void onCancel(){
     // add your code here if necessary
    dispose();
    }

    }
