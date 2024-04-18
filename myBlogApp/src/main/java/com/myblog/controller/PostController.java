package com.myblog.controller;

import com.myblog.dto.PostDto;
import com.myblog.service.impl.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity <PostDto> createPost (@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/all?id=1
    @GetMapping ("/all")
    public ResponseEntity <PostDto> getPostById (@RequestParam long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/post?pageNo?&pageSize=3&shortBy=title&sortDir=des
    //for pagination we have to read the data from postman - so use @RequestParam
    //then supply pageNo, and pageSize and refactor the code.
    //to shortBy use third parameter @requestParam shortBy - here we give "id".
    @GetMapping
    public List<PostDto> getAllPost (
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "id") String sortDir
    ){
        List<PostDto> postDtos = postService.getAllPost(pageNo, pageSize, sortBy,sortDir);
        return postDtos;
    }
}
