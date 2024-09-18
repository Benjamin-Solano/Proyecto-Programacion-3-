package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Linea {
    @XmlID
    String numero;
    @XmlIDREF
    Producto producto;
    @XmlElement
    int cantidad;
    float descuento;

    public Linea() {
        this.cantidad = 0;
        this.descuento = 0;
        this.producto = new Producto();
        this.numero = "";
    }
    public Linea(String numero,Producto producto, int cantidad, float descuento) {
        this.numero = numero;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descuento = descuento;
    }

    // Getters y setters
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return producto.equals(linea.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto);
    }

    public String getNumero() {return numero;}
    public void setNumero(String numero) {this.numero = numero;}

    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public float getDescuento() {return descuento;}
    public void setDescuento(float descuento) {this.descuento = descuento;}

    public String toString(){
        return numero;
    }

}
