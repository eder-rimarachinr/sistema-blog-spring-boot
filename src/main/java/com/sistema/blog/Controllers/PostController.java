package com.sistema.blog.Controllers;

import com.sistema.blog.Models.DTO.PostDTO;
import com.sistema.blog.Models.DTO.PostResponse;
import com.sistema.blog.Services.PostService;
import com.sistema.blog.Utils.AppConstans;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
@Api(value = "Post Management", protocols = "http")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNum", defaultValue = AppConstans.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
            @RequestParam(name = "pageSize", defaultValue = AppConstans.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int tamanoPagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstans.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sorDir", defaultValue = AppConstans.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sorDir) {
        return new ResponseEntity<>(postService.getAllPost(numeroPagina, tamanoPagina, ordenarPor, sorDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<PostDTO> addPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.addPost(postDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @PathVariable(name = "id") Long id, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(id, postDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post eliminado con exito", HttpStatus.OK);
    }
}
