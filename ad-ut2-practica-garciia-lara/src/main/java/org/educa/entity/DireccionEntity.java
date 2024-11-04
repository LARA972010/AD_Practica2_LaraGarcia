package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEntity {
    private Integer id;
    private String calle;
    private String ciudad;
    private String cp;
    private Timestamp fecCre;
    private Timestamp fecMod;
    private String pais;
    private String usuCre;
    private String usuMod;
    private ClienteEntity clienteEntity;

}