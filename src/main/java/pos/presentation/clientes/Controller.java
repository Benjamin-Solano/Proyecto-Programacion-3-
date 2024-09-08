package pos.presentation.clientes;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.logic.Cliente;
import pos.logic.Service;

import javax.swing.text.StyleConstants;
import java.io.File;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Cliente()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cliente filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Cliente e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Cliente());
        search(model.getFilter());
    }

    public void edit(int row){
        Cliente e = model.getList().get(row);
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
        model.setCurrent(new Cliente());
    }
    public void generarReporte() throws Exception {
        String pdfPath = "reporte_clientes.pdf";

        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.setMargins(20, 25, 20, 25);
        //PdfFont font= PdfFontFactory.createFont();
        document.add(new Paragraph("Reporte de Clientes"));

        // Crear la tabla
        float[] columnWidths = {100,100,200,200,200}; // Ancho de columnas
        Table table = new Table(columnWidths);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Telefono");
        table.addCell("Email");
        table.addCell("Descuento");

        List<Cliente> clientes = model.getList();
        // Llena la tabla con cajeros
        for (Cliente cliente : clientes) {
            table.addCell(clientes.getFirst().getId());
            table.addCell(clientes.getFirst().getNombre());
            table.addCell(clientes.getFirst().getTelefono());
            table.addCell(clientes.getFirst().getEmail());
            table.addCell(String.valueOf(clientes.getFirst().getDescuento()));
        }
        document.add(table);  // Añade la tabla al documento
        document.close(); // Cerrar

        // Imprimir ruta donde se guardó
        System.out.println("Reporte de cajeros PDF generado en: " + new File(pdfPath).getAbsolutePath());
    }

}
