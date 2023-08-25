package entidades;

import interfaces.ConDescuento;

public class ProductoDeLimpieza extends Producto implements ConDescuento {

    private String tipoDeAplicacion;
    private Float porcentajeDescuento;

    public ProductoDeLimpieza(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, String tipoDeAplicacion) throws Exception{
        super(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible);
        this.tipoDeAplicacion = tipoDeAplicacion;
    }


    public String getTipoDeAplicacion() {
        return tipoDeAplicacion;
    }

    public void setTipoDeAplicacion(String tipoDeAplicacion) {
        this.tipoDeAplicacion = tipoDeAplicacion;
    }

    @Override
    public void setPorcentajeDescuento(Float porcentajeDescuento) {

    }

    @Override
    public Float getPorcentajeDescuento() {
        return null;
    }

    @Override
    public Float getPrecioConDescuento() {
        return null;
    }


    @Override
    public String toString() {
        return "ProductoDeLimpieza{" +
                "tipoDeAplicacion='" + tipoDeAplicacion + '\'' +
                ", porcentajeDescuento=" + porcentajeDescuento +
                ", id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantEnStock=" + cantEnStock +
                ", precioVentaAlPublico=" + precioVentaAlPublico +
                ", costoProducto=" + costoProducto +
                ", estaDisponible=" + estaDisponible +
                '}';
    }
}