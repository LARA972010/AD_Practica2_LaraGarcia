package org.educa.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the pedido_producto database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoEntity {
    private Integer id;
    private PedidoEntity pedido;
    private ProductoEntity producto;

}