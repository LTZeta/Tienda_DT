package main;

import entidades.ProductoBebida;
import entidades.ProductoDeLimpieza;
import entidades.Tienda;
import main.menus.Menu;

import java.text.SimpleDateFormat;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Tienda tienda = new Tienda("", 0F);
        System.out.println("Antes de comenzar necesitamos el nombre de su tienda:");
        String nombre = scanner.next();
        tienda.setNombre(nombre+scanner.nextLine());
        System.out.println("Genial, ahora por favor indique su saldo en caja (Puede ser un numero con coma):");
        tienda.setSaldoEnCaja(scanner.nextFloat());
        boolean bandera = true;
        Menu menu = new Menu();
        while(bandera){
            System.out.println("Bievenido al menu de la tienda");
            System.out.println("Por favor escribir la opcion adecuada:");
            System.out.println("1 = Agregar producto a Stock");
            System.out.println("2 = Vender productos");
            System.out.println("3 = Deshabilitar un producto existente");
            System.out.println("99 = Finalizar simulacion");
            String opcion = scanner.next();

            switch (opcion){
                case "1":
                    System.out.println("Genial, ahora por favor, indique la familia del producto nuevo:");
                    System.out.println("1 = Envasado");
                    System.out.println("2 = Bebida");
                    System.out.println("3 = de Limpieza");
                    byte productoACrear = scanner.nextByte();
                    if (productoACrear == 1){
                        tienda = menu.crearProductoEnvasado(tienda);
                    } else if (productoACrear == 2) {
                        tienda = menu.crearProductoBebida(tienda);
                    } else if (productoACrear == 3) {
                        tienda = menu.crearProductoLimpieza(tienda);
                    }
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "99":
                    bandera = false;
                    break;
            }
        }
        System.out.println("Un placer simular una tienda, hasta pronto");
        scanner.close();
    }
}