package pos.presentation.facturacion;


import pos.Application;
import pos.data.XmlPersister;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Linea;
import pos.logic.Service;
import pos.data.XmlPersister;


import java.util.List;

public class Controller {
    View view;
    Model model;
    Service service = Service.instance();
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
    public void search(Linea filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Linea());
        model.setList(Service.instance().search(model.getFilter()));
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
    public void save(Linea e) throws  Exception{
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

    public String generadorNumFactura(){
        Service service = Service.instance();
        int contadorFacturas= service.contadorFacturas;
                String numeroFactura = "FAC-" + String.format("%04d", contadorFacturas);
        contadorFacturas++;
        return numeroFactura;
    }

    public boolean existeLinea(Linea nuevaLinea) {
        List<Linea> listaDeLineas = service.getLineas();
        for (Linea linea : listaDeLineas) {
            if (linea.getProducto().getCodigo().equals(nuevaLinea.getProducto().getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public void actualizarCantidad(Linea nuevaLinea) {
        for (Linea linea : service.getLineas()) {
            if (linea.getProducto().equals(nuevaLinea.getProducto())) {
                linea.setCantidad(linea.getCantidad() + nuevaLinea.getCantidad());
                break;
            }
        }
    }
}
