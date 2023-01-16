package com.sistema.blog.Services.Imp;

import com.sistema.blog.Exceptions.BlogAppException;
import com.sistema.blog.Exceptions.ResourceNotFoundException;
import com.sistema.blog.Models.DTO.CommentDTO;
import com.sistema.blog.Models.Entity.Comment;
import com.sistema.blog.Models.Entity.Post;
import com.sistema.blog.Repository.CommentRepository;
import com.sistema.blog.Repository.PostRepository;
import com.sistema.blog.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public CommentDTO addComment(long id, CommentDTO commentDTO) {

        Comment comment = mapperToEntity(commentDTO);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado", "id", id));

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapperToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentToPostByID(long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream()
                .map(this::mapperToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentByID(long postID, long commentID) {
        Comment comment = SearchCommentToPost(postID, commentID, postRepository, commentRepository);
        return mapperToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postID, Long commentID, CommentDTO commentDTO) {
        Comment comment = SearchCommentToPost(postID, commentID, postRepository, commentRepository);

        comment.setNombre(commentDTO.getNombre());
        comment.setEmail(commentDTO.getEmail());
        comment.setCuerpo(commentDTO.getCuerpo());

        Comment updateComment = commentRepository.save(comment);

        return mapperToDTO(updateComment);
    }

    @Override
    public void deleteComment(Long postID, Long commentID) {
        Comment comment = SearchCommentToPost(postID, commentID, postRepository, commentRepository);
        commentRepository.delete(comment);
    }

    private static Comment SearchCommentToPost(Long postID, Long commentID, PostRepository postRepository, CommentRepository commentRepository) {
        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));
        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentID));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        return comment;
    }

    private Comment mapperToEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    private CommentDTO mapperToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

}
