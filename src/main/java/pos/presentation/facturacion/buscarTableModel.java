package pos.presentation.facturacion;

import pos.logic.Producto;
import pos.presentation.AbstractTableModel;
import java.util.List;

public class buscarTableModel extends AbstractTableModel<Producto> implements javax.swing.table.TableModel {

    public buscarTableModel(int[] cols, List<Producto> rows) {
        super(cols, rows);
    }

    public static final int codigo=0;
    public static final int descripcion=1;
    public static final int unidadDeMedida=2;
    public static final int precioUnitario=3;
    public static final int existencias=4;
    public static final int categoria=5;

    @Override
    protected Object getPropetyAt(Producto e, int col) {
        switch (cols[col]){
            case codigo: return e.getCodigo();
            case descripcion: return e.getDescripcion();
            case unidadDeMedida: return e.getUnidadDeMedida();
            case precioUnitario: return e.getPrecioUnitario();
            case existencias: return e.getExistencias();
            case categoria: return e.getCategoria();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[6];
        colNames[codigo]= "Codigo";
        colNames[descripcion]= "Descripcion";
        colNames[unidadDeMedida]= "Unidad De Medida";
        colNames[precioUnitario]= "Precio Unitario";
        colNames[existencias]= "Existencias";
        colNames[categoria]= "Categoria";
    }

}
