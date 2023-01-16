package com.sistema.blog.Services;

import com.sistema.blog.Models.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO addComment(long id, CommentDTO commentDTO);

    List<CommentDTO> getCommentToPostByID(long postId);

    CommentDTO getCommentByID(long postID, long commentID);

    CommentDTO updateComment(Long postID, Long commentID, CommentDTO commentDTO);

    void deleteComment(Long postID, Long commentID);
}
