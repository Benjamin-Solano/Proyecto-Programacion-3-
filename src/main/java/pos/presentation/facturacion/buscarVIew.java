package pos.presentation.facturacion;

import pos.logic.Producto;
import pos.logic.Service;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class buscarVIew extends javax.swing.JDialog implements PropertyChangeListener {
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton buttonOK;
    private javax.swing.JButton buttonCancel;
    private JTextField DescripcionTextField;
    private JTable listSubPanel;
    private JLabel DescripciónLbl;
    private Producto productoSeleccionado;
    //MVC
    private buscarController busController;
    private buscarModel busModel;

    public JPanel getPanel() {
        return contentPane;
    }

    public buscarVIew(){
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

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
            if (e.getClickCount() == 2) {
                int row = listSubPanel.getSelectedRow();
                if (row >= 0) {
                   productoSeleccionado = ((buscarTableModel) listSubPanel.getModel()).getRowAt(row);
                    busController.edit(row);
                }
            }
        }
    });

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

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }
    public void setController(buscarController buscarController) {
        this.busController = buscarController;
    }

    public void setModel(buscarModel model) {
        this.busModel = model;
        busModel.addPropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case buscarModel.LIST:
                actualizarTabla();
                break;
            case buscarModel.FILTER:
                DescripcionTextField.setText(busModel.getFilter().getDescripcion());
                break;
        }
        contentPane.revalidate();
    }
    private void actualizarTabla() {
        int[] cols = {
                buscarTableModel.codigo, buscarTableModel.descripcion,
                buscarTableModel.unidadDeMedida, buscarTableModel.precioUnitario,
                buscarTableModel.existencias, buscarTableModel.categoria
        };

        // Configuración de la tabla
        buscarTableModel tableModel = new buscarTableModel(cols, busModel.getList());
        listSubPanel.setModel(tableModel);
        listSubPanel.setRowHeight(30);

        TableColumnModel columnModel = listSubPanel.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(150);  // Ajustar ancho de columnas
        columnModel.getColumn(3).setPreferredWidth(150);
    }


    private void onOK() {
        String descripcionFiltro = DescripcionTextField.getText().trim();
        try {
            Producto filter = new Producto();
            filter.setDescripcion(descripcionFiltro);
            busController.search(filter);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onCancel(){
     // add your code here if necessary
    dispose();
    }

}
