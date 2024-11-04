package org.educa.dao;

import org.educa.entity.PedidoEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface PedidoDAO {

    int insertarPedido(PedidoEntity pedido, Connection connection) throws SQLException;

    void insertarHistoricoPedido(int pedidoId, String cambios, String usumod, Timestamp fechaMod, Connection connection) throws SQLException;


    void insertarPedidoProducto(int productoId, int pedidoId, Connection connection) throws SQLException;
}