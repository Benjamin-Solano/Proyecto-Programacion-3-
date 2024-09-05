package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD) //no tiene lista de productos, productos tiene atributo categoria*
public class Categoria {
    @XmlID
    private String codigo;
    private String nombre;
    public Categoria() {
        this.codigo = " ";
        this.nombre = " ";
    }

    public Categoria(String id,String nombre) {
        this.codigo = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCatId() {
        return this.codigo;
    }

    public void setCatId(String id) {
        this.codigo = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(codigo, categoria.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return this.codigo+"-"+this.nombre;
    }

}
