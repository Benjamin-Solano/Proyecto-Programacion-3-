package pos.presentation.facturacion;


import pos.Application;
import pos.data.XmlPersister;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.logic.*;
import pos.data.XmlPersister;


import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        try {
            List<Linea> lineas = Service.instance().getLineas();

            List<Cliente> clientes = XmlPersister.instance().load().getClientes();
            List<Cajero> cajeros = XmlPersister.instance().load().getCajeros();

            // Validar que los lineas, cajeros y clientes no sean nulos
            if (lineas != null && cajeros!= null && clientes!=null) {
                model.init(lineas, cajeros, clientes);
                // Si la lista de lineas no está vacía, establecer el primero como actual
                if (!lineas.isEmpty()) {
                    model.setCurrent(lineas.get(0));
                }
            } else {
                System.out.println("Error: cajero o clientes,  son nulos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar cajero o clientes: " + e.getMessage());
        }

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }


    public void init() {
        try {
            // Cargar los cajeros y cleintes desde el archivo XML usando el método de persistencia
            List<Cajero> cajeros = XmlPersister.instance().load().getCajeros();
            List<Cliente> clientes = XmlPersister.instance().load().getClientes();

            model.init(Service.instance().getLineas(),cajeros,clientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchF(Factura filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrentFactura(new Factura());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void saveF(Factura factura) throws Exception {
        try {
            switch (model.getMode()) {
                case Application.MODE_CREATE:
                    Service.instance().create(factura);
                    break;
                case Application.MODE_EDIT:
                    Service.instance().update(factura);
                    break;
            }
            model.setFilter(new Factura());
            searchF(model.getFilterFactura());
        } catch (Exception ex) {
            System.out.println("Error al guardar la factura: " + ex.getMessage());
            throw ex;
        }
    }

    public void clearFactura() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrentFactura(new Factura());
    }
    public void actualizarTotales() {
        int totalArticulos = model.currentFactura.cantProductosT();
        double subtotal = model.currentFactura.precioNetoPagarT();
        double descuentos = model.currentFactura.ahorroXDescuentoT();
        double total = model.currentFactura.precioTotalPagar();
        view.actualizarCamposTotales(totalArticulos, subtotal, descuentos, total);
    }


    //-----------------------------------De Lineas de Factura----------------------------------------------------


    public void search(Linea filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Linea());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Linea e) throws  Exception {
        try {
            switch (model.getMode()) {
                case Application.MODE_CREATE:
                    Service.instance().create(e);
                    break;
                case Application.MODE_EDIT:
                    Service.instance().update(e);
                    break;
            }
            model.setFilter(new Linea());
            search(model.getFilter());
        }
        catch (Exception ex) {
            System.out.println("Error al guardar la línea: " + ex.getMessage());
            throw ex;
        }
    }

    public void edit(int row){
        Linea e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Linea());
    }
    public void actualizarComboBox(){
        try {
            // Obtener las listas actualizadas del servicio
            Service service = Service.instance();
            List<Cajero> cajeros = service.getCajeros(); // Obtener la lista de cajeros desde el servicio
            List<Cliente> clientes = service.getClientes(); // Obtener la lista de clientes desde el servicio

            if (cajeros != null && clientes != null) {
                // Actualizar el modelo con los nuevos datos
                model.setCajeros(cajeros);
                model.setClientes(clientes);
            } else {
                System.out.println("Error: Los cajeros o clientes son nulos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar los datos de los ComboBox: " + e.getMessage());
        }
    }
    public String generadorNumFactura(){
        Service service = Service.instance();
        int contadorFacturas= service.contadorFacturas;
        String numeroFactura = "FAC-" + String.format("%04d", contadorFacturas);
        contadorFacturas++;
        return numeroFactura;
    }

}
