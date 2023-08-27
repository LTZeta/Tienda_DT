package entidades;

import interfaces.ConDescuento;

import java.text.SimpleDateFormat;

public class ProductoDeLimpieza extends Producto implements ConDescuento {

    private String tipoDeAplicacion;
    private Float porcentajeDescuento;

    public ProductoDeLimpieza(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, String tipoDeAplicacion, String categoria) throws Exception{
        super(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible, categoria);
        this.tipoDeAplicacion = tipoDeAplicacion;
        this.porcentajeDescuento = 0.0F;
    }


    public String getTipoDeAplicacion() {
        return tipoDeAplicacion;
    }

    public void setTipoDeAplicacion(String tipoDeAplicacion) {
        this.tipoDeAplicacion = tipoDeAplicacion;
    }

    @Override
    public void setPorcentajeDescuento(Float porcentajeDescuento) {this.porcentajeDescuento = porcentajeDescuento;}

    @Override
    public Float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public Float getPrecioConDescuento() {
        return getPrecioVentaAlPublico() * (1 - porcentajeDescuento / 100);
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