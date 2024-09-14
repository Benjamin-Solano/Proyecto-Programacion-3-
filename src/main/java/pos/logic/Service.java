package pos.logic;

import pos.data.Data;
import pos.data.XmlPersister;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service { //esto es un singleton
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//================= CLIENTES ============

    public void create(Cliente e) throws Exception {
        Cliente result = data.getClientes().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result == null) data.getClientes().add(e);
        else throw new Exception("Cliente ya existe");
    }

    public Cliente read(Cliente e) throws Exception {
        Cliente result = data.getClientes().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Cliente e) throws Exception {
        Cliente result;
        try {
            result = this.read(e);
            data.getClientes().remove(result);
            data.getClientes().add(e);
        } catch (Exception ex) {
            throw new Exception("Cliente no existe");
        }
    }

    public void delete(Cliente e) throws Exception {
        data.getClientes().remove(e);
    }

    public List<Cliente> search(Cliente e) {
        return data.getClientes().stream()
                .filter(i -> i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }

    public List <Cliente> getClientes(){
        return data.getClientes();
    }
    //================= Cajeros ============
    public void create(Cajero e) throws Exception {

        Cajero result = data.getCajeros().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result == null) data.getCajeros().add(e);
        else throw new Exception("Cajero ya existe");
    }

    public Cajero read(Cajero e) throws Exception {
        Cajero result = data.getCajeros().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Cajero no existe");
    }

    public void update(Cajero e) throws Exception {
        Cajero result;
        try {
            result = this.read(e);
            data.getCajeros().remove(result);
            data.getCajeros().add(e);
        } catch (Exception ex) {
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Cajero e) throws Exception {
        data.getCajeros().remove(e);
    }

    public List<Cajero> search(Cajero e) {
        return data.getCajeros().stream()
                .filter(i -> i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }
    public List<Cajero> getCajeros(){
        return data.getCajeros();
    }
    //================= Productos ============
    public void create(Producto e) throws Exception {

        Producto result = data.getProductos().stream().filter(i -> i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result == null) data.getProductos().add(e);
        else throw new Exception("Producto ya existe");
    }

    public Producto read(Producto e) throws Exception {
        Producto result = data.getProductos().stream().filter(i -> i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Producto no existe");
    }

    public void update(Producto e) throws Exception {
        Producto result;
        try {
            result = this.read(e);
            data.getProductos().remove(result);
            data.getProductos().add(e);
        } catch (Exception ex) {
            throw new Exception("Producto no existe");
        }
    }
    public void delete(Producto e) throws Exception {
        data.getProductos().remove(e);
    }
    public List<Producto> search(Producto e) {
        return data.getProductos().stream()
                .filter(i -> i.getCodigo().contains(e.getCodigo()))
                .sorted(Comparator.comparing(Producto::getCodigo))
                .collect(Collectors.toList());
    }
    public List<Producto> getProductos() {
        return data.getProductos();  // Retorna la lista completa de productos
    }
    //================= Categoriass ============

    public void create(Categoria e) throws Exception {

        Categoria result = data.getCategorias().stream().filter(i -> i.getCatId().equals(e.getCatId())).findFirst().orElse(null);
        if (result == null) data.getCategorias().add(e);
        else throw new Exception("Categoria ya existe");
    }

    public Categoria read(Categoria e) throws Exception {
        Categoria result = data.getCategorias().stream().filter(i -> i.getCatId().equals(e.getCatId())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Categoria no existe");
    }

    public void update(Categoria e) throws Exception {
        Categoria result;
        try {
            result = this.read(e);
            data.getCategorias().remove(result);
            data.getCategorias().add(e);
        } catch (Exception ex) {
            throw new Exception("Categoria no existe");
        }
    }

    public void delete(Categoria e) throws Exception {
        data.getCategorias().remove(e);
    }

    public List<Categoria> search(Categoria e) {
        return data.getCategorias().stream()
                .filter(i -> i.getCatId().contains(e.getCatId()))
                .sorted(Comparator.comparing(Categoria::getCatId))
                .collect(Collectors.toList());
    }
    public List<Categoria> getCategorias() {
        return data.getCategorias();  // Retorna la lista completa de productos
    }

    //================= Lineas ============

    public void create(Linea e) throws Exception {

        Linea result = data.getLineas().stream().filter(i -> i.getNumero().equals(e.getNumero())).findFirst().orElse(null);
        if (result == null) data.getLineas().add(e);
        else throw new Exception("Linea ya existe");
    }

    public Linea read(Linea e) throws Exception {
        Linea result = data.getLineas().stream().filter(i -> i.getNumero().equals(e.getNumero())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Linea no existe");
    }

    public void update(Linea e) throws Exception {
        Linea result;
        try {
            result = this.read(e);
            data.getLineas().remove(result);
            data.getLineas().add(e);
        } catch (Exception ex) {
            throw new Exception("Linea no existe");
        }
    }

    public void delete(Linea e) throws Exception {
        data.getLineas().remove(e);
    }

    public List<Linea> search(Linea e) {
        return data.getLineas().stream()
                .filter(i -> i.numero.contains(e.getNumero()))
                .sorted(Comparator.comparing(Linea::getNumero))
                .collect(Collectors.toList());
    }
    public List<Linea> getLineas() {
        return data.getLineas();  // Retorna la lista completa de Lineas
    }

    //================= Facturas ============

//Esto es para el el num
    public int contadorFacturas=1;



}

