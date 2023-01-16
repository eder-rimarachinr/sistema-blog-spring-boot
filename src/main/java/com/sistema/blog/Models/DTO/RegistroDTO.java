package com.sistema.blog.Models.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistroDTO {
    @NotEmpty(message = "El nombre no debe ser vacío o nulo")
    private String nombre;

    @NotEmpty(message = "El username no debe ser vacío o nulo")
    @Size(min = 6, message = "El username debe contener almenos 6 carácteres")
    private String username;
    @NotEmpty(message = "El email no debe ser vacío o nulo")
    private String email;

    @NotEmpty(message = "El password no debe ser vacío o nulo")
    @Size(min = 6, message = "El password debe contener almenos 6 carácteres")
    private String password;


}
