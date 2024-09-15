package pos.presentation.facturacion;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.Producto;
import pos.logic.Service;


import java.util.List;

public class buscarController {

    buscarVIew view;
    buscarModel model;

    public buscarController(buscarVIew view, buscarModel model) {
        try{

            List<Producto> productos = Service.instance().getProductos();
            System.out.println("Productos cargados: " + productos.size());
            if (productos != null  && !productos.isEmpty()) {
                model.init(productos);
                if (!productos.isEmpty()) {
                    model.setCurrent(productos.get(0));
                }
            }
            else {
                System.out.println("No se encontraron productos.");
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

    public void search(Producto filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
        model.setList(Service.instance().searchDescripcion(model.getFilter()));
    }

    public void init() {
        try {
            model.init(Service.instance().getProductos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void save(Producto e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    public void edit(int row){
        Producto e = model.getList().get(row);
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
        model.setCurrent(new Producto());
    }

}
