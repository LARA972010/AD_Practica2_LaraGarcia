package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDaoImpl;
import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

public class ClienteService {
    private final ClienteDAO clienteDAO = new ClienteDaoImpl();

    public ClienteEntity login(String dni, String password) throws SQLException {
        ClienteEntity cliente = clienteDAO.obtenerClientePorDni(dni, password);
        if (cliente != null) {
            //cargar las direcciones del cliente
            cliente.setDirecciones(clienteDAO.obtenerDireccionesPorClienteId(cliente.getId()));
        }
        return cliente;
    }
}

