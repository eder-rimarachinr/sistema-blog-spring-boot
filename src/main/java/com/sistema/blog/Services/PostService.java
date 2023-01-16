package com.sistema.blog.Services;

import com.sistema.blog.Models.DTO.PostDTO;
import com.sistema.blog.Models.DTO.PostResponse;


public interface PostService {

    PostDTO addPost(PostDTO postDTO);

    PostResponse getAllPost(int numeroPagina, int tamanoPagina, String ordenarPor, String sorDir);

    PostDTO getPostById(Long id);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(Long id);

}
