package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.DireccionEntity;
import org.educa.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements ClienteDAO {
    @Override
    public ClienteEntity obtenerClientePorDni(String dni, String pass)  {
        ClienteEntity cliente = null;
        String sql = "SELECT * FROM cliente WHERE dni = ? AND pass = ?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, pass);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    cliente = new ClienteEntity();
                    cliente.setId(rs.getInt("id"));
                    cliente.setDni(rs.getString("dni"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setFecCre(rs.getTimestamp("fec_cre"));
                    cliente.setFecMod(rs.getTimestamp("fec_mod"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setPass(rs.getString("pass"));
                    cliente.setPrimerApellido(rs.getString("primer_apellido"));
                    cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setUsuCre(rs.getString("usu_cre"));
                    cliente.setUsuMod(rs.getString("usu_mod"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el cliente: " + e.getMessage(), e);
        }

        return cliente;
    }

    @Override
    public List<DireccionEntity> obtenerDireccionesPorClienteId(int clienteId) {
        List<DireccionEntity> direcciones = new ArrayList<>();
        String sql = "SELECT * FROM direccion WHERE id_cliente = ?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, clienteId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    DireccionEntity direccion = new DireccionEntity();
                    direccion.setId(rs.getInt("id"));
                    direccion.setCalle(rs.getString("calle"));
                    direccion.setCiudad(rs.getString("ciudad"));
                    direccion.setCp(rs.getString("cp"));
                    direccion.setFecCre(rs.getTimestamp("fec_cre"));
                    direccion.setFecMod(rs.getTimestamp("fec_mod"));
                    direccion.setPais(rs.getString("pais"));
                    direccion.setUsuCre(rs.getString("usu_cre"));
                    direccion.setUsuMod(rs.getString("usu_mod"));
                    direccion.setClienteEntity(new ClienteEntity());
                    direcciones.add(direccion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las direcciones: " + e.getMessage(), e);
        }

        return direcciones;
    }

}
