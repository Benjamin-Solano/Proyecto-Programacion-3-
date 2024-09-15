package pos.presentation.historico;

import pos.logic.Factura;
import pos.logic.Linea;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Factura> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Factura> rows) {
        super(cols, rows);
    }

    public static final int Numero = 0;
    public static final int Cliente = 1;
    public static final int Cajero = 2;
    public static final int Fecha = 3;
    public static final int Importe = 4;

    @Override
    protected Object getPropetyAt(Factura e, int col) {
        switch (cols[col]) {
            case Numero:
                return e.getNumero();
            case Cliente:
                return e.getCliente().getNombre();
            case Cajero:
                return e.getCajero().getNombre();
            case Fecha:
                return e.getFecha();
            case Importe: {
                double totalImporte = 0.0;
                for (Linea linea : e.getLineas()) {
                    double importe = linea.getProducto().getPrecioUnitario() * linea.getCantidad();
                    double descuento = linea.getDescuento();
                    totalImporte += (importe - descuento);
                }
                return totalImporte;
            }
            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[Numero] = "Numero";
        colNames[Cliente] = "Cliente";
        colNames[Cajero] = "Cajero";
        colNames[Fecha] = "Fecha";
        colNames[Importe] = "Importe";
    }
}
