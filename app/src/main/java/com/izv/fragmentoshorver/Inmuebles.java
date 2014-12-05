package com.izv.fragmentoshorver;

import java.io.Serializable;


public class Inmuebles implements Comparable<Inmuebles>, Serializable {

    private String id, localidad, calle, tipo, numero;
    private double precio;

    public Inmuebles() {
        id="";
        localidad="";
        calle="";
        tipo="";
        numero="";
        precio=0;
    }

    public Inmuebles( String id, String localidad, String calle, String tipo, String numero, double precio) {
        this.id = id;
        this.localidad = localidad;
        this.calle = calle;
        this.tipo = tipo;
        this.numero = numero;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
       return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inmuebles inmuebles = (Inmuebles) o;

        if (calle != null ? !calle.equals(inmuebles.calle) : inmuebles.calle != null) return false;
        if (id != null ? !id.equals(inmuebles.id) : inmuebles.id != null) return false;
        if (localidad != null ? !localidad.equals(inmuebles.localidad) : inmuebles.localidad != null)
            return false;
        if (numero != null ? !numero.equals(inmuebles.numero) : inmuebles.numero != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (localidad != null ? localidad.hashCode() : 0);
        result = 31 * result + (calle != null ? calle.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }

    public int compareTo(Inmuebles inmueble) {
        String a = getLocalidad().toLowerCase();
        String b = inmueble.getLocalidad().toLowerCase();
        return a.compareTo(b);
    }

}
