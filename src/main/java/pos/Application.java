package pos;

import pos.logic.Service;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ex) {};

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });

        pos.presentation.clientes.Model clientesModel= new pos.presentation.clientes.Model();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        clientesController = new pos.presentation.clientes.Controller(clientesView,clientesModel);
        Icon clientesIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/client.png"));

        pos.presentation.cajeros.Model cajerosModel= new pos.presentation.cajeros.Model();
        pos.presentation.cajeros.View cajerosView = new pos.presentation.cajeros.View();
        cajerosController = new pos.presentation.cajeros.Controller(cajerosView,cajerosModel);
        Icon cajeroIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/cajero.png.png"));

        pos.presentation.productos.Model productosModel= new pos.presentation.productos.Model();
        pos.presentation.productos.View productosView = new pos.presentation.productos.View();
        ProductosController = new pos.presentation.productos.Controller(productosView, productosModel);
        Icon productosIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/productos.png"));

        pos.presentation.facturacion.Model facturacionModel= new pos.presentation.facturacion.Model();
        pos.presentation.facturacion.View facturacionView = new pos.presentation.facturacion.View();
        Factura = new pos.presentation.facturacion.Controller(facturacionView,facturacionModel);
        Icon facturacionIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/historial.png"));

        pos.presentation.historico.Model historicoModel= new pos.presentation.historico.Model();
        pos.presentation.historico.View historicoView = new pos.presentation.historico.View();
        historicoController = new pos.presentation.historico.Controller(historicoView,historicoModel);
        Icon historicoIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/facturaIcon.png"));

        pos.presentation.estadistica.Model estadisticaModel = new pos.presentation.estadistica.Model();
        pos.presentation.estadistica.View estadisticaView = new pos.presentation.estadistica.View();
        estadisticaController = new pos.presentation.estadistica.Controller(estadisticaView,estadisticaModel);
        Icon estadisticoIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/pdf.png"));


        tabbedPane.addTab("Clientes  ",clientesIcon,clientesView.getPanel());
        tabbedPane.addTab("Cajeros ",cajeroIcon,cajerosView.getPanel());
        tabbedPane.addTab("Productos ",productosIcon,productosView.getPanel());
        tabbedPane.addTab("Facturacion",facturacionIcon,facturacionView.getPanel());
        tabbedPane.addTab("Historico ",historicoIcon,historicoView.getPanel());
        tabbedPane.addTab("Estadistica",estadisticaView.getPanel());

        window.setSize(900,450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Objects.requireNonNull(Application.class.getResource("/pos/presentation/icons/icon.png")))).getImage());
        window.setTitle("POS: Point Of Sale");
        window.setVisible(true);

    }

    public static pos.presentation.clientes.Controller clientesController;
    public static pos.presentation.cajeros.Controller cajerosController;
    public static pos.presentation.productos.Controller ProductosController;
    public static pos.presentation.facturacion.Controller  Factura;
    public static pos.presentation.historico.Controller  historicoController;
    public static pos.presentation.estadistica.Controller estadisticaController;

    public static JFrame window;

    public final static int MODE_CREATE=1;
    public final static int MODE_EDIT=2;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
}
