package com.sistema.blog.Models.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDTO> contenido;
    private int numeroPagina;
    private int tamanoPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public PostResponse() {
    }
}
