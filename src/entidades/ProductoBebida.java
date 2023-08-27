package entidades;

import interfaces.Comestibles;
import interfaces.ConDescuento;

import java.text.SimpleDateFormat;
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
        this.porcentajeDescuento = 0.0F;
    }

    public void AsignarGraduacionAlcoholica(byte graduacionAlcoholica){
        this.graduacionAlcoholica = graduacionAlcoholica;
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

    @Override
    public void setFechaVencimiento(Date fechaVencimiento) {this.fechaVencimiento = fechaVencimiento;}
    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
    }


    @Override
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    @Override
    public Integer getCalorias() {
        return calorias;
    }

    // Implementación de métodos de la interfaz ConDescuento
    @Override
    public void setPorcentajeDescuento(Float porcentaje) {
        this.porcentajeDescuento = porcentaje;
    }

    @Override
    public Float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public Float getPrecioConDescuento() {
        if (esImportado){
            return  (getPrecioVentaAlPublico() * 1.10f) * (1 - porcentajeDescuento / 100);
        }
        return getPrecioVentaAlPublico() * (1 - porcentajeDescuento / 100);
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