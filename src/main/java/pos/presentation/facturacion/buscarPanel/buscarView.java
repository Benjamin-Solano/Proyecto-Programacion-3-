package pos.presentation.facturacion.buscarPanel;

import pos.logic.Producto;
import pos.logic.Service;
import pos.logic.SubPanelesFactura;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class buscarView extends JDialog implements PropertyChangeListener, SubPanelesFactura {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel DescripcionLbl;
    private JTextField DescripcionTextField;
    private JTable listSubPanel;
    
    private Producto productoSeleccionado;
    private Service service = Service.instance();
    //MVC
    private buscarController busController;
    private buscarModel busModel;
    private SubPanelesFactura productoSelectionListener;

    public JPanel getPanel() {
        return contentPane;
    }
    public buscarView() {

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
                int existenciaActu;
                if (e.getClickCount() == 2) {
                    int row = listSubPanel.getSelectedRow();
                    if (row >= 0) {
                        productoSeleccionado = ((buscarTableModel) listSubPanel.getModel()).getRowAt(row);
                        busController.edit(row);
                        onProductoSeleccionado(productoSeleccionado);
                        //Cambia la existencia en la lista original de productos
                        existenciaActu = (int) service.obtenerProductoEspecifico(productoSeleccionado).getExistencias();
                        if (productoSelectionListener != null) {
                            productoSelectionListener.onProductoSeleccionado(productoSeleccionado);
                            service.obtenerProductoEspecifico(productoSeleccionado).setExistencias(existenciaActu - 1);
                            busController.actualizarLista();
                        }else{
                            JOptionPane.showMessageDialog(null, "Producto inexistente " );
                        }
                    }
                }
            }
        });
        contentPane.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                busController.actualizarLista();
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

    public void onProductoSeleccionado(Producto productoSeleccionado){
        this.productoSeleccionado = productoSeleccionado;
    }
    public Producto getProductoSeleccionado() {
        return this.productoSeleccionado;
    }

    public void setProductoSeleccionadoListener(SubPanelesFactura listener) {
        this.productoSelectionListener = listener;
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
