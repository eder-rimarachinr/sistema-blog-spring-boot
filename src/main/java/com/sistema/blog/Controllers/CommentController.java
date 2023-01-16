package com.sistema.blog.Controllers;

import com.sistema.blog.Models.DTO.CommentDTO;
import com.sistema.blog.Services.CommentService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Api(value = "Post Management", protocols = "http")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/posts/{postID}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentToPost(
            @PathVariable(name = "postID") long postID) {
        return ResponseEntity.ok(commentService.getCommentToPostByID(postID));
    }

    @GetMapping("/post/{postID}/comments/{commentID}")
    public ResponseEntity<CommentDTO> getCommentByID(
            @PathVariable(name = "postID") long postID,
            @PathVariable(name = "commentID") long commentID) {
        CommentDTO commentDTO = commentService.getCommentByID(postID, commentID);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PostMapping("/posts/{postID}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable(name = "postID") Long postID,
            @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.addComment(postID, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postID}/comments/{commentID}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(name = "postID") Long postID,
            @PathVariable(name = "commentID") Long commentID,
            @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO comment = commentService.updateComment(postID, commentID, commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postID}/comments/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postID") Long postID, @PathVariable(name = "commentID") Long commentID) {
        commentService.deleteComment(postID, commentID);
        return new ResponseEntity<>("Safe delete comment successful", HttpStatus.OK);
    }
}
