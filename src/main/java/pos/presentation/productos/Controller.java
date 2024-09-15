package pos.presentation.productos;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.logic.Service;
import pos.data.XmlPersister;

import java.io.File;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
    try{
        List<Producto> productos = Service.instance().getProductos();//productos disponibles
        List<Categoria> categorias = XmlPersister.instance().load().getCategorias(); //cargando las categorias del xml

        // Validar que los productos y categorías no sean nulos
        if (productos != null && categorias != null) {
            model.init(productos, categorias);
            // Si la lista de productos no está vacía, establecer el primero como actual
            if (!productos.isEmpty()) {
                model.setCurrent(productos.get(0));
            }
        } else {
            System.out.println("Error: Productos o Categorías son nulos.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar productos o categorías: " + e.getMessage());
    }
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Producto filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void init() {
        try {
            // Cargar las categorías desde el archivo XML usando el método de persistencia
            List<Categoria> categorias = XmlPersister.instance().load().getCategorias();
            model.init(Service.instance().getProductos(), categorias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(Producto e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    public void edit(int row){
        Producto e = model.getList().get(row);
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
        model.setCurrent(new Producto());
    }
    public void generarReporte() throws Exception {
        String pdfPath = "reporte_productos.pdf";

        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.add(new Paragraph("Reporte de Productos"));

        // Crear la tabla
        float[] columnWidths = {100, 200, 100, 100, 100, 200}; // Ancho de columnas
        Table table = new Table(columnWidths);
        table.addCell("Código");
        table.addCell("Descripción");
        table.addCell("Unidad de Medida");
        table.addCell("Precio");
        table.addCell("Existencias");
        table.addCell("Categoría");
        List<Producto> productos = model.getList();

        // Llena la tabla con los productos
        for (Producto producto : productos) {
            table.addCell(producto.getCodigo());
            table.addCell(producto.getDescripcion());
            table.addCell(producto.getUnidadDeMedida());
            table.addCell(String.valueOf(producto.getPrecioUnitario()));
            table.addCell(String.valueOf(producto.getExistencias()));
            table.addCell(producto.getCategoria().getNombre());
        }
        document.add(table);  // Añade la tabla al documento
        document.close(); // Cerrar

        // Imprimir ruta donde se guardó
        System.out.println("Reporte de productos PDF generado en: " + new File(pdfPath).getAbsolutePath());
    }
}
