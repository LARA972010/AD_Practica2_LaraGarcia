package org.educa;

import org.educa.entity.*;
import org.educa.service.ClienteService;
import org.educa.service.PedidoService;
import org.educa.service.ProductoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final ClienteService clienteService = new ClienteService();
    private final ProductoService productoService = new ProductoService();

    public void init() {
        try (Scanner sc = new Scanner(System.in)) {
            ClienteEntity clienteEntity = login(sc);
            PedidoEntity pedido = new PedidoEntity();
            pedido.setCliente(clienteEntity);
            boolean finalizar = false;
            do {
                ProductoEntity productoEntity = elegirProducto(sc);
                if (productoEntity != null) {
                    productoEntity = elegirColorYTalla(sc, productoEntity);
                    if (productoEntity != null) {
                        crearPedidoProducto(pedido, productoEntity);
                        System.out.println("Desea realizar el pedido:");
                        System.out.println("1- Realizar pedido");
                        System.out.println("Cualquier otra tecla - Seguir comprando");
                        int option = sc.nextInt();
                        sc.nextLine();
                        if (option == 1) {
                            createPedido(sc, pedido);
                        }
                    }
                } else {
                    finalizar = true;
                    System.out.println("Saliendo...");
                }
            } while (!finalizar);

        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    private void createPedido(Scanner sc, PedidoEntity pedido) throws SQLException {
        DireccionEntity direccion = seleccioneDireccionCliente(sc, pedido.getCliente());
        if (direccion != null) {
            pedido.setDireccion(direccion);
            PedidoService pedidoService = new PedidoService();
            pedidoService.insertarPedido(pedido);
            System.out.println("Pedido creado");
        } else {
            System.out.println("Pedido cancelado");
        }
    }

    private DireccionEntity seleccioneDireccionCliente(Scanner sc, ClienteEntity cliente) {
        DireccionEntity direccion = null;
        if (cliente.getDirecciones().isEmpty()) {
            System.out.println("Cliente sin direcciones:");

        } else {
            System.out.println("Seleccione direccion de envío:");


            for (int i = 0; i < cliente.getDirecciones().size(); i++) {
                direccion = cliente.getDirecciones().get(i);
                System.out.printf("%d- %s (%s)%n", i + 1, direccion.getCalle(), direccion.getCp());
            }

            direccion = null;
            boolean selected = false;
            do {
                int option = sc.nextInt();
                sc.nextLine();
                if (option > 0 && option < cliente.getDirecciones().size() + 1) {
                    selected = true;
                    direccion = cliente.getDirecciones().get(option - 1);
                } else {
                    System.err.println("Seleccione una dirección de la lista.");
                }
            } while (!selected);
        }
        return direccion;
    }

    private void crearPedidoProducto(PedidoEntity pedido, ProductoEntity producto) {
        PedidoProductoEntity pedidoProducto = new PedidoProductoEntity();
        pedidoProducto.setPedido(pedido);
        pedidoProducto.setProducto(producto);
        pedido.getPedidoProducto().add(pedidoProducto);
    }

    private ProductoEntity elegirColorYTalla(Scanner sc, ProductoEntity producto) throws SQLException {
        System.out.printf("Producto seleccionado: %s (%s).%nPor favor seleccione talla y color:%n",
                producto.getNombre(), producto.getPrecioFinal());
        List<ProductoEntity> productos = productoService.findByName(producto);

        System.out.println("0- Salir");
        for (int i = 0; i < productos.size(); i++) {
            ProductoEntity productoEntity = productos.get(i);
            System.out.printf("%d- Talla %s %s%n", i + 1, productoEntity.getTalla(), productoEntity.getColor());
        }

        boolean selected = false;
        boolean exit = false;

        do {
            int option = sc.nextInt();
            sc.nextLine();
            if (option > 0 && option < productos.size() + 1) {
                selected = true;
                ProductoEntity productoElegido = productos.get(option - 1);
                producto = productoService.getFirstProductoByNameTallaAndColor(productoElegido.getNombre(),
                        productoElegido.getTalla(), productoElegido.getColor());

            } else if (option == 0) {
                exit = true;
                producto = null;
            } else {
                System.err.println("Seleccione una talla de la lista.");
            }
        } while (!selected && !exit);

        return producto;
    }

    private ProductoEntity elegirProducto(Scanner sc) throws SQLException {
        System.out.println("Seleccione producto:");
        List<ProductoEntity> productoEntities = productoService.findAllProducts();
        System.out.println("0- Salir");
        for (int i = 0; i < productoEntities.size(); i++) {
            ProductoEntity productoEntity = productoEntities.get(i);
            System.out.printf("%d- %s (%s)%n", i + 1, productoEntity.getNombre(), productoEntity.getPrecioFinal());
        }

        ProductoEntity productoEntity = null;
        boolean selected = false;
        boolean exit = false;

        do {
            int option = sc.nextInt();
            sc.nextLine();
            if (option > 0 && option < productoEntities.size() + 1) {
                selected = true;
                productoEntity = productoEntities.get(option - 1);
            } else if (option == 0) {
                exit = true;
            } else {
                System.err.println("Seleccione un producto de la lista.");
            }
        } while (!selected && !exit);

        return productoEntity;
    }

    public ClienteEntity login(Scanner sc) throws Exception {
        ClienteEntity clienteEntity;
        do {
            System.out.println("Por favor teclee el dni:");
            String dni = sc.nextLine();
            System.out.println("Por favor teclee el contraseña:");
            String password = sc.nextLine();
            clienteEntity = clienteService.login(dni, password);
            if (clienteEntity == null) {
                System.err.println("Usuario o contraseña incorrectos");
            }
        } while (clienteEntity == null);

        return clienteEntity;
    }


}
