package entidades;

import java.util.*;

public class Tienda {
    private String nombre;
    private final Integer MAX_PRODUCTOS_EN_STOCK=100;
    private Float saldoEnCaja;
    private Map<String, ArrayList<Producto>> productosEnStock;
    private final String KEY_ENVASADOS = "envasados";
    private final String KEY_BEBIDAS = "bebidas";
    private final String KEY_LIMPIEZA = "limpieza";
    private final String PREFIJO_ID_ENVASADOS = "AB";
    private final String PREFIJO_ID_BEBIDAS = "AC";
    private final String PREFIJO_ID_LIMPIEZA = "AZ";

    public Tienda(String nombre, Float saldoEnCaja) {
        this.nombre = nombre;
        this.saldoEnCaja = saldoEnCaja;
        this.productosEnStock = new HashMap<>();
        productosEnStock.put(KEY_ENVASADOS, new ArrayList<>());
        productosEnStock.put(KEY_BEBIDAS, new ArrayList<>());
        productosEnStock.put(KEY_LIMPIEZA, new ArrayList<>());
    }

    public void agregarProducto(String tipo, Producto producto, int cantidad) throws Exception {
        if (productosEnStock.containsKey(tipo) && productosEnStock.get(tipo).size() + 1 <= MAX_PRODUCTOS_EN_STOCK) {
            productosEnStock.get(tipo).add(producto);
            Float costoTotal = producto.getCostoProducto() * cantidad;
            if (saldoEnCaja >= costoTotal) {
                saldoEnCaja -= costoTotal;
                System.out.println("Producto agregado exitosamente.");
            } else {
                productosEnStock.get(tipo).remove(producto); // Eliminamos el producto agregado si no se pudo comprar
                throw new Exception(("El producto no puede ser agregado a la tienda por saldo insuficiente en la caja."));
            }
        } else {
            throw new Exception("No se pueden agregar más productos de este tipo o se ha alcanzado el límite de stock.");
        }
    }


    public ProductoEnvasado crearEnvasado(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, String tipoEnvase, boolean esImportado, Date fechaVencimiento, Integer calorias) throws Exception {
        ProductoEnvasado nuevoProducto = new ProductoEnvasado(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible, tipoEnvase, esImportado, fechaVencimiento, calorias);
        ArrayList<Producto> productos = productosEnStock.get(KEY_ENVASADOS);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(id)){
                throw new Exception("El producto que desea crear ya existe" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }
        productos.add(nuevoProducto);
        agregarProducto(KEY_ENVASADOS, nuevoProducto, cantEnStock);
        System.out.println("El producto fue creado exitosamente");
        return nuevoProducto;
    }

    public ProductoBebida crearBebida(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, boolean esAlcoholica, boolean esImportado, Date fechaVencimiento, Integer calorias) throws Exception {
        ProductoBebida nuevoProducto = new ProductoBebida(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible, esAlcoholica, esImportado, fechaVencimiento, calorias);
        ArrayList<Producto> productos = productosEnStock.get(KEY_BEBIDAS);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(id)){
                throw new Exception("El producto que desea crear ya existe" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }
        productos.add(nuevoProducto);
        agregarProducto(KEY_BEBIDAS, nuevoProducto, cantEnStock);
        System.out.println("El producto fue creado exitosamente");
        return nuevoProducto;
    }

    public ProductoDeLimpieza crearDeLimpieza(String id, String descripcion, Integer cantEnStock, Float precioVentaAlPublico, Float costoProducto, boolean estaDisponible, String tipoDeAplicacion) throws Exception {
        ProductoDeLimpieza nuevoProducto = new ProductoDeLimpieza(id, descripcion, cantEnStock, precioVentaAlPublico, costoProducto, estaDisponible, tipoDeAplicacion);
        ArrayList<Producto> productos = productosEnStock.get(KEY_LIMPIEZA);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(id)){
                throw new Exception("El producto que desea crear ya existe" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }
        productos.add(nuevoProducto);
        agregarProducto(KEY_LIMPIEZA, nuevoProducto, cantEnStock);
        System.out.println("El producto fue creado exitosamente");
        return nuevoProducto;
    }

