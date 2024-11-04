package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the pedido database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {
    private Integer id;
    private Timestamp fecha;
    private ClienteEntity cliente;
    private DireccionEntity direccion;
    private EstadoPedidoEntity estadoPedido;
    private List<PedidoProductoEntity> pedidoProducto = new ArrayList<>();
}