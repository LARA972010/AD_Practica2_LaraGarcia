package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * The persistent class for the estado_pedido database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoPedidoEntity {
    private Integer id;
    private Timestamp fecCre;
    private Timestamp fecMod;
    private String nombre;
    private String usuCre;
    private String usuMod;
}