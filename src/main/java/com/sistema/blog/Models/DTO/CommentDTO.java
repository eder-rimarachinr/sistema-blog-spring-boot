package com.sistema.blog.Models.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {

    private long id;

    @NotEmpty(message = "Nombre no debe ser vacío o nulo")
    private String nombre;
    @NotEmpty(message = "El email no debe ser vacío o nulo")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 1, message = "El cuerpo del comentario debe tener almenos 1 carácter")
    private String cuerpo;
}
