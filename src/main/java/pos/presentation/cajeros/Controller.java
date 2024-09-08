package pos.presentation.cajeros;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import pos.Application;
import pos.logic.Cajero;
import pos.logic.Service;

import java.io.File;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Cajero()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cajero filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cajero());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Cajero e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Cajero());
        search(model.getFilter());
    }

    public void edit(int row){
        Cajero e = model.getList().get(row);
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
        model.setCurrent(new Cajero());
    }
    public void generarReporte() throws Exception {
        String pdfPath = "reporte_cajeros.pdf";

        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.add(new Paragraph("Reporte de Cajeros"));

        // Crear la tabla
        float[] columnWidths = {100, 200}; // Ancho de columnas
        Table table = new Table(columnWidths);
        table.addCell("ID");
        table.addCell("Nombre");

        List<Cajero> cajeros = model.getList();
        // Llena la tabla con cajeros
        for (Cajero cajero : cajeros) {
            table.addCell(cajeros.getFirst().getId());
            table.addCell(cajeros.getFirst().getNombre());
        }
        document.add(table);  // Añade la tabla al documento
        document.close(); // Cerrar

        // Imprimir ruta donde se guardó
        System.out.println("Reporte de cajeros PDF generado en: " + new File(pdfPath).getAbsolutePath());
    }

}
