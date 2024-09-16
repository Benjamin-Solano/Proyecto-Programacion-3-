package pos.presentation.estadistica;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.Categoria;
import pos.logic.Cliente;
import pos.logic.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class Controller {
  /*
    View view;
    Model model;

    private JComboBox<Integer> ComboBoxAnoDesde;
    private JComboBox<Integer> ComboBoxAnoHasta;
    private JComboBox<String> ComboBoxMesDesde;
    private JComboBox<String> ComboBoxMesHasta;

    public Controller() {
        try {
            List<Categoria> categorias = XmlPersister.instance().load().getCategorias(); //cargar las categorias

            if (categorias != null) {
                model.init(categorias);
                // Si la lista de categorias no está vacía, establecer el primero como actual
                if (!categorias.isEmpty()) {
                    model.setCurrent(categorias.get(0));
                }
            } else {
                System.out.println("Error: Productos o Categorías son nulos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pueden leer las categorias");
        }

        // Inicializar los ComboBox
        ComboBoxAnoDesde = new JComboBox<>();
        ComboBoxAnoHasta = new JComboBox<>();
        ComboBoxMesDesde = new JComboBox<>();
        ComboBoxMesHasta = new JComboBox<>();

        // Configurar los ComboBox de Año
        int currentYear = Year.now().getValue();
        IntStream.rangeClosed(currentYear - 3, currentYear + 3)
                .forEach(year -> {
                    ComboBoxAnoDesde.addItem(year);
                    ComboBoxAnoHasta.addItem(year);
                });

        // Configurar los ComboBox de Mes
        for (Month mes : Month.values()) {
            String nombreMes = mes.getDisplayName(TextStyle.FULL, Locale.getDefault());
            ComboBoxMesDesde.addItem(nombreMes);
            ComboBoxMesHasta.addItem(nombreMes);
        }

        // Añadir ActionListener para la validación de fechas
        ComboBoxMesHasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int anoDesde = (int) ComboBoxAnoDesde.getSelectedItem();
                int anoHasta = (int) ComboBoxAnoHasta.getSelectedItem();
                int mesDesde = ComboBoxMesDesde.getSelectedIndex();
                int mesHasta = ComboBoxMesHasta.getSelectedIndex();

                if (anoHasta < anoDesde || (anoHasta == anoDesde && mesHasta <= mesDesde)) {
                    System.out.println("El mes final debe ser mayor que el mes inicial.");
                }
            }
        });

        // Añadir los ComboBox a la vista
        view.addComboBox(ComboBoxAnoDesde);
        view.addComboBox(ComboBoxMesDesde);
        view.addComboBox(ComboBoxAnoHasta);
        view.addComboBox(ComboBoxMesHasta);
    }
    public void init() {
        try {
            // Cargar las categorías desde el archivo XML usando el método de persistencia
            List<Categoria> categorias = XmlPersister.instance().load().getCategorias();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Categoria());
    }

   */
}
