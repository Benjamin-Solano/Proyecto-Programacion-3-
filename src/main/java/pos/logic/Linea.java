package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Linea {
    @XmlID
    String numero;
    @XmlIDREF
    Producto producto;
    @XmlIDREF
    Factura factura;
    int cantidad;
    float descuento;

    public Linea() {
        this.cantidad = 0;
        this.descuento = 0;
        this.producto = new Producto();
        this.factura = new Factura();
        this.numero = "";
    }
    public Linea(String numero,Producto producto, Factura factura, int cantidad, float descuento) {
        this.numero = numero;
        this.producto = producto;
        this.factura = factura;
        this.cantidad = cantidad;
        this.descuento = descuento;
    }
    public String getNumero() {return numero;}
    public void setNumero(String numero) {this.numero = numero;}

    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}

    public Factura getFactura() {return factura;}
    public void setFactura(Factura factura) {this.factura = factura;}

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public float getDescuento() {return descuento;}
    public void setDescuento(float descuento) {this.descuento = descuento;}

    public String toString(){
        return numero;
    }

}
