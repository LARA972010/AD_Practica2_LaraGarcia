package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.DireccionEntity;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    ClienteEntity obtenerClientePorDni(String dni, String pass) throws SQLException;
    List<DireccionEntity> obtenerDireccionesPorClienteId(int clienteId);
}
