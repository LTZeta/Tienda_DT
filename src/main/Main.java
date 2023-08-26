package main;

import entidades.*;
import main.menus.Menu;

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
        Menu menu = new Menu();

        System.out.println("Antes de comenzar necesitamos el nombre de su tienda:");
        String nombre = scanner.next();
        tienda.setNombre(nombre+scanner.nextLine());
        System.out.println("Genial, ahora por favor indique su saldo en caja (Puede ser un numero con coma):");
        tienda.setSaldoEnCaja(scanner.nextFloat());
        System.out.println("Bievenido al menu de la tienda");
        while(bandera){

            System.out.println("Elegir la opcion adecuada:");
            System.out.println("1. Agregar producto a Stock");
            System.out.println("2. Vender productos");
            System.out.println("3. Deshabilitar un producto");
            System.out.println("4. Aplicar descuento a un producto");
            System.out.println("99. Finalizar simulacion");
            System.out.print("Elija una opción: ");
            String opcion = scanner.next();

            switch (opcion){

                case "1":
                    System.out.println("Genial, ahora por favor, indique la familia del producto nuevo:");
                    System.out.println("1. Envasado");
                    System.out.println("2. Bebida");
                    System.out.println("3. de Limpieza");
                    byte productoACrear = scanner.nextByte();
                    if (productoACrear == 1){
                        try {
                            tienda = menu.crearProductoEnvasado(tienda);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    } else if (productoACrear == 2) {
                        try {
                            tienda = menu.crearProductoBebida(tienda);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    } else if (productoACrear == 3) {
                        try {
                            tienda = menu.crearProductoLimpieza(tienda);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case "2":
                    List<ItemCompra> carrito = new ArrayList<>();
                    try {
                        menu.menuDeVentas(scanner, tienda, carrito);
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
                    System.out.println("Por favor, ingrese la ID completa del producto que desea deshabilitar: ");
                    String idP = scanner.next();
                    idP += scanner.nextLine();
                    Producto producto1 = tienda.buscarProductoPorId(idP);
                    System.out.println("¿Que porcentaje de descuento quiere aplicar al producto?");
                    System.out.println("Ejemplo para 10% escribir: 10,0\nPara %12,5 escribir: 12,5. ¡Gracias!");
                    Float descuentoAAplicar = scanner.nextFloat();
                    if (producto1 == null){
                        System.out.println("El producto no existe");
                        break;
                    }
                    try {
                        tienda.definirDescuento(producto1.getCategoria(), producto1, descuentoAAplicar);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "99":
                    bandera = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
        System.out.println("Un placer simular una tienda, hasta pronto");
        scanner.close();
    }
}