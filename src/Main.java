import entidades.*;
import menu.MenuDeVentas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Tienda tienda = new Tienda("", 0F);
        boolean bandera = true;
        MenuDeVentas menuDeVentas = new MenuDeVentas();

        System.out.println("Antes de comenzar necesitamos el nombre de su tienda:");
        String nombre = scanner.next();
        tienda.setNombre(nombre+scanner.nextLine());
        System.out.println("Genial, ahora por favor indique su saldo en caja (Puede ser un numero con coma):");
        tienda.setSaldoEnCaja(scanner.nextFloat());
        System.out.println("Bievenido al menu de la tienda");
        while(bandera){

            System.out.println("Elegir la opcion adecuada:");
            System.out.println("1. Crear producto / Ingresar más unidades a Stock");
            System.out.println("2. Menú de Ventas");
            System.out.println("3. Deshabilitar un producto");
            System.out.println("4. Habilitar un producto");
            System.out.println("5. Aplicar descuento a un producto");
            System.out.println("6. Opciones Extras");
            System.out.println("99. Finalizar simulacion");
            System.out.print("Elija una opción: ");
            String opcion = scanner.next();

            switch (opcion){
                case "1":
                    System.out.println("Genial, ahora por favor, indique la familia del producto nuevo:");
                    System.out.println("1. Envasado");
                    System.out.println("2. Bebida");
                    System.out.println("3. de Limpieza");
                    System.out.println("4. Agregar unidades a un producto existente");
                    byte productoACrear = scanner.nextByte();
                    switch (productoACrear) {
                        case 1:
                            try {
                                menuDeVentas.crearProductoEnvasado(tienda);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case 2:
                            try {
                                menuDeVentas.crearProductoBebida(tienda);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case 3:
                            try {
                                menuDeVentas.crearProductoLimpieza(tienda);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                            break;

                        case 4:
                            System.out.println("Productos en Stock:");
                            tienda.mostrarProductos();
                            System.out.print("Escriba la ID del producto que desea actualizar: ");
                            String idProductoAActualizar = scanner.next();
                            scanner.nextLine(); // Limpiar buffer del scanner
                            System.out.print("Cuantas nuevas unidades ingresaron: ");
                            Integer nuevasUnidades = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                tienda.actualizarStockDeProducto(idProductoAActualizar, nuevasUnidades);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                            break;

                        default:
                            System.out.println("Opción inválida, volviendo al menú de inicio");
                            break;
                    }
                    break;

                case "2":
                    List<ItemCompra> carrito = new ArrayList<>();
                    try {
                        menuDeVentas.menuDeVentas(scanner, tienda, carrito);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Por favor, ingrese la ID del producto que desea deshabilitar: ");
                    String id = scanner.next();
                    id += scanner.nextLine();
                    Producto producto = tienda.buscarProductoPorId(id);
                    if (producto == null){
                        System.out.println("El producto no existe");
                        break;
                    }
                    try {
                        tienda.deshabilitarProducto(producto.getCategoria(), producto.getId());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "4":
                    System.out.println("Por favor, ingrese la ID del producto que desea deshabilitar: ");
                    String id1 = scanner.next();
                    id1 += scanner.nextLine();
                    Producto producto1 = tienda.buscarProductoPorId(id1);
                    if (producto1 == null){
                        System.out.println("El producto no existe");
                        break;
                    }
                    try {
                        tienda.habilitarProducto(producto1.getCategoria(), producto1.getId());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "5":
                    System.out.println("Por favor, ingrese la ID completa del producto que desea deshabilitar: ");
                    String id2 = scanner.next();
                    id2 += scanner.nextLine();
                    Producto producto2;
                    try {
                        producto2 = tienda.buscarProductoPorId(id2);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("¿Que porcentaje de descuento quiere aplicar al producto?");
                    System.out.println("Ejemplo para 10% escribir: 10,0");
                    System.out.println("Para 12,5% escribir: 12,5.");
                    Float descuentoAAplicar = scanner.nextFloat();
                    try {
                        tienda.definirDescuento(producto2.getCategoria(), producto2, descuentoAAplicar);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "6":
                    System.out.println("Elija la opción adecuada:");
                    System.out.println("1. Obtener Comestibles Con Menor Descuento");
                    System.out.println("2. Listar Productos Con Utilidades Inferiores");
                    byte opcionElegida = scanner.nextByte();
                    scanner.nextLine();
                    if (opcionElegida == 1){
                        System.out.println("Escriba el % que se tomará como parámetro ");
                        System.out.println("Ejemplo para 10% escribir: 10,0");
                        System.out.println("Para 12,5% escribir: 12,5.");
                        System.out.print("porcentaje de descuento a tener en cuenta: ");
                        float porcentajeDescuentoAComprobar = scanner.nextFloat();
                        scanner.nextLine();
                        try {
                            List<String> comestiblesConMenorDescuento = tienda.obtenerComestiblesConMenorDescuento(porcentajeDescuentoAComprobar);
                            tienda.imprimirComestiblesConMenorDescuento(comestiblesConMenorDescuento);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    if (opcionElegida == 2){
                        System.out.println("Escriba el % que se tomará como parámetro ");
                        System.out.println("Ejemplo para 10% escribir: 10,0");
                        System.out.println("Para 12,5% escribir: 12,5.");
                        System.out.print("porcentaje de utilidad a tener en cuenta: ");
                        float porcentajeDescuentoAComprobar = scanner.nextFloat();
                        scanner.nextLine();
                        try {
                            tienda.listarProductosConUtilidadesInferiores(porcentajeDescuentoAComprobar);
                            break;
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                    System.out.println("Opción inválida volviendo al menú principal.");
                    break;

                case "99":
                    bandera = false;
                    break;

                default:
                    System.out.println("Opción inválida volviendo al menú principal.");
            }
        }
        System.out.println("Un placer simular una tienda, hasta pronto");
        scanner.close();
    }
}