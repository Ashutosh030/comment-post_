package com.myblog.service.impl;

import com.myblog.dto.PostDto;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    //create a bean in config class for modelmapper

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //we cannot DTO to Database, so we create post

       /* Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        // here we cannot return post, we have to return Dto.
       /* PostDto dto = new PostDto();
        dto.setTitle(savedPost.getTitle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());*/

        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found With This Id: " + id)
        );
        // Now convert post object to Dto-
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        //to use sortDir use ternary operation, build if else -
        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) ? Sort.by((sortBy)).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort); //here convert string to sort - by - sort.by()
        Page<Post> pagePost = postRepository.findAll(pageable);

        //convert pagePost to ListPost -  by pagepost.getcontent();
        List<Post> posts = pagePost.getContent();

        //we have list of post and have to convert to dto - so use stream API
        List<PostDto> dtos1 = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return dtos1;
    }

    //create a method to copy  entity to dto
    PostDto mapToDto(Post post) {
        PostDto map = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return map;
    }
    //create a method to copy  dto to entity

    Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
