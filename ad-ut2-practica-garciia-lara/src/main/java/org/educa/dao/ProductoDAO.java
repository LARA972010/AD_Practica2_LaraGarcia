package org.educa.dao;

import org.educa.entity.ProductoEntity;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    List<ProductoEntity> findAllProducts();

    ProductoEntity getFirstProductoByNameTallaAndColor(String nombre, String talla, String color);

    List<ProductoEntity> findByName(String producto) throws SQLException;

    void markProductAsSold(ProductoEntity producto);
}
