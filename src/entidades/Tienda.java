package entidades;

import interfaces.Comestibles;
import interfaces.ConDescuento;

import java.util.*;

public class Tienda {
    private String nombre;
    private final Integer MAX_PRODUCTOS_EN_STOCK=1000000;
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

    public void agregarProductoAStock(String tipo, Producto producto, int cantidad) throws Exception {
        if (productosEnStock.containsKey(tipo) && productosEnStock.get(tipo).size() + producto.getCantEnStock() <= MAX_PRODUCTOS_EN_STOCK) {
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


    public void crearEnvasado(ProductoEnvasado nuevoProducto) throws Exception {
        ArrayList<Producto> productos = productosEnStock.get(KEY_ENVASADOS);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(nuevoProducto.getId())){
                throw new Exception("El producto que desea crear ya existe\n" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }

        if (nuevoProducto.getCostoProducto() * nuevoProducto.getCantEnStock() > saldoEnCaja){
            throw new Exception("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja.");
        }
        try {
            agregarProductoAStock(KEY_ENVASADOS, nuevoProducto, nuevoProducto.getCantEnStock());
            System.out.println("El producto fue creado exitosamente");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void crearBebida(ProductoBebida nuevoProducto) throws Exception {
        ArrayList<Producto> productos = productosEnStock.get(KEY_BEBIDAS);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(nuevoProducto.getId())){
                throw new Exception("El producto que desea crear ya existe\n" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }
        try {
            agregarProductoAStock(KEY_BEBIDAS, nuevoProducto, nuevoProducto.getCantEnStock());
            System.out.println("El producto fue creado exitosamente");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void crearDeLimpieza(ProductoDeLimpieza nuevoProducto) throws Exception {
        ArrayList<Producto> productos = productosEnStock.get(KEY_LIMPIEZA);
        for (Producto producto: productos) {
            if (producto.getId().equalsIgnoreCase(nuevoProducto.getId())){
                throw new Exception("El producto que desea crear ya existe\n" + "El producto es: '"+producto.getDescripcion()+"'");
            }
        }
        try {
            agregarProductoAStock(KEY_LIMPIEZA, nuevoProducto, nuevoProducto.getCantEnStock());
            System.out.println("El producto fue creado exitosamente");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deshabilitarProducto(String tipo, String codigoProducto) {
        ArrayList<Producto> productosEnLista = productosEnStock.get(tipo);
        for (int i=0; i<productosEnLista.size(); i++){
            if (productosEnLista.get(i).getId().equalsIgnoreCase(codigoProducto)){
                productosEnLista.get(i).setEstaDisponible(false);
                productosEnStock.replace(tipo, productosEnLista);
            }
        }
    }

    public Producto buscarProductoPorId(String id) {
        // Buscar el producto en las 3 listas, ya que no se conoce previamente la categoria
        for (List<Producto> listaProductos : productosEnStock.values()) {
            for (Producto producto : listaProductos) {
                if (producto.getId().equals(id)) {
                    return producto;
                }
            }
        }
        return null; // Si no se encuentra el producto
    }
    public void mostrarProductos(){
        for (Map.Entry<String, ArrayList<Producto>> entry : productosEnStock.entrySet()) {
            String categoria = entry.getKey();
            List<Producto> productos = entry.getValue();

            System.out.println("\nCategoría: " + categoria);

            for (Producto producto : productos) {
                System.out.println("\nIdentificador: " + producto.getId());
                System.out.println("Descripción: " + producto.getDescripcion());
                System.out.println("Cantidad en Stock: " + producto.getCantEnStock());
            }
        }
    }



    public void venderProductos(List<ItemCompra> carrito) throws Exception {
        float totalVenta = 0.0f; // Variable para almacenar el total de la venta
        boolean hayStockInsuficiente = false;


        for (ItemCompra item : carrito) {
            Producto producto = item.getProducto();

            if (!producto.getEstaDisponible()) {
                System.out.println("El producto " + producto.getId() + " " + producto.getDescripcion() + " no se encuentra disponible.");
                continue; // Saltar al siguiente producto en el carrito para evitar que se corte la ejecución de la venta solo por un producto con incidencia.
            }

            int cantidadVendida = Math.min(item.getCantidad(), producto.getCantEnStock());

            if (cantidadVendida <= 0) {
                System.out.println("Producto " + producto.getId() + " " + producto.getDescripcion() + " no disponible en stock.");
                continue;
            }

            // Restricción de hasta 10 unidades por producto
            if (cantidadVendida > 10) {
                System.out.println("No se pueden vender más de 10 unidades de " + producto.getId() + " " + producto.getDescripcion());
                continue;
            }

            // Verificar stock disponible
            if (cantidadVendida > producto.getCantEnStock() && producto.getCantEnStock() > 0) {
                System.out.println("No hay suficiente stock para vender " + cantidadVendida + " unidades de " + producto.getId());
                cantidadVendida = producto.getCantEnStock(); // Vender solo lo que hay en stock
                hayStockInsuficiente = true;
            }

            // Calcular el precio total del producto
            Float precioVentaUnidad = producto.getPrecioVentaAlPublico();
            Float precioTotalProducto = precioVentaUnidad * cantidadVendida;

            if (producto instanceof Comestibles){
                float porcentajeGanancia = ((precioTotalProducto - (producto.getPrecioVentaAlPublico() * cantidadVendida)) / (producto.getPrecioVentaAlPublico() * cantidadVendida)) * 100;
                float porcentajeGananciaMaximo = 20.0f;
                if (porcentajeGanancia > porcentajeGananciaMaximo) {
                    System.out.println("El porcentaje de ganancia para el producto " + producto.getId() + " " + producto.getDescripcion() + " excede el máximo permitido.");
                    continue;
                }
            }

            if (producto instanceof ProductoDeLimpieza){
                float porcentajeGanancia = ((precioTotalProducto - (producto.getPrecioVentaAlPublico() * cantidadVendida)) / (producto.getPrecioVentaAlPublico() * cantidadVendida)) * 100;
                float porcentajeGananciaMaximo = 25.0f;
                float porcentajeGananciaMinimo = 10.0f;
                float precioConDescuento = ((ProductoDeLimpieza) producto).getPrecioConDescuento();
                float porcentajeMaxDeDescuento = 25.0f;
                float porcentajeDeDescuento = ((ProductoDeLimpieza) producto).getPorcentajeDescuento();

                if (((ProductoDeLimpieza) producto).getTipoDeAplicacion().equalsIgnoreCase("ropa") || ((ProductoDeLimpieza) producto).getTipoDeAplicacion().equalsIgnoreCase("multiuso")){
                    if (porcentajeGanancia > porcentajeGananciaMaximo){
                        System.out.println("El porcentaje de ganancia para el producto " + producto.getId() + " " + producto.getDescripcion() + " excede el máximo permitido.");
                        continue;
                    }
                } else if (porcentajeGanancia > porcentajeGananciaMaximo || porcentajeGanancia < porcentajeGananciaMinimo) {
                    System.out.println("El porcentaje de ganancia para el producto " + producto.getId() + " " + producto.getDescripcion() + " no cumple con el máximo o minimo permitido.");
                    continue;
                }
                if (precioConDescuento < producto.getCostoProducto() || porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }
            }

            if (producto instanceof ProductoEnvasado){
                float precioConDescuento = ((ProductoEnvasado) producto).getPrecioConDescuento();
                float porcentajeMaxDeDescuento = 20.0f;
                float porcentajeDeDescuento = ((ProductoEnvasado) producto).getPorcentajeDescuento();

                if (precioConDescuento < producto.getCostoProducto() || porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }

                if (((ProductoEnvasado) producto).getEsImportado()){
                    precioVentaUnidad = (producto.getPrecioVentaAlPublico() * 1.10f);
                    precioTotalProducto = precioVentaUnidad * cantidadVendida;
                }
            }

            if (producto instanceof ProductoBebida){
                float precioConDescuento = ((ProductoBebida) producto).getPrecioConDescuento();
                float porcentajeMaxDeDescuento = 20.0f;
                float porcentajeDeDescuento = ((ProductoBebida) producto).getPorcentajeDescuento();

                if (precioConDescuento < producto.getCostoProducto() || porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }

                if (((ProductoBebida) producto).getEsImportado()){
                    precioVentaUnidad = (producto.getPrecioVentaAlPublico() * 1.10f);
                    precioTotalProducto = precioVentaUnidad * cantidadVendida;
                }

            }

            // Actualizar el total de la venta
            totalVenta += precioTotalProducto;

            // Imprimir el detalle de la venta para el producto actual
            System.out.println(producto.getId() + " " + producto.getDescripcion() + " " +
                    cantidadVendida + " x " + precioVentaUnidad);
        }

        if (hayStockInsuficiente) {
            System.out.println("Hay productos con stock disponible menor al solicitado.");
        }

        System.out.println("TOTAL VENTA: " + totalVenta);

    }

    public List<Producto> generarListaDeProductos(List<ItemCompra> carrito) throws Exception {
        if (carrito.isEmpty()){
            List<Producto> productos = new ArrayList<>();
            for (ItemCompra item: carrito) {
                productos.add(item.getProducto());
            }
            return productos;
        }else throw new Exception("El carrito esta vacio, no se modifico el stock ni se realizo ninguna venta");
    }

    public void definirDescuento(String categoria, Producto producto, Float nuevoPorcentajeDescuento) throws Exception {

        List<Producto> listaProductos = productosEnStock.get(categoria);
        if (listaProductos != null && listaProductos.contains(producto)) {
            if (categoria.equalsIgnoreCase(KEY_BEBIDAS)){
                ((ProductoBebida)producto).setPorcentajeDescuento(nuevoPorcentajeDescuento);
                System.out.println("Descuento actualizado para el producto " + producto.getId() + " " + producto.getDescripcion());
            }
            if (categoria.equalsIgnoreCase(KEY_ENVASADOS)){
                ((ProductoEnvasado)producto).setPorcentajeDescuento(nuevoPorcentajeDescuento);
                System.out.println("Descuento actualizado para el producto " + producto.getId() + " " + producto.getDescripcion());
            }
            if (categoria.equalsIgnoreCase(KEY_LIMPIEZA)){
                ((ProductoDeLimpieza)producto).setPorcentajeDescuento(nuevoPorcentajeDescuento);
                System.out.println("Descuento actualizado para el producto " + producto.getId() + " " + producto.getDescripcion());
            }else {
                System.out.println("El producto no se encuentra en stock.");
            }
        }else throw new Exception("El producto no se encuentra en stock, o aún no hay ningún producto cargado.");

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