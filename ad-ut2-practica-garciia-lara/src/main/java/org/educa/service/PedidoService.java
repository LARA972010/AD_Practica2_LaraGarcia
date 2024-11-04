package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDaoImpl;
import org.educa.dao.PedidoDAO;
import org.educa.dao.PedidoDaoImpl;
import org.educa.entity.DireccionEntity;
import org.educa.entity.PedidoEntity;
import org.educa.entity.PedidoProductoEntity;
import org.educa.entity.ProductoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class PedidoService {
    private final ProductoService productoService;
    private final ClienteDAO clienteDAO; //He decidido obtener las direcciones usando el método del clienteDAO
    private final PedidoDAO pedidoDAO;

    //constructor sin parámetros
    public PedidoService() {
        this.productoService = new ProductoService();
        this.clienteDAO = new ClienteDaoImpl();
        this.pedidoDAO = new PedidoDaoImpl();
    }

    public List<DireccionEntity> obtenerDireccionesPorClienteId(int clienteId) throws SQLException {
        return clienteDAO.obtenerDireccionesPorClienteId(clienteId); //Llama al método en ClienteDaoImpl
    }

    //TODO: Crear pedido
    public void insertarPedido(PedidoEntity pedido) {
        Connection connection = null; // Asegúrate de manejar la conexión correctamente
        try {
            connection = ConnectionPool.getDataSource().getConnection();
            connection.setAutoCommit(false); //Realizo la conexión de esta otra forma (en las anteriores lo hago en el dao
            // y lo inicio dentro de try (así no se cerrarían) de esta manera pretendo mostrar otra forma de realizar la conexión
            //de esta forma será necesario cerrar la misma.

            //Insertar pedido y obtener el ID, como se genera auromáticamente nos lo tiene que devolver
            int pedidoId = pedidoDAO.insertarPedido(pedido, connection);

            //Insertar en histórico
            String cambios = "Pedido creado"; //En cambios he decidido introducir este string, se podría cambiar.
            String usumod = "usuario"; //asigno usuario
            Timestamp fechaMod = new Timestamp(System.currentTimeMillis());
            pedidoDAO.insertarHistoricoPedido(pedidoId, cambios, usumod, fechaMod, connection);

            //validar productos antes de insertarlos
            for (PedidoProductoEntity pedidoProducto : pedido.getPedidoProducto()) {
                if (pedidoProducto.getProducto() == null || pedidoProducto.getProducto().getId() == null) {
                    throw new IllegalArgumentException("El producto es nulo o su ID es nulo para el pedido.");
                }
                int productoId = pedidoProducto.getProducto().getId();
                pedidoDAO.insertarPedidoProducto(productoId, pedidoId, connection);
            }

            connection.commit(); //confirmar transacción si todos los pasos salieron bien
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); //Hacemos rollback si alguno de los insert anteriores dan fallo.
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Error al insertar el pedido: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}