    public void deshabilitarProducto(String tipo, String codigoProducto) throws Exception {
        ArrayList<Producto> productosEnLista = productosEnStock.get(tipo);
        for (int i=0; i<productosEnLista.size(); i++){
            if (productosEnLista.get(i).getId().equalsIgnoreCase(codigoProducto)){
                productosEnLista.get(i).setEstaDisponible(false);
                productosEnStock.replace(tipo, productosEnLista);
            }
        }

    }

    public void venderProductos(List<Producto> productos) {
        // Implementación
        Float totalVenta = 0.0f;
        boolean hayStockInsuficiente = false;
        boolean hayProductosNoDisponibles = false;

        for (Producto producto : productos) {
            if ((producto.getCantEnStock() <= 0) || !producto.getEstaDisponible()) {
                hayProductosNoDisponibles = true;
                continue;
            }

            Integer cantidadVendida = Math.min(producto.getCantEnStock(), 10);
            Float precioUnitario = producto.getPrecioVentaAlPublico();

            // Aplicar impuesto si es un producto importado
            if (producto instanceof ProductoEnvasado){
                if (((ProductoEnvasado) producto).getEsImportado()){
                    precioUnitario *= 1.10f; // Aplicar impuesto del 10%
                }
            } else if (producto instanceof ProductoBebida) {
                if (((ProductoBebida) producto).getEsImportado()){
                    precioUnitario *= 1.10f; // Aplicar impuesto del 10%
                }
            }

            // Aplicar descuento si aplica
/*            Float descuento = producto.getPorcentajeDescuento();
            if (descuento > 0 && descuento <= producto.getMaxPorcentajeDescuento()) {
                Float precioDescuento = precioUnitario * (1 - descuento / 100);
                if (precioDescuento >= producto.getPrecioCompra()) {
                    precioUnitario = precioDescuento;
                } else {
                    System.out.println("El descuento registrado para el producto " + producto.getIdentificador() + " no pudo ser aplicado.");
                }
            }*/

            totalVenta += cantidadVendida * precioUnitario;
            producto.setCantEnStock(producto.getCantEnStock() - cantidadVendida);
        }

        // Imprimir mensajes adicionales
/*        if (hayStockInsuficiente) {
            System.out.println("Hay productos con stock disponible menor al solicitado.");
        }*/
        if (hayProductosNoDisponibles) {
            System.out.println("Algunos productos no están disponibles para la venta.");
        }

        System.out.println("TOTAL VENTA: " + totalVenta);
    }


    @Override
    public String toString() {
        return "Tienda{" +
                "nombre='" + nombre + '\'' +
                ", MAX_PRODUCTOS_EN_STOCK=" + MAX_PRODUCTOS_EN_STOCK +
                ", saldoEnCaja=" + saldoEnCaja +
                ", productosEnStock=" + productosEnStock +
                '}';
    }

    public String getKEY_ENVASADOS() {
        return KEY_ENVASADOS;
    }

    public String getKEY_BEBIDAS() {
        return KEY_BEBIDAS;
    }

    public String getKEY_LIMPIEZA() {
        return KEY_LIMPIEZA;
    }

    public String getPREFIJO_ID_ENVASADOS() {
        return PREFIJO_ID_ENVASADOS;
    }

    public String getPREFIJO_ID_BEBIDAS() {
        return PREFIJO_ID_BEBIDAS;
    }

    public String getPREFIJO_ID_LIMPIEZA() {
        return PREFIJO_ID_LIMPIEZA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMAX_PRODUCTOS_EN_STOCK() {
        return MAX_PRODUCTOS_EN_STOCK;
    }

    public Float getSaldoEnCaja() {
        return saldoEnCaja;
    }

    public void setSaldoEnCaja(Float saldoEnCaja) {
        this.saldoEnCaja = saldoEnCaja;
    }

    public Map<String, ArrayList<Producto>> getProductosEnStock() {
        return productosEnStock;
    }

}