package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the producto database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity {
    private Integer id;
    private String codigoBarra;
    private String color;
    private BigDecimal descuento;
    private Timestamp fecCre;
    private Timestamp fecMod;
    private String nombre;
    private BigDecimal precio;
    //Variable para guardar el precio final aplicado los descuentos
    private BigDecimal precioFinal;
    private String talla;
    private String usuCre;
    private String usuMod;
    private ColeccionEntity coleccionBean;
    private EstadoProductoEntity estadoProducto;
    private TipoProductoEntity tipoProducto;

}