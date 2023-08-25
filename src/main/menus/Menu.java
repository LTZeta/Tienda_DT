package main.menus;

import entidades.ProductoBebida;
import entidades.ProductoDeLimpieza;
import entidades.ProductoEnvasado;
import entidades.Tienda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Menu {

    String id;
    String key;

    public Tienda crearProductoEnvasado(Tienda tienda) throws Exception {

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        ProductoEnvasado nuevoProducto = new ProductoEnvasado("", "", 0, 0F, 0F, true, "", true, new Date(), 0);
        id = tienda.getPREFIJO_ID_ENVASADOS();
        key = tienda.getKEY_ENVASADOS();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        nuevoProducto.setId(id+scanner.next());
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
            tienda.agregarProducto(key, nuevoProducto, nuevoProducto.getCantEnStock());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tienda;
    }

    public Tienda crearProductoBebida(Tienda tienda) throws Exception {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        ProductoBebida nuevoProducto = new ProductoBebida("", "", 0, 0F, 0F, true, false, true, new Date(), 0);
        id = tienda.getPREFIJO_ID_BEBIDAS();
        key = tienda.getKEY_BEBIDAS();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        nuevoProducto.setId(id+scanner.next());
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
        System.out.println("¿Pertenece a la familia de bebidas alcoholicas?");
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
            Date fecha = new Date();
            fecha = formatoEntrada.parse(String.valueOf(fecha));
            nuevoProducto.setFechaVencimiento(fecha);
        }
        System.out.println("Ingresa las calorias del producto:");
        nuevoProducto.setCalorias(scanner.nextInt());
        try {
            tienda.agregarProducto(key, nuevoProducto, nuevoProducto.getCantEnStock());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tienda;
    }

    public Tienda crearProductoLimpieza(Tienda tienda) throws Exception {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
        ProductoDeLimpieza nuevoProducto = new ProductoDeLimpieza("", "", 0, 0F, 0F, true, "");
        id = tienda.getPREFIJO_ID_LIMPIEZA();
        key = tienda.getKEY_LIMPIEZA();


        System.out.println("Por favor, complete el siguiente formulario siguiendo las instrucciones");
        System.out.println("número identificador (máximo de 3 numeros), ejemplo '123':");
        nuevoProducto.setId(id+scanner.next());
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
        System.out.println("1 = cocina");
        System.out.println("2 = pisos");
        System.out.println("3 = ropa");
        System.out.println("4 = multiuso");
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
            tienda.agregarProducto(key, nuevoProducto, nuevoProducto.getCantEnStock());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tienda;
    }
}
