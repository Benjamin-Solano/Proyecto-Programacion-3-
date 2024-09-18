/*package pos.presentation.estadistica;

import pos.presentation.AbstractTableModel;

import java.util.Arrays;

public class EstadisticaTableModel extends AbstractTableModel {
    private String[] rows;
    private String[] cols;
    private Float[][] data;

    public EstadisticaTableModel(String[] rows, String[] cols, Float[][] data) {
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return cols.length + 1; // +1 para incluir la columna de "Categoría"
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rows[rowIndex];
        } else if (rowIndex >= 0 && rowIndex < data.length && columnIndex > 0 && columnIndex <= data[rowIndex].length) {
            return data[rowIndex][columnIndex - 1];
        }
        return null;
    }

    @Override
    protected Object getPropetyAt(Object o, int col) {
        return null;
    }

    @Override
    protected Object getPropertyAt(Object o, int col) {
        return null;
    }

    @Override
    protected void initColNames() {

    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "Categoría";
        } else {
            return cols[col - 1];
        }
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (columnIndex == 0) ? String.class : Float.class;  // Usar `Float.class` para las columnas numéricas
    }

    public void agregarDatosCategoria(String categoria, Float[] valoresMensuales) {
        boolean categoriaExistente = false;

        for (int i = 0; i < getRowCount(); i++) {
            if (getValueAt(i, 0).equals(categoria)) {
                categoriaExistente = true;
                for (int j = 1; j < getColumnCount(); j++) {
                    data[i][j - 1] = valoresMensuales[j - 1];
                }
                break;
            }
        }

        if (!categoriaExistente) {
            String[] newRows = Arrays.copyOf(rows, rows.length + 1);
            Float[][] newData = Arrays.copyOf(data, data.length + 1);

            newData[newData.length - 1] = valoresMensuales;
            newRows[newRows.length - 1] = categoria;

            rows = newRows;
            data = newData;
        }
        fireTableDataChanged();  // Refrescar la tabla
    }

    public int obtenerColumnaPorMes(int mesSeleccionado) {
        for (int i = 1; i < getColumnCount(); i++) {
            if (getColumnName(i).contains(String.valueOf(mesSeleccionado))) {
                return i - 1;  // Ajustar índice, ya que los meses empiezan en la columna 1
            }
        }
        return -1;  // Si no encuentra el mes
    }

    public void actualizarDatosPorFecha(int mesSeleccionado, int fila, Float valor) {
        int columna = obtenerColumnaPorMes(mesSeleccionado);  // Mapea el mes a la columna
        if (columna != -1 && fila >= 0 && fila < data.length) {
            data[fila][columna] = valor;
            fireTableCellUpdated(fila, columna + 1);// Notificar el cambio en la celda
        }
    }


}*/

package pos.presentation.estadistica;

import javax.swing.table.AbstractTableModel;

public class EstadisticaTableModel extends AbstractTableModel {
    private String[] rows;
    private String[] cols;
    private Float[][] data;

    public EstadisticaTableModel(String[] rows, String[] cols, Float[][] data) {
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return cols.length + 1;
    }


    @Override
    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return rows[row];
        } else {
            return data[row][col - 1];
        }
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "Categoria";
        } else {
            return String.valueOf(cols[col - 1]);
        }
    }
}
