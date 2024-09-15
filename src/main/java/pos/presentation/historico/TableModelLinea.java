package pos.presentation.historico;

import pos.logic.Linea;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModelLinea extends AbstractTableModel<Linea> implements javax.swing.table.TableModel {

    public TableModelLinea(int[] cols, List<Linea> rows) {
        super(cols, rows);
    }

    public static final int Codigo = 0;
    public static final int Articulo = 1;
    public static final int Categoria = 2;
    public static final int Cantidad = 3;
    public static final int PrecioUnitario = 4;
    public static final int Descuento = 5;
    public static final int PrecioNeto = 6;
    public static final int Importe = 7;

    @Override
    protected Object getPropetyAt(Linea e, int col) {
        switch (cols[col]) {
            case Codigo:
                return e.getProducto().getCodigo();
            case Articulo:
                return e.getProducto().getDescripcion();
            case Categoria:
                return e.getProducto().getCategoria().getNombre();
            case Cantidad:
                return e.getCantidad();
            case PrecioUnitario:
                return e.getProducto().getPrecioUnitario();
            case Descuento:
                return e.getDescuento();
            case PrecioNeto:
                return e.getProducto().getPrecioUnitario() - e.getDescuento();
            case Importe:
                return (e.getProducto().getPrecioUnitario() - e.getDescuento()) * e.getCantidad();
            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[8];
        colNames[Codigo] = "Código";
        colNames[Articulo] = "Artículo";
        colNames[Categoria] = "Categoría";
        colNames[Cantidad] = "Cantidad";
        colNames[PrecioUnitario] = "Precio";
        colNames[Descuento] = "Descuento";
        colNames[PrecioNeto] = "Neto";
        colNames[Importe] = "Importe";
    }
}
