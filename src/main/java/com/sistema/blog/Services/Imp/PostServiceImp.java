package com.sistema.blog.Services.Imp;

import com.sistema.blog.Exceptions.ResourceNotFoundException;
import com.sistema.blog.Models.DTO.PostDTO;
import com.sistema.blog.Models.DTO.PostResponse;
import com.sistema.blog.Models.Entity.Post;
import com.sistema.blog.Repository.PostRepository;
import com.sistema.blog.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImp(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO addPost(PostDTO postDTO) {
        //Convertirmos de DTO a entidad
        Post post = mapToEntity(postDTO);

        // Guardamos la data
        Post newPost = postRepository.save(post);

        // Convertimos de Entidad a DTO
        //PostDTO postResponse = mapToDTO.map(newPost);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPost(int numeroPagina, int tamanoPagina, String ordenarPor, String sorDir) {
        // Indicamos el orden
        Sort sort = sorDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending() : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroPagina, tamanoPagina, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDTO> contenido = postList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContenido(contenido);
        postResponse.setNumeroPagina(posts.getNumber());
        postResponse.setTamanoPagina(posts.getSize());
        postResponse.setTotalElementos(posts.getTotalElements());
        postResponse.setTotalPaginas(posts.getTotalPages());
        postResponse.setUltima(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado", "id", id));

        post.setTitulo(postDTO.getTitulo());
        post.setDescripcion(postDTO.getDescripcion());
        post.setContenido(postDTO.getContenido());

        Post postUpdated = postRepository.save(post);
        return mapToDTO(postUpdated);

    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado", "id", id));
        postRepository.delete(post);
    }

    private Post mapToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    private PostDTO mapToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
