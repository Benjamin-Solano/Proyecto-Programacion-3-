package pos.presentation.historico;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import pos.Application;
import pos.data.XmlPersister;
import pos.logic.*;

import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        try {
            List<Factura> facturas = Service.instance().getFacturas();
            List<Linea> lineas = Service.instance().getLineas();

            if (facturas != null && lineas != null) {
                model.init(facturas, lineas);
                // Si la lista de productos no está vacía, establecer el primero como actual
                if (!facturas.isEmpty()) {
                    model.setCurrentFactura(facturas.get(0));
                }if(!lineas.isEmpty()){
                    model.setCurrentLinea(lineas.get(0));
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

    public void search(Factura filter) throws Exception {
        model.setFilterFactura(filter);
        model.setMode(Application.MODE_CREATE);
        model.setFilterFactura(new Factura());
        model.setListFacturas(Service.instance().search(model.getFilterFactura()));
    }

    public void init() {
        try {
            List<Factura> facturas =Service.instance().getFacturas();
            List<Linea> lineas = Service.instance().getLineas();
            model.init(facturas,lineas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editFacturas(int row){
        Factura e = model.getListFacturas().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrentFactura(Service.instance().read(e));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void editLineas(int row){
        Linea e = model.getListLineas().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrentLinea(Service.instance().read(e));
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }

    public void print()throws Exception{
        String dest="historico.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        //Document document = new Document(pdf, PageSize.A4.rotate());
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Historico").setFont(font).setBold().setFontSize(20f), false));
        //header.addCell(getCell(new Image(ImageDataFactory.create("logo.jpg")), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));

        com.itextpdf.kernel.colors.Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;
        Table body = new Table(7);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Factura").setBackgroundColor(bkg).setFontColor(frg), true));
        body.addCell(getCell(new Paragraph("Producto").setBackgroundColor(bkg).setFontColor(frg), true));

        for(Factura e: model.getListFacturas()){
            body.addCell(getCell(new Paragraph(e.getNumero()), true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getCajero().getId())), true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getFecha().getMonth())), true));
        }
        for(Linea e:model.getListLineas()){
            body.addCell(getCell(new Paragraph(e.getProducto().getCodigo()), true));
            body.addCell(getCell(new Paragraph(e.getProducto().getDescripcion()), true));
            body.addCell(getCell(new Paragraph(e.getProducto().getCategoria().getNombre()), true));
            body.addCell(getCell(new Paragraph(e.getProducto().getUnidadDeMedida()), true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getProducto().getPrecioUnitario())), true));
        }
        document.add(body);
        document.close();
    }

    private Cell getCell(Paragraph paragraph, boolean hasBorder) {
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(TextAlignment.CENTER);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

//    public void generarReporte() throws Exception {
//        String pdfPath = "reporte_historico.pdf";
//
//        PdfWriter writer = new PdfWriter(pdfPath);
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document document = new Document(pdfDoc);
//        document.add(new Paragraph("Reporte historico"));
//
//        // Crear la tabla sugún los datos
//        /*
//        float[] columnWidths = {100, 200}; // Ancho de columnas
//        Table table = new Table(columnWidths);
//        table.addCell("Código");
//
//        List<Producto> productos = model.getList();
//
//        for (Producto producto : productos) {
//            table.addCell(producto.getCodigo());
//
//        }
//        document.add(table);  // Añade la tabla al documento
//
//        */
//
//        document.close(); // Cerrar
//
//        // Imprimir ruta donde se guardó
//        System.out.println("Reporte historico PDF generado en: " + new File(pdfPath).getAbsolutePath());
//    }
}