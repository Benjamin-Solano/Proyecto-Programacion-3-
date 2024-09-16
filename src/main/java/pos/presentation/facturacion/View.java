package pos.presentation.facturacion;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.*;

//SubPanel Buscar
import pos.presentation.facturacion.buscarPanel.buscarController;
import pos.presentation.facturacion.buscarPanel.buscarModel;
import pos.presentation.facturacion.buscarPanel.buscarView;


import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class View implements PropertyChangeListener, SubPanelesFactura {
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
    private JScrollPane listaCompra;

    private buscarView buscarView;
    private buscarController buscarController;
    private buscarModel buscarModel;
    private XmlPersister xmlPersister;
    private Service service=Service.instance();
    public JPanel getPanel() {
        return panel1;
    }

    public View() {
        buscarView = new buscarView();
        buscarModel = new buscarModel();
        buscarController = new buscarController(buscarView, buscarModel);
        buscarView.setController(buscarController);
        buscarView.setModel(buscarModel);
        buscarView.setSize(600, 400);
        buscarView.setLocationRelativeTo(null);
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
                cobrarFactura();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarView.setProductoSeleccionadoListener(View.this); // Registrar el View como escuchador
                abrirVentanaBusqueda();
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
                int selectedRow = list.getSelectedRow();
                if (selectedRow >= 0) { // Verificar si se ha seleccionado una fila
                    Linea lineaSeleccionada = model.getList().get(selectedRow); // Obtener la línea seleccionada
                    Producto producto = lineaSeleccionada.getProducto();
                    producto.setExistencias((int) (producto.getExistencias() + lineaSeleccionada.getCantidad()));
                    model.getList().remove(selectedRow);

                    model.notificarCambioLista();

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una línea para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                for(Linea temp: Service.instance().getLineas()) {
                    temp.getProducto().setExistencias((int)(temp.getCantidad()+temp.getProducto().getExistencias()));
                }
                try {
                    controller.clear();
                    JOptionPane.showMessageDialog(panel1, "Compra borrada", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                controller.actualizarComboBox();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        String codProducto = codigoProductoTxtfield.getText();
        if (codProducto == null || codProducto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El código del producto no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            valid=false;
        }
        Producto existeEnInventario = null;
        for (Producto producto : Service.instance().getProductos()) {
            if (producto.getCodigo().equals(codProducto)) {
                existeEnInventario = producto;
                break;
            }
        }
        if (existeEnInventario == null) {
            JOptionPane.showMessageDialog(null, "El producto no existe en el inventario", "Error", JOptionPane.ERROR_MESSAGE);
            valid=false;
        }
        // se ha seleccionado una fila
        int filaSeleccionada = list.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un producto de la lista", "Error", JOptionPane.ERROR_MESSAGE);
            valid=false;
        }
        // cantidad ingresada
        String cantidadStr = JOptionPane.showInputDialog(null, "Ingresa la cantidad del producto:", "Cantidad", JOptionPane.INFORMATION_MESSAGE);
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0 || cantidad > existeEnInventario.getExistencias()) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número positivo y no superar el stock disponible", "Error", JOptionPane.ERROR_MESSAGE);
                valid=false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            valid=false;
        }
        // algún descuento y si es un número válido
        String descuentoStr = JOptionPane.showInputDialog(null, "Ingresa el descuento:", "Descuento", JOptionPane.INFORMATION_MESSAGE);
        try {
            int descuento = Integer.parseInt(descuentoStr);
            if (descuento < 0 || descuento > existeEnInventario.getPrecioUnitario()) {
                JOptionPane.showMessageDialog(null, "El descuento no puede ser mayor al precio del producto", "Error", JOptionPane.ERROR_MESSAGE);
                valid=false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El descuento debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            valid=false;
        }
        valid=true;; // Si todas las validaciones se pasan
        return valid;
    }
    private void abrirVentanaBusqueda() {
        if (buscarView == null && buscarModel == null) {
            buscarView = new buscarView();
            buscarModel = new buscarModel();
            buscarController = new buscarController(buscarView, buscarModel);
            buscarView.setController(buscarController);
            buscarView.setModel(buscarModel);
            buscarView.setSize(600, 400);
            buscarView.setLocationRelativeTo(null);
        }
        buscarView.setVisible(true);
    }
    public void onProductoSeleccionado(Producto producto) {
        if (producto != null) {
            Linea nuevaLinea = new Linea();
            nuevaLinea.setProducto(producto);
            nuevaLinea.setCantidad(1); // Cantidad inicial
            nuevaLinea.setDescuento(0);
            System.out.println("Verificando si la línea ya existe...");
            if (service.existeLinea(nuevaLinea)) {
                try {
                    //Extraer esa linea y settearla
                    Linea lineaExistente = service.obtenerLineaEspecifica(nuevaLinea);
                    System.out.println("Línea existente encontrada. Actualizando cantidad...");
                    service.actualizarCantidad(lineaExistente);
                    System.out.println("Cantidad actualizada correctamente en la línea existente.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la línea: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    System.out.println("Guardando nueva línea...");
                    controller.save(nuevaLinea);
                    System.out.println("Línea procesada correctamente.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al guardar la línea: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            buscarView.setVisible(false);
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún producto.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cobrarFactura(){
        //Haceer metodo void aparte
        Double total = 0.0;
        for(Linea temp: Service.instance().getLineas()){ //revisar, tiempo de compilación
            total+= (temp.getProducto().getPrecioUnitario()-temp.getDescuento());
        }
        //delpliegue de sub-panel


        //según metodos de pago
    }

    public Factura take() {
        TableModel mod= (TableModel) list.getModel();
        Factura e = new Factura();
        e.setNumero(controller.generadorNumFactura());
        e.setCliente((Cliente)ClienteComboBox.getSelectedItem());
        e.setCajero((Cajero)CajeroComboBox.getSelectedItem());
        List<Linea> lineas = new ArrayList<>();
        for (int i = 0; i < mod.getRowCount(); i++) {
            Linea linea = (Linea) model.getList().get(i);
            lineas.add(linea);
        }
        e.setLineas(lineas);
        e.setFecha(LocalDate.now());
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

