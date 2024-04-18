package com.myblog.controller;

import com.myblog.dto.CommentDto;
import com.myblog.service.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comment?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment (
            @RequestBody CommentDto commentDto,
            @RequestParam long postId){

        CommentDto dto = commentService.createComment(commentDto, postId);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/comment/3
    @DeleteMapping ("/{id}") // give the id as a path parameter
    public ResponseEntity<String> deleteComment (@PathVariable long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>("Comment is deleted..!", HttpStatus.OK);
    }
    @PutMapping("/{id}/post/{postId}")
    public ResponseEntity<CommentDto> updateComment (@PathVariable long id,@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto dto = commentService.updateComment(id, commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

}
