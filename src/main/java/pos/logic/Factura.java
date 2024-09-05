package pos.logic;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Factura {
    @XmlID
    String numero;
    @XmlIDREF
    Cliente cliente;
    @XmlIDREF
    Cajero cajero;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    LocalDate fecha;
    @XmlIDREF
    @XmlElementWrapper(name = "lineas")
    @XmlElement(name = "linea")
    List<Linea> lineas; //productos

    public Factura() {
        this.numero = "";
        this.cliente = null;
        this.cajero = null;
        this.fecha = LocalDate.now();
        this.lineas = new ArrayList<>();
    }
    public Factura(String numero, Cliente cliente, Cajero cajero, LocalDate fecha) {
        this.numero = numero;
        this.cliente = cliente;
        this.cajero = cajero;
        this.fecha = fecha;
        this.lineas = new ArrayList<>();
    }

    public Cajero getCajero() {
        return cajero;
    }
    public void setCajero(Cajero cajero) {this.cajero = cajero;}

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public String getNumero() {return numero;}
    public void setNumero(String numero) {this.numero = numero;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public List<Linea> getLineas() {return lineas;}
    public void setLineas(List<Linea> lineas) {this.lineas = lineas;}

}
