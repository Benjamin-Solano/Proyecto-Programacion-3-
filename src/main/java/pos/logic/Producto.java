package pos.logic;

import jakarta.xml.bind.annotation.*;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Producto {
    @XmlID
    String codigo;
    String descripcion;
    String unidadDeMedida;
    float precioUnitario;
    int existencias;
    @XmlIDREF
    Categoria categoria;

    public Producto() {
        this("", "", "", 0, 0,null);
    } //null para categoria

    public Producto(String codigo, String descripcion, String unidadDeMedida, float precioUnitario, int existencias,Categoria categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadDeMedida = unidadDeMedida;
        this.precioUnitario = precioUnitario;
        this.existencias = existencias;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public float getExistencias() {
        return existencias;
    }

    public Categoria getCategoria() { return categoria; }

    public void setUnidadDeMedida(String unidadDeMedida) { this.unidadDeMedida = unidadDeMedida;}

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

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
