package entidades;

public abstract class Producto {

    /*
    En este contexto, siendo que la clase Producto es una clase Abstracta, por defecto,
    todos los atributos de la clase llevarian el modificador de acceso protected, en caso de que no se le defina manualmente.
    Pero, en mi caso prefiero definirlos manualmente, ya que lo considero una buena práctica.
    */
    protected String id;
    protected String descripcion;
    protected Integer cantEnStock;
    protected Float precioVentaAlPublico;
    protected Float costoProducto;
    protected boolean estaDisponible;

    //Constructor
    protected Producto(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible) throws Exception{
        this.id = id;
        this.descripcion = descripcion;
        this.cantEnStock = cantEnStock;
        this.precioVentaAlPublico = precioVentaAlPublico;
        this.costoProducto = costoProducto;
        this.estaDisponible = estaDisponible;
    }

    //Getters y setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantEnStock(Integer cantEnStock) {
        this.cantEnStock = cantEnStock;
    }

    public void setPrecioVentaAlPublico(Float precioVentaAlPublico) {
        this.precioVentaAlPublico = precioVentaAlPublico;
    }

    public void setCostoProducto(Float costoProducto) {
        this.costoProducto = costoProducto;
    }

    public void setEstaDisponible(boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantEnStock() {
        return cantEnStock;
    }

    public Float getPrecioVentaAlPublico() {
        return precioVentaAlPublico;
    }

    public Float getCostoProducto() {
        return costoProducto;
    }

    public boolean getEstaDisponible() {
        return estaDisponible;
    }

}