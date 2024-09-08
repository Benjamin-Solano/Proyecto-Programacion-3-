package pos.presentation.historico;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.data.XmlPersister;
import pos.logic.Categoria;
import pos.logic.Cliente;

import pos.logic.Service;

import java.io.File;
import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cliente filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
        model.setList(Service.instance().search(model.getFilter()));
    }


    public void edit(int row) {
        Cliente e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
        }
    }
    public void generarReporte() throws Exception {
        String pdfPath = "reporte_historico.pdf";

        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.add(new Paragraph("Reporte historico"));

        // Crear la tabla sugún los datos
        /*
        float[] columnWidths = {100, 200}; // Ancho de columnas
        Table table = new Table(columnWidths);
        table.addCell("Código");

        List<Producto> productos = model.getList();

        for (Producto producto : productos) {
            table.addCell(producto.getCodigo());

        }
        document.add(table);  // Añade la tabla al documento

        */

        document.close(); // Cerrar

        // Imprimir ruta donde se guardó
        System.out.println("Reporte historico PDF generado en: " + new File(pdfPath).getAbsolutePath());
    }
}