package pos.presentation.estadistica;

import javax.swing.table.AbstractTableModel;

//me quiero morir

// Aun falta mucho para eso Jose...

public class TableModel extends AbstractTableModel {
    private String[] rows;
    private String[] columns;
    private Float[][] data;

    public TableModel(String[] rows, String[] columns, Float[][] data) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }
    @Override
    public int getRowCount() { return data.length; }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) {
            return rows[rowIndex];
        } else {
            return data[rowIndex][columnIndex -1];
        }
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0) {
            return "CATEGORIA";
        } else {
            return String.valueOf(columns[column-1]);
        }
    }

}
