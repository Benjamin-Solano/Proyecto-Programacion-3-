package pos.data;

import pos.logic.*;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "clientes")
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;

    @XmlElementWrapper(name = "cajeros")
    @XmlElement(name = "cajero")
    private List<Cajero> cajeros;

    @XmlElementWrapper(name = "productos")
    @XmlElement(name = "producto")
    private List<Producto> productos;

    @XmlElementWrapper(name = "categorias")
    @XmlElement(name = "categoria")
    private List<Categoria> categorias;

    @XmlElementWrapper(name = "Lineas")
    @XmlElement(name = "Linea")
    private List<Linea> Lineas;

    public Data() {
        this.clientes = new ArrayList<>();
        this.cajeros = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.Lineas = new ArrayList<>();
    }



    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Cajero> getCajeros() {
        return cajeros;
    }

    public List<Producto> getProductos() { return productos; }

    public List<Categoria> getCategorias(){return categorias;}

    public List<Linea> getLineas(){return Lineas;}
}
