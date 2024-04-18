package com.myblog.service.impl;

import com.myblog.dto.CommentDto;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    //constructor base injection-

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found with Id:" + postId)
        );
        //if post is found , copy the content from DTo to comment object. Dto to Entity

        Comment comment = new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());

        //now to set the comment for perticullar post? - >

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        //convert the savedComment to CommentDto->

        CommentDto dto = new CommentDto();

        dto.setId(savedComment.getId());
        dto.setEmail(savedComment.getEmail());
        dto.setText(savedComment.getText());
        dto.setQuotation("Completed!!");
        return dto;

    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found By this ID..!:" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found by id.!:" + id)
        );
        //Take the Dto and Copy back to Comment - >
        Comment c = mapToEntity(commentDto);

        c.setId(comment.getId());
        c.setPost(post);
        Comment savedComment = commentRepository.save(c);

        return maptoDto(savedComment);

    }

    //entity to dto
    CommentDto maptoDto (Comment comment){
        CommentDto map = modelMapper.map(comment, CommentDto.class);
        return map;
    }

    //dto to entity ->
    Comment mapToEntity (CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return  comment;
    }
}
