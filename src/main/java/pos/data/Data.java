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

    public Data() {
        this.clientes = new ArrayList<>();
        this.cajeros = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Cajero> getCajeros() {
        return cajeros;
    }
}
