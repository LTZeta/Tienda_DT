package menu;

import entidades.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuDeVentas {

    private String id;
    private String key;

    public void menuDeVentas(Scanner scanner, Tienda tienda, List<ItemCompra> carrito) throws Exception {
        boolean bandera = true;
        while (bandera) {

            System.out.println("\nMenú de ventas:");
            System.out.println("1. Mostrar productos en stock");
            System.out.println("2. Vender productos");
            System.out.println("3. Ver carrito");
            System.out.println("4. Eliminar producto del carrito");
            System.out.println("5. Finalizar compra");
            System.out.println("6. Volver atras");
            System.out.print("Elija una opción: ");
            byte opcion = scanner.nextByte();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    tienda.mostrarProductos();
                    break;

                case 2:
                    System.out.println("Para agregar un producto al carrito favor de ingresar el codigo más la cantidad que desea llevar");
                    System.out.println("Ingrese el ID del producto a vender: ");
                    String idProducto = scanner.next();
                    System.out.println("Ingrese la cantidad a vender: ");
                    int cantidad = scanner.nextInt();
                    try {
                        agregarProductoAlCarrito(carrito, tienda, idProducto, cantidad);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    // Ver carrito
                    System.out.println("\nCarrito de compras:");
                    for (ItemCompra item : carrito) {
                        System.out.println("Producto: " + item.getProducto().getDescripcion() +
                                ", Cantidad: " + item.getCantidad());
                    }
                    break;

                case 4:
                    // Eliminar producto del carrito
                    System.out.println("Ingrese la ID del producto a eliminar:");
                    String idProductoAEliminar = scanner.next();
                    scanner.nextLine(); // Consumir el salto de línea pendiente

                    // llamar a la función que devuelve un booleano en caso de que se haya borrado algún item buscando con el código proporcionado
                    if (eliminarProductoDelCarrito(idProductoAEliminar, carrito)){
                        System.out.println("El producto fue eliminado exitosamente");
                    }else System.out.println("No se encontró ningún producto con ese código");
                    break;

                case 5:
                    // verificar si el carrito está vacio o excede el máximo de 3 items permitidos
                    if (carrito.isEmpty() || carrito.size() > 3){
                        throw new Exception("No se puede realizar una venta de más de 3 productos o con 0 productos");
                    }else{
                        try {
                            tienda.procesarVenta(carrito);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    bandera = false;
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    bandera = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public boolean eliminarProductoDelCarrito(String idProductoAEliminar, List<ItemCompra> carrito) {
        return carrito.removeIf(item -> item.getProducto().getId().equalsIgnoreCase(idProductoAEliminar));
    }

    public void crearProductoEnvasado(Tienda tienda) throws Exception {

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        ProductoEnvasado nuevoProducto = new ProductoEnvasado("", "", 0, 0F, 0F, true, "", true, new Date(), 0, tienda.getKEY_ENVASADOS());
        id = tienda.getPREFIJO_ID_ENVASADOS();
        key = tienda.getKEY_ENVASADOS();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        System.out.println("Tenga en cuenta que a este numero se le agregara AB al principio por ejemplo si escribió '123' el código final sería 'AB123'");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        if (codigo <= 999 && codigo >= 0){
            nuevoProducto.setId(id+codigo);
        }else throw new Exception("el código ingresado no es válido");
        System.out.println("Descripción:");
        String descripcion = scanner.next();
        descripcion += scanner.nextLine();
        nuevoProducto.setDescripcion(descripcion);
        System.out.println("Stock Inicial:");
        nuevoProducto.setCantEnStock(scanner.nextInt());
        System.out.println("Costo unitario de compra:");
        nuevoProducto.setCostoProducto(scanner.nextFloat());
        System.out.println("Precio de venta al publico:");
        nuevoProducto.setPrecioVentaAlPublico(scanner.nextFloat());
        System.out.println("Indique el tipo de envase\n" +
                "1 = Plástico\n" +
                "2 = Vidrio\n" +
                "3 = Lata:");
        short tipoEnvase = scanner.nextShort();
        if (tipoEnvase == 1){
            nuevoProducto.setTipoEnvase("Plástico");
        }else if(tipoEnvase == 2){
            nuevoProducto.setTipoEnvase("Vidrio");
        }else if (tipoEnvase == 3){
            nuevoProducto.setTipoEnvase("Lata");
        }
        System.out.println("¿El producto es importado? contestar 'true' de ser verdadero o 'false' en caso de ser falso:");
        nuevoProducto.setEsImportado(scanner.nextBoolean());
        System.out.println("Ingresa la fecha en formato dd-MM-yyyy: ");
        String inputFecha = scanner.next();
        inputFecha += scanner.nextLine();
        try {
            Date fecha = formatoEntrada.parse(inputFecha);
            nuevoProducto.setFechaVencimiento(fecha);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha.");
            Date fecha = new Date();
            fecha = formatoEntrada.parse(String.valueOf(fecha));
            nuevoProducto.setFechaVencimiento(fecha);
        }
        System.out.println("Ingresa las calorias del producto:");
        nuevoProducto.setCalorias(scanner.nextInt());
        try {
            tienda.crearEnvasado(nuevoProducto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void crearProductoBebida(Tienda tienda) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Date hoy = new Date();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");

        ProductoBebida nuevoProducto = new ProductoBebida("", "", 0, 0F, 0F, true, false, true, hoy, 0, tienda.getKEY_BEBIDAS());
        id = tienda.getPREFIJO_ID_BEBIDAS();
        key = tienda.getKEY_BEBIDAS();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        System.out.println("Tenga en cuenta que a este numero se le agregara AC al principio por ejemplo si escribió '123' el código final sería 'AC123'");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        if (codigo <= 999 && codigo >= 0){
            nuevoProducto.setId(id+codigo);
        }else throw new Exception("el código ingresado no es válido");
        System.out.println("Descripción:");
        String descripcion = scanner.next();
        descripcion += scanner.nextLine();
        nuevoProducto.setDescripcion(descripcion);
        System.out.println("Stock Inicial:");
        nuevoProducto.setCantEnStock(scanner.nextInt());
        System.out.println("Costo unitario de compra:");
        nuevoProducto.setCostoProducto(scanner.nextFloat());
        System.out.println("Precio de venta al publico:");
        nuevoProducto.setPrecioVentaAlPublico(scanner.nextFloat());
        System.out.println("¿El producto es importado? contestar 'true' de ser verdadero o 'false' en caso de ser falso:");
        nuevoProducto.setEsImportado(scanner.nextBoolean());
        System.out.println("¿Pertenece a la familia de bebidas alcoholicas? contestar 'true' de ser verdadero o 'false' en caso de ser falso:");
        nuevoProducto.setEsAlcoholica(scanner.nextBoolean());
        if (nuevoProducto.getEsAlcoholica()){
            System.out.println("Indique la graduación de alcohol del producto (0-100):");
            nuevoProducto.setGraduacionAlcoholica(scanner.nextByte());
        }
        System.out.println("Ingresa la fecha en formato dd-MM-yyyy:");
        String inputFecha = scanner.next();
        inputFecha += scanner.nextLine();
        try {
            Date fecha = formatoEntrada.parse(inputFecha);
            nuevoProducto.setFechaVencimiento(fecha);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha.");
            hoy = formatoEntrada.parse(String.valueOf(hoy));
            nuevoProducto.setFechaVencimiento(hoy);
        }
        System.out.println("Ingresa las calorias del producto:");
        nuevoProducto.setCalorias(scanner.nextInt());
        try {
            tienda.crearBebida(nuevoProducto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void crearProductoLimpieza(Tienda tienda) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ProductoDeLimpieza nuevoProducto = new ProductoDeLimpieza("", "", 0, 0F, 0F, true, "", tienda.getKEY_LIMPIEZA());
        id = tienda.getPREFIJO_ID_LIMPIEZA();
        key = tienda.getKEY_LIMPIEZA();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        System.out.println("Tenga en cuenta que a este numero se le agregara AZ al principio por ejemplo si escribió '123' el código final sería 'AZ123'");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        if (codigo <= 999 && codigo >= 0){
            nuevoProducto.setId(id+codigo);
        }else throw new Exception("el código ingresado no es válido");
        System.out.println("Descripción:");
        String descripcion = scanner.next();
        descripcion += scanner.nextLine();
        nuevoProducto.setDescripcion(descripcion);
        System.out.println("Stock Inicial:");
        nuevoProducto.setCantEnStock(scanner.nextInt());
        System.out.println("Costo unitario de compra:");
        nuevoProducto.setCostoProducto(scanner.nextFloat());
        System.out.println("Precio de venta al publico:");
        nuevoProducto.setPrecioVentaAlPublico(scanner.nextFloat());
        System.out.println("Indique el tipo de aplicación del producto teniendo en cuenta que");
        System.out.println("1. Cocina");
        System.out.println("2. Pisos");
        System.out.println("3. Ropa");
        System.out.println("4. Multiuso");
        switch (scanner.next()){
            case "1":
                nuevoProducto.setTipoDeAplicacion("cocina");
                break;
            case "2":
                nuevoProducto.setTipoDeAplicacion("pisos");
                break;
            case "3":
                nuevoProducto.setTipoDeAplicacion("ropa");
                break;
            case "4":
                nuevoProducto.setTipoDeAplicacion("multiuso");
                break;
        }
        try {
            tienda.crearDeLimpieza(nuevoProducto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void agregarProductoAlCarrito(List<ItemCompra> carrito, Tienda tienda, String idProducto, int cantidad) throws Exception {
        String prefijo = idProducto.substring(0, 2);
        if (prefijo.equalsIgnoreCase("AB")) {
            List<Producto> productos = tienda.getProductosEnStock().get(tienda.getKEY_ENVASADOS());
            for (Producto p: productos) {
                if (p.getId().equalsIgnoreCase(idProducto) && p.getCantEnStock() >= cantidad){
                    carrito.add(new ItemCompra(p, cantidad));
                }
            }
        } else if (prefijo.equalsIgnoreCase("AC")) {
            List<Producto> productos = tienda.getProductosEnStock().get(tienda.getKEY_BEBIDAS());
            for (Producto p: productos) {
                if (p.getId().equalsIgnoreCase(idProducto) && p.getCantEnStock() >= cantidad){
                    carrito.add(new ItemCompra(p, cantidad));
                }
            }
        } else if (prefijo.equalsIgnoreCase("AZ")) {
            List<Producto> productos = tienda.getProductosEnStock().get(tienda.getKEY_LIMPIEZA());
            for (Producto p: productos) {
                if (p.getId().equalsIgnoreCase(idProducto) && p.getCantEnStock() >= cantidad){
                    carrito.add(new ItemCompra(p, cantidad));
                }
            }
        }else throw new Exception("El código ingresado no es válido");
    }
}
