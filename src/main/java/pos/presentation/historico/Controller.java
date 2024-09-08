package pos.presentation.historico;

import pos.Application;
import pos.data.XmlPersister;
import pos.logic.Categoria;
import pos.logic.Cliente;
import pos.logic.Service;

import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Cliente filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
        model.setList(Service.instance().search(model.getFilter()));
    }


    public void edit(int row) {
        Cliente e = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
        }
    }
}