package org.educa.dao;

import org.educa.entity.PedidoEntity;

import java.sql.*;

public class PedidoDaoImpl implements PedidoDAO {

    @Override
    public int insertarPedido(PedidoEntity pedido, Connection connection) throws SQLException {
        String sql = "INSERT INTO pedido (id_cliente, estado, direccion, fecha) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pedido.getCliente().getId());
            preparedStatement.setInt(3, pedido.getDireccion().getId());
            preparedStatement.setInt(2, 1); // Estado siempre 1
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            try (ResultSet generatedKeys = preparedStatement.executeQuery()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el ID del nuevo pedido
                } else {
                    throw new SQLException("No se pudo obtener el ID del pedido generado.");
                }
            }
        }
    }

    @Override
    public void insertarHistoricoPedido(int pedidoId, String cambios, String usumod, Timestamp fechaMod, Connection connection) throws SQLException {
        String sql = "INSERT INTO historico_pedido (id_pedido, cambios, usu_mod, fec_mod) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pedidoId);
            preparedStatement.setString(2, cambios);
            preparedStatement.setString(3, usumod);
            preparedStatement.setTimestamp(4, fechaMod);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void insertarPedidoProducto(int productoId, int pedidoId, Connection connection) throws SQLException {
        String sql = "INSERT INTO pedido_producto (id_pedido, id_producto) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(2, productoId);
            preparedStatement.setInt(1, pedidoId);
            preparedStatement.executeUpdate();
        }
    }
}

