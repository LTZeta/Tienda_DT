package entidades;

import interfaces.Comestibles;
import interfaces.ConDescuento;

import java.util.Date;

public class ProductoEnvasado extends Producto implements Comestibles, ConDescuento {

    private String tipoEnvase;
    private boolean esImportado;
    private Date fechaVencimiento;
    private Integer calorias;
    private Float porcentajeDescuento;

    public ProductoEnvasado(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, String tipoEnvase, boolean esImportado, Date fechaVencimiento, Integer calorias) throws Exception{
        super(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible);
        this.tipoEnvase = tipoEnvase;
        this.esImportado = esImportado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
    }

    // Implementación de métodos de la interfaz Comestible
    @Override
    public void setFechaVencimiento(Date fecha) {
        this.fechaVencimiento = fecha;
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
        return getPrecioVentaAlPublico() * (1 - porcentajeDescuento / 100);
    }

    public String getTipoEnvase() {
        return tipoEnvase;
    }

    public void setTipoEnvase(String tipoEnvase) {
        this.tipoEnvase = tipoEnvase;
    }

    public boolean getEsImportado() {
        return esImportado;
    }

    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
    }

    @Override
    public String toString() {
        return "ProductoEnvasados{" +
                "tipoEnvase='" + tipoEnvase + '\'' +
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