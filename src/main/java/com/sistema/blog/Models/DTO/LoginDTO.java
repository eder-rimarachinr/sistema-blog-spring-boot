package com.sistema.blog.Models.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @NotEmpty(message = "El email no debe ser vacío o nulo")
    private String usernameOrEmail;
    @NotEmpty(message = "La contraseña no debe ser vacía o nula")
    private String password;
}
