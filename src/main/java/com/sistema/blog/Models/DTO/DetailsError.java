package com.sistema.blog.Models.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class DetailsError {
    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;

    public DetailsError(Date marcaDeTiempo, String mensaje, String detalles) {
        super();
        this.marcaDeTiempo = marcaDeTiempo;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }
}
