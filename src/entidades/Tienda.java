package entidades;

import interfaces.Comestibles;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public void agregarProductoNuevoAStock(String tipo, Producto producto, int cantidad) throws Exception {
        if (productosEnStock.containsKey(tipo) && productosEnStock.get(tipo).size() + producto.getCantEnStock() <= MAX_PRODUCTOS_EN_STOCK) {
            productosEnStock.get(tipo).add(producto);
            Float costoTotal = producto.getCostoProducto() * cantidad;
            if (saldoEnCaja >= costoTotal) {
                System.out.println("Producto agregado exitosamente.");
                System.out.println("Saldo en caja anterior: " + saldoEnCaja);
                saldoEnCaja -= costoTotal;
                System.out.println("Nuevo saldo en caja: " + saldoEnCaja);

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
            System.out.println("Saldo en caja actual: " + saldoEnCaja);
            throw new Exception("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja.");
        }
        try {
            agregarProductoNuevoAStock(KEY_ENVASADOS, nuevoProducto, nuevoProducto.getCantEnStock());
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
            agregarProductoNuevoAStock(KEY_BEBIDAS, nuevoProducto, nuevoProducto.getCantEnStock());
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
            agregarProductoNuevoAStock(KEY_LIMPIEZA, nuevoProducto, nuevoProducto.getCantEnStock());
            System.out.println("El producto fue creado exitosamente");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deshabilitarProducto(String categoria, String codigoProducto) {
        // Buscar el producto en el Array correspondiente a su categoria
        ArrayList<Producto> productosEnLista = productosEnStock.get(categoria);
        for (int i=0; i<productosEnLista.size(); i++){ // For tradicional para conocer el índice al cual pertenece el producto y así poder deshabilitarlo
            if (productosEnLista.get(i).getId().equalsIgnoreCase(codigoProducto)){
                productosEnLista.get(i).setEstaDisponible(false);
                productosEnStock.replace(categoria, productosEnLista);
            }
        }
    }

    public void habilitarProducto(String categoria, String codigoProducto) {
        // Buscar el producto en el Array correspondiente a su categoria
        ArrayList<Producto> productosEnLista = productosEnStock.get(categoria);
        for (int i=0; i<productosEnLista.size(); i++){ // For tradicional para conocer el índice al cual pertenece el producto y así poder deshabilitarlo
            if (productosEnLista.get(i).getId().equalsIgnoreCase(codigoProducto)){
                productosEnLista.get(i).setEstaDisponible(true);
                productosEnStock.replace(categoria, productosEnLista);
            }
        }
    }

    public List<String> obtenerComestiblesConMenorDescuento(float porcentajeDescuentoAComparar) throws Exception {
        List<String> comestiblesConMenorDescuento = productosEnStock.values().stream() // Stream de todas las listas de productos
                .flatMap(List::stream) // Stream de todos los productos
                .filter(Comestibles.class::isInstance) // Filtrar solo productos comestibles
                .filter(producto -> producto instanceof ProductoEnvasado || producto instanceof ProductoBebida) // Filtrar solo productos envasados y bebidas
                .filter(producto -> {
                    if (producto instanceof ProductoEnvasado) {
                        return ((ProductoEnvasado) producto).getPorcentajeDescuento() < porcentajeDescuentoAComparar;
                    } else if (producto instanceof ProductoBebida) {
                        return ((ProductoBebida) producto).getPorcentajeDescuento() < porcentajeDescuentoAComparar;
                    }
                    return false;
                }) // Filtrar por descuento menor al parámetro
                .sorted(Comparator.comparingDouble(Producto::getPrecioVentaAlPublico)) // Ordenar por precio de venta ascendente
                .map(producto -> producto.getDescripcion().toUpperCase()) // Obtener descripciones en mayúsculas
                .collect(Collectors.toList()); // Recolectar en una lista
        if (comestiblesConMenorDescuento.isEmpty()){
            throw new Exception("No se encontró ningún producto con un descuento menor al %" + porcentajeDescuentoAComparar + "\n");
        }
        return comestiblesConMenorDescuento;
    }

    public void imprimirComestiblesConMenorDescuento(List<String> comestiblesConMenorDescuento) {
        String resultado = String.join(", ", comestiblesConMenorDescuento);
        System.out.println(resultado);
    }

    public void listarProductosConUtilidadesInferiores(float porcentajeUtilidad) throws Exception {
        List<String> productosConUtilidadesInferiores = productosEnStock.values().stream() // Stream de todas las listas de productos
                .flatMap(List::stream) // Stream de todos los productos
                .filter(producto -> calcularUtilidad(producto) < porcentajeUtilidad) // Filtrar por utilidad inferior al porcentaje
                .map(producto -> String.format("Código: %s, Descripción: %s, Cantidad en stock: %d",
                        producto.getId(), producto.getDescripcion(), producto.getCantEnStock())) // Formatear información del producto
                .collect(Collectors.toList()); // Recolectar en una lista
        if (productosConUtilidadesInferiores.isEmpty()){
            throw new Exception("No hay productos con utilidades inferiores al %" + porcentajeUtilidad + "\n");
        }
        productosConUtilidadesInferiores.forEach(System.out::println); // Imprimir cada producto
    }

    private float calcularUtilidad(Producto producto) {
        float costoTotalCompra = producto.getCostoProducto() * producto.getCantEnStock();
        float gananciaTotal = producto.getPrecioVentaAlPublico() * producto.getCantEnStock() - costoTotalCompra;
        return (gananciaTotal / costoTotalCompra) * 100;
    }

    public Producto buscarProductoPorId(String id) throws Exception {
        // Buscar el producto en las 3 listas, ya que no se conoce previamente la categoria
        for (List<Producto> listaProductos : productosEnStock.values()) {
            for (Producto producto : listaProductos) {
                if (producto.getId().equalsIgnoreCase(id)) {
                    return producto;
                }
            }
        }
        System.out.println("La id " + id + " no esta asociada a ningún producto existente"); // Si no se encuentra el producto
        return null;
    }

    public void actualizarStockDeProducto(String idProductoAActualizar, Integer nuevasUnidades) throws Exception {
        Producto producto = buscarProductoPorId(idProductoAActualizar);
        if (nuevasUnidades > 0){
            producto.setCantEnStock(producto.getCantEnStock() + nuevasUnidades);
            saldoEnCaja = saldoEnCaja - (producto.getCostoProducto() * nuevasUnidades);
            System.out.println("El producto " + producto.getId() + " " + producto.getDescripcion() + " Quedó con un total de " + producto.getCantEnStock() +" unidades en stock");
            System.out.println("Nuevo saldo en caja: " + saldoEnCaja);
        }else throw new Exception("Las unidades que ingresaron no pueden ser negativas");

    }
    public void mostrarProductos(){
        // Mostrar todos los productos separados por categoría
        for (Map.Entry<String, ArrayList<Producto>> entry : productosEnStock.entrySet()) {
            String categoria = entry.getKey();
            List<Producto> productos = entry.getValue();

            System.out.println("\nCategoría: " + categoria);

            // Imprimir por pantalla los datos de cada producto dentro de la lista
            for (Producto producto : productos) {
                System.out.println("\nIdentificador: " + producto.getId());
                System.out.println("Descripción: " + producto.getDescripcion());
                System.out.println("Cantidad en Stock: " + producto.getCantEnStock());
                if (!producto.getEstaDisponible()){
                    System.out.println("Habilitado: No");
                }else {
                    System.out.println("Habilitado: Sí");
                }
            }
        }
    }



    public void procesarVenta(List<ItemCompra> carrito) throws Exception {
        float totalVenta = 0.0f; // Variable para almacenar el total de la venta
        boolean hayStockInsuficiente = false; // Variable para determinar si imprimir o no un mensaje informando de una venta de un producto con stock insuficiente.
        float precioTotalProducto; // Variable para ir calculando el total del producto, se va a usar para aplicar descuentos y para calcular el % de ganancia.
        float costoTotalCompra; // Variable que almacena el total de lo que costaría comprar este mismo producto al proveedor (Costo de compra), se utiliza para comparar en % si se respeta lo pautado para cada producto.

        // Iteramos por cada item dentro del carrito
        for (ItemCompra item : carrito) {

            // Almacenamos el producto para tener un acceso más rápido a sus métodos getter
            Producto producto = item.getProducto();

            // Si el producto no está disponible se obvia su venta y se informa al usuario
            if (!producto.getEstaDisponible()) {
                System.out.println("El producto " + producto.getId() + " " + producto.getDescripcion() + " no se encuentra disponible.");
                continue; // Saltar al siguiente producto en el carrito para evitar que se corte la ejecución de la venta solo por un producto con incidencia.
            }

            // Funcion de la Clase Math, que devuelve el menor entre la cantidad que se pretende comprar y la cantidad real en stock del producto
            // De esta manera siempre se va a vender el 'máximo disponible'
            int cantidadVendida = Math.min(item.getCantidad(), producto.getCantEnStock());

            // Verificar si la cantidad a vender es igual o menor a 0 y así determinar si seguir o no con el programa
            if (cantidadVendida <= 0) {
                System.out.println("Producto " + producto.getId() + " " + producto.getDescripcion() + " no disponible en stock.");
                continue;
            }

            // Restricción de hasta 10 unidades por producto
            if (cantidadVendida > 10) {
                System.out.println("No se pueden vender más de 10 unidades de " + producto.getId() + " " + producto.getDescripcion());
                continue;
            }

            // En caso de que el valor original de cantidad a comprar haya sufrido un cambio significará que hay stock insuficiente del producto
            if (cantidadVendida != item.getCantidad()) {
                hayStockInsuficiente = true;
            }

            // Calcular el precio total del producto así como su costo de compra.
            precioTotalProducto = producto.getPrecioVentaAlPublico() * cantidadVendida;
            costoTotalCompra = producto.getCostoProducto() * cantidadVendida;


            if (producto instanceof ProductoDeLimpieza){
                // Variables para calcular si se respetan los % de ganancia.
                float porcentajeMinDeGanancia = 10.0f;
                float porcentajeMaxDeGanancia = 25.0f;
                // Variables para calcular si se respetan los % de descuento.
                float porcentajeMaxDeDescuento = 25.0f;
                float porcentajeDeDescuento = ((ProductoDeLimpieza) producto).getPorcentajeDescuento();


                // En caso de que el porcentaje de descuento del producto sea mayor al porcentaje máximo permitido, se obviará su compra
                if (porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }else { // de lo contrario se asigna el nuevo precio al producto
                    precioTotalProducto = ((ProductoDeLimpieza) producto).getPrecioConDescuento() * cantidadVendida;
                }

                // Primero se compara los productos de limpieza con tipo de aplicación en Ropa y Multiuso, los cuales no tienen restricción de ganancia minima
                if (((ProductoDeLimpieza) producto).getTipoDeAplicacion().equalsIgnoreCase("ropa") || ((ProductoDeLimpieza) producto).getTipoDeAplicacion().equalsIgnoreCase("multiuso")){
                    if (calcularPorcentajeMaximoDeGanancia(porcentajeMaxDeGanancia, cantidadVendida, precioTotalProducto, costoTotalCompra)){
                        System.out.println("El porcentaje de ganancia para el producto: " + producto.getId() + " " + producto.getDescripcion() + " excede el máximo permitido.");
                        continue;
                    }
                } else if (calcularPorcentajeMaximoDeGanancia(porcentajeMaxDeGanancia, cantidadVendida, precioTotalProducto, costoTotalCompra) && calcularPorcentajeMinimoDeGanancia(porcentajeMinDeGanancia, cantidadVendida, precioTotalProducto, costoTotalCompra)){
                    System.out.println("El porcentaje de ganancia para el producto: " + producto.getId() + " " + producto.getDescripcion() + " no cumple con el definido");
                    System.out.println("No puede ser menos del 10% de ganancia ni más del 25% de ganancia");
                    continue;
                }

            }

            if (producto instanceof ProductoEnvasado){

                // Comprobar la fecha de vencimiento del producto
                if (productoVencido(producto)){
                    System.out.println(producto.getId() + " " + producto.getDescripcion() + " Se encuentra vencido, no fue posible realizar su venta");
                    continue;
                }

                // Impuesto del 10% a productos importados
                if (((ProductoEnvasado) producto).getEsImportado()){
                    precioTotalProducto =  (producto.getPrecioVentaAlPublico() * 1.10f) * cantidadVendida;
                }

                float precioConDescuento = ((ProductoEnvasado) producto).getPrecioConDescuento();
                float porcentajeMaxDeDescuento = 20.0f;
                float porcentajeDeDescuento = ((ProductoEnvasado) producto).getPorcentajeDescuento();

                if (precioConDescuento < producto.getCostoProducto() || porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }

            }

            if (producto instanceof ProductoBebida){

                // Comprobar la fecha de vencimiento del producto
                if (productoVencido(producto)){
                    System.out.println(producto.getId() + " " + producto.getDescripcion() + " Se encuentra vencido, no fue posible realizar su venta");
                    continue;
                }

                // Impuesto del 10% a productos importados
                if (((ProductoBebida) producto).getEsImportado()){
                    precioTotalProducto =  (producto.getPrecioVentaAlPublico() * 1.10f) * cantidadVendida;
                }

                float precioConDescuento = ((ProductoBebida) producto).getPrecioConDescuento();
                float porcentajeMaxDeDescuento = 20.0f;
                float porcentajeDeDescuento = ((ProductoBebida) producto).getPorcentajeDescuento();

                if (precioConDescuento < producto.getCostoProducto() || porcentajeDeDescuento > porcentajeMaxDeDescuento) {
                    System.out.println("El descuento registrado para el producto " + producto.getId() + " " + producto.getDescripcion() + " no pudo ser aplicado.");
                    continue;
                }

            }

            if (producto instanceof Comestibles && (calcularPorcentajeMaximoDeGanancia(20.0f, cantidadVendida, precioTotalProducto, costoTotalCompra))) {
                System.out.println("El porcentaje de ganancia del producto: " + producto.getId() + " " + producto.getDescripcion() + " se excede al permitido del 20%");
                continue;
            }

            // Actualizar el total de la venta
            totalVenta += precioTotalProducto;
            float precioUnitarioFinal = precioTotalProducto / cantidadVendida;

            // Imprimir el detalle de la venta para el producto actual
            System.out.println(producto.getId() + " " + producto.getDescripcion() + " " +
                    cantidadVendida + " x " + precioUnitarioFinal);
        }

        if (hayStockInsuficiente) {
            System.out.println("Hay productos con stock disponible menor al solicitado.");
        }

        saldoEnCaja += totalVenta;

        if (totalVenta != 0){
            System.out.println("TOTAL VENTA: " + totalVenta);
            System.out.println("NUEVO SALDO EN CAJA: " + saldoEnCaja);
        }else throw new Exception("No se realizó ninguna venta, el saldo en la caja permanece igual" +
                "\nSaldo en caja: " + saldoEnCaja);
    }

    private boolean productoVencido(Producto producto) {
        Date fechaHoy = new Date();

        if (producto instanceof ProductoEnvasado){
            Date vencimientoProducto = ((ProductoEnvasado) producto).getFechaVencimiento();
            return vencimientoProducto.before(fechaHoy);
        }else {
            Date vencimientoProducto = ((ProductoBebida) producto).getFechaVencimiento();
            return vencimientoProducto.before(fechaHoy);
        }

    }

    private boolean calcularPorcentajeMaximoDeGanancia(float porcentajeMaximo, int cantidadVendida, float precioTotalProducto, float costoTotalCompra) {
        // Ganancia total = total de venta - total de costo
        float gananciaTotal = precioTotalProducto - costoTotalCompra;

        // Pasar a porcentaje
        float porcentajeGanancia = (gananciaTotal / costoTotalCompra) * 100;

        // Comparar y devolver si el porcentaje de ganancia supera el máximo permitido
        return porcentajeGanancia > porcentajeMaximo;
    }

    private boolean calcularPorcentajeMinimoDeGanancia(float porcentajeMinimo, int cantidadVendida, float precioTotalProducto, float costoTotalCompra) {
        // Ganancia total = total de venta - total de costo
        float gananciaTotal = precioTotalProducto - costoTotalCompra;

        // Pasar a porcentaje
        float porcentajeGanancia = (gananciaTotal / costoTotalCompra) * 100;

        // Comparar y devolver si el porcentaje de ganancia es menor al minimo permitido
        return porcentajeGanancia < porcentajeMinimo;
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
            }
        }else throw new Exception("El producto no se encuentra en stock");

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