package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {

    private Integer id;
    private String dni;
    private String email;
    private Timestamp fecCre;
    private Timestamp fecMod;
    private String nombre;
    private String pass;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String usuCre;
    private String usuMod;
    private List<DireccionEntity> direcciones;

}