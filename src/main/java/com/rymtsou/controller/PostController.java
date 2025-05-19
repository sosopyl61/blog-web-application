package com.rymtsou.controller;

import com.rymtsou.model.request.CreatePostRequestDto;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.FindPostsRequestDto;
import com.rymtsou.model.request.UpdatePostRequestDto;
import com.rymtsou.model.response.CreatePostResponseDto;
import com.rymtsou.model.response.GetPostResponseDto;
import com.rymtsou.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody CreatePostRequestDto dto) {
        Optional<CreatePostResponseDto> createdPost = postService.createPost(dto);
        if (createdPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdPost.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponseDto> getPostById(@PathVariable Long id) {
        Optional<GetPostResponseDto> post = postService.getPostById(id);
        if (post.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<GetPostResponseDto>> getPostsByUsername(@RequestBody FindPostsRequestDto dto) {
        List<GetPostResponseDto> posts = postService.getPostsByUsername(dto);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<GetPostResponseDto>> getAllPosts() {
        List<GetPostResponseDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<GetPostResponseDto> updatePost(@RequestBody UpdatePostRequestDto dto) {
        Optional<GetPostResponseDto> updatedPost = postService.updatePost(dto);
        if (updatedPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedPost.get(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deletePost(@RequestBody DeleteByIdRequestDto dto) {
        Boolean result = postService.deletePost(dto);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/countLikes/{id}")
    public ResponseEntity<String> countLikes(@PathVariable Long id) {
        Long likes = postService.getLikesCountById(id);
        return new ResponseEntity<>("Likes: " + likes, HttpStatus.OK);
    }
}
