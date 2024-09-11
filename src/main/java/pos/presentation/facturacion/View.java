package pos.presentation.facturacion;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.*;


import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JComboBox ClienteComboBox;
    private JComboBox CajeroComboBox;
    private JButton agregarButton;
    private JButton cobrarButton;
    private JButton buscarButton;
    private JButton cantidadButton;
    private JButton quitarButton;
    private JButton descuentoButton;
    private JButton cancelarButton;
    private JTextField codigoProductoTxtfield;
    private JTable list;
    private JLabel ProductoLbl;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private XmlPersister xmlPersister;

    public JPanel getPanel() {
        return panel1;
    }

    public View() {
        //desarrollo de botones
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codProducto= codigoProductoTxtfield.getText(); //Obtengo cod de usuario

                    Producto existeEnInventario=null; //busca en inventario
                    for (Producto producto : Service.instance().getProductos()) {  // listaDeProductos
                        if (producto.getCodigo().equals(codProducto)) {
                            existeEnInventario = producto;
                            break;
                        }
                    }
                    if(existeEnInventario!=null){
                        JOptionPane.showMessageDialog(null, "El producto existe, ingresa la cantidad del producto");
                        Linea nuevo = new Linea();
                        nuevo.setProducto(existeEnInventario);
                        nuevo.setCantidad(1); //por defecto
                        existeEnInventario.setExistencias((int) (existeEnInventario.getExistencias()-1)); //disminuyo
                        model.getList().add(nuevo);
                        model.setList(model.getList());
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "El producto no existe");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            Double total = 0.0;
                for(Linea temp: Service.instance().getLineas()){ //revisar, tiempo de compilación
                 total+= (temp.getProducto().getPrecioUnitario()-temp.getDescuento());
                }
                //delpliegue de sub-panel


                //según metodos de pago

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Abrir una ventana con todos los productos


                //buscar por descrip

                //reflejar codigo en campoText de producto


            }
        });
        cantidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceFilaSeleccionada = list.getSelectedRow();
                    Linea lineaSeleccionada = model.getList().get(indiceFilaSeleccionada);
                    try {
                        String newCantidadStr = JOptionPane.showInputDialog(null, "Ingresa la nueva cantidad:", lineaSeleccionada.getCantidad());
                        int newCantidad = Integer.parseInt(newCantidadStr); // Convertir a entero
//verificación de que esa cantidad si se pueda
                        if(newCantidad>lineaSeleccionada.getProducto().getExistencias()){
                            lineaSeleccionada.setCantidad(newCantidad);
                            lineaSeleccionada.getProducto().setExistencias(newCantidad-1); //del por defecto al agregar producto
                            model.setList(model.getList());
                            // Refrescar la vista (JTable)
                            list.revalidate();
                            list.repaint();
                        }
                        else { //buscar excepcion para quitar este:
                            JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser menor o igual a lo disponible en inventario", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
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
        quitarButton.addActionListener(new ActionListener() { //Este es similar al limpiarButton
            @Override
            public void actionPerformed(ActionEvent e) {
                //regresar cantidades modificadas a originales, revisar
                int anteriorExistencia=0;
                for(Linea temp: Service.instance().getLineas()) {
                    anteriorExistencia= (int)temp.getProducto().getExistencias();
                    temp.getProducto().setExistencias(temp.getCantidad()+anteriorExistencia);
                }
                controller.clear();
            }
        });
        descuentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceFilaSeleccionada = list.getSelectedRow();
                Linea lineaSeleccionada = model.getList().get(indiceFilaSeleccionada);
                String descuentoStr= JOptionPane.showInputDialog(null, "Ingresa descuento por aplicar:", lineaSeleccionada.getCantidad());
                int descuento = Integer.parseInt(descuentoStr);
                model.getCurrent().setDescuento(descuento);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //regresar cantidades modificadas a originales, revisar
                int anteriorExistencia=0;
                for(Linea temp: Service.instance().getLineas()) {
                    anteriorExistencia= (int)temp.getProducto().getExistencias();
                    temp.getProducto().setExistencias(temp.getCantidad()+anteriorExistencia);
                }
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel1, "Compra borrada", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        // las condiciones para las entradas de datos

        //hay unas que las tengo en los botones, hay que pasarlas

        return valid;
    }

    public Linea take() {
    Linea e = new Linea();
//cambios con setters
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
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.ARTICULO, TableModel.CATEGORIA,TableModel.CANTIDAD, TableModel.PRECIO, TableModel.DESCUENTO, TableModel.NETO, TableModel.IMPORTE };
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case Model.CURRENT:
                CajeroComboBox.setSelectedItem(model.getCajeros());  // Mostrar cajeros en el ComboBox
                ClienteComboBox.setSelectedItem(model.getClientes());

                if (model.getMode() == Application.MODE_EDIT) {
                    codigoProductoTxtfield.setEnabled(false);
                    quitarButton.setEnabled(true);
                } else {
                    codigoProductoTxtfield.setEnabled(true);
                    quitarButton.setEnabled(false);
                }

                ProductoLbl.setBorder(null);
                ProductoLbl.setToolTipText(null);

                break;
            case Model.CAJEROS:
                CajeroComboBox.setModel(new DefaultComboBoxModel<>(model.getCajeros().toArray(new Cajero[0])));
                break;
            case Model.CLIENTES:
                ClienteComboBox.setModel(new DefaultComboBoxModel<>(model.getClientes().toArray(new Cliente[0])));
                break;

            case Model.FILTER:
                codigoProductoTxtfield.setText(model.getFilter().getProducto().getCodigo());
                break;
        }

        this.panel1.revalidate();
    }
}

