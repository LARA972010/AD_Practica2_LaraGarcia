package org.educa.service;

import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDaoImpl;
import org.educa.entity.ProductoEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductoService {

    private final ProductoDAO productoDAO;
    public ProductoService() {
        this.productoDAO = new ProductoDaoImpl();
    }


    public List<ProductoEntity> findAllProducts() throws SQLException {
        List<ProductoEntity> products = productoDAO.findAllProducts();

        for(ProductoEntity producto : products){
            BigDecimal prec = producto.getPrecio();
            BigDecimal desc = producto.getDescuento();

            if(prec !=null && desc != null){
                BigDecimal descApli = prec.multiply(desc).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                BigDecimal finalPrice = prec.subtract(descApli).setScale(2, RoundingMode.HALF_UP);
                producto.setPrecioFinal(finalPrice.subtract(descApli));
            }

        }

        return productoDAO.findAllProducts();
    }


    public ProductoEntity getFirstProductoByNameTallaAndColor(String nombre, String talla, String color) throws SQLException {
        return productoDAO.getFirstProductoByNameTallaAndColor(nombre, talla, color);
    }

    public List<ProductoEntity> findByName(ProductoEntity producto) throws SQLException {
        return productoDAO.findByName(producto.getNombre());
    }

    // Este método no lo desarrollo ya que la práctica solo te pide que realices un pedido, cuando se hace el mismo
    // automáticamente pasa a estado 1 (Preparando), cuando se siga desarrollando la api tendrá lógica hacer un
    // método con un update
    public void markProductAsSold(Connection c, ProductoEntity producto) throws SQLException {
        productoDAO.markProductAsSold(producto);
    }
}
