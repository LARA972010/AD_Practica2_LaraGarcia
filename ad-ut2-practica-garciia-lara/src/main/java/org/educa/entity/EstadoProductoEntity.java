package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoProductoEntity {
    private long id;
    private String nombre;
    private String usuCre;
    private LocalDate fecCre;
    private String usuMod;
    private LocalDate fecMod;
}
