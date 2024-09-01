package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Producto {
    @XmlID
    String codigo;
    String descripcion;
    String unidadDeMedida;
    float precioUnitario;
    int existencias;

    public Producto() {
        this("", "", "", 0, 0);
    }

    public Producto(String codigo, String descripcion, String unidadDeMedida, float precioUnitario, int existencias) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadDeMedida = unidadDeMedida;
        this.precioUnitario = precioUnitario;
        this.existencias = existencias;
    }

    String getCodigo() {
        return codigo;
    }

    String getDescripcion() {
        return descripcion;
    }

    String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    float getPrecioUnitario() {
        return precioUnitario;
    }

    float getExistencias() {
        return existencias;
    }

    void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(codigo, producto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
