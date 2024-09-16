package pos.presentation.estadistica;

import pos.logic.Categoria;
import pos.logic.Producto;
import pos.presentation.AbstractTableModel;

import java.util.List;
//me quiero morir
public class TableModel  extends AbstractTableModel<Producto> implements javax.swing.table.TableModel{

    public TableModel(int[] cols, List<Producto> rows) {
        super(cols, rows);
    }

    public static final int Categoria=0;


}
