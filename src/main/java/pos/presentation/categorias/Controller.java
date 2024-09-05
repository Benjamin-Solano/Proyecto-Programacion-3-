package pos.presentation.categorias;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Service;

import java.util.List;

public class Controller {
    Model model;

    public Controller(Model model) {
        model.init(Service.instance().search(new Categoria()));
        this.model = model;
    }

    public void search(Categoria filter) throws  Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Categoria());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Categoria e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Categoria());
        search(model.getFilter());
    }

    public void edit(int row){
        Categoria e = model.getList().get(row);
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
        model.setCurrent(new Categoria());
    }
}
