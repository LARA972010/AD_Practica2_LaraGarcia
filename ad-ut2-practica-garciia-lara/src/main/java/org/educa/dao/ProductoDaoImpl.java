package org.educa.dao;

import org.educa.entity.ProductoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImpl implements ProductoDAO {
    @Override
    public List<ProductoEntity> findAllProducts() {
        List<ProductoEntity> product = new ArrayList<>();
        String sql = "select distinct nombre,precio,descuento from producto";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                ProductoEntity producto = new ProductoEntity();
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecioFinal(rs.getBigDecimal("precio"));
                producto.setDescuento(rs.getBigDecimal("descuento"));
                product.add(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public ProductoEntity getFirstProductoByNameTallaAndColor(String nombre, String talla, String color) {
        ProductoEntity producto = null;
        String sql = "SELECT * FROM producto WHERE nombre = ? AND talla = ? AND color = ? LIMIT 1";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, talla);
            ps.setString(3, color);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new ProductoEntity();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setTalla(rs.getString("talla"));
                    producto.setColor(rs.getString("color"));
                    producto.setPrecioFinal(rs.getBigDecimal("precio"));
                    producto.setDescuento(rs.getBigDecimal("descuento"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public List<ProductoEntity> findByName(String nombre) {
        List<ProductoEntity> productos = new ArrayList<>();
        String sql = "SELECT DISTINCT talla, color FROM producto WHERE nombre = ?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductoEntity producto = new ProductoEntity();
                    producto.setNombre(nombre);
                    producto.setTalla(rs.getString("talla"));
                    producto.setColor(rs.getString("color"));
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public void markProductAsSold(ProductoEntity producto) {
        // Aquí se crearía el update, la lógica de la consola nos hace no utiñizar dicho código.
    }
}
