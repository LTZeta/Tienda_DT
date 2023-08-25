package entidades;

import interfaces.Comestibles;
import interfaces.ConDescuento;

import java.util.Date;

public class ProductoBebida extends Producto implements Comestibles, ConDescuento {

    private boolean esAlcoholica;
    private byte graduacionAlcoholica;
    private boolean esImportado;
    private Date fechaVencimiento;
    private Integer calorias;
    private Float porcentajeDescuento;

    public ProductoBebida(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, boolean esAlcoholica, boolean esImportado, Date fechaVencimiento, Integer calorias, String categoria) throws Exception{
        super(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible, categoria);
        this.esAlcoholica = esAlcoholica;
        this.esImportado = esImportado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
    }

    public void AsignarGraduacionAlcoholica(byte graduacionAlcoholica){
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    @Override
    public void setFechaVencimiento(Date fechaVencimiento) {

    }

    public boolean getEsAlcoholica() {
        return esAlcoholica;
    }

    public void setEsAlcoholica(boolean esAlcoholica) {
        this.esAlcoholica = esAlcoholica;
    }

    public byte getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public void setGraduacionAlcoholica(byte graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    public boolean getEsImportado() {
        return esImportado;
    }

    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
    }

    @Override
    public Date getFechaVencimiento() {
        return null;
    }

    @Override
    public void setCalorias(Integer calorias) {

    }

    @Override
    public Integer getCalorias() {
        return null;
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
        return "ProductoBebida{" +
                "esAlcoholica=" + esAlcoholica +
                ", graduacionAlcoholica=" + graduacionAlcoholica +
                ", esImportado=" + esImportado +
                ", fechaVencimiento=" + fechaVencimiento +
                ", calorias=" + calorias +
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