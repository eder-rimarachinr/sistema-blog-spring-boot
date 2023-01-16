package com.sistema.blog.Models.DTO;

import com.sistema.blog.Models.Entity.Comment;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2, message = "El titulo de la  publicación debería tener almenos 2 carácteres.")
    private String titulo;
    @NotEmpty
    @Size(min = 10, message = "La descricion de la  publicación debería tener almenos 10 carácteres.")
    private String descripcion;
    @NotEmpty
    private String contenido;
    private Set<Comment> comentarios;

    public PostDTO() {
        super();
    }

}
