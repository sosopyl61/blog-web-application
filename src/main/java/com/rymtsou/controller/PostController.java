package com.rymtsou.controller;

import com.rymtsou.model.request.CreatePostRequestDto;
import com.rymtsou.model.request.UpdatePostRequestDto;
import com.rymtsou.model.response.CreatePostResponseDto;
import com.rymtsou.model.response.GetPostResponseDto;
import com.rymtsou.model.response.GetPostsResponseDto;
import com.rymtsou.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create post",
            description = "Creating post with title and main content.")
    @ApiResponses(value = {
            @ApiResponse(description = "Post was created.", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = CreatePostResponseDto.class))),
            @ApiResponse(description = "Post creating was failed.", responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody @Valid CreatePostRequestDto dto) {
        Optional<CreatePostResponseDto> createdPost = postService.createPost(dto);
        if (createdPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdPost.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Get post by id",
            description = "Getting user's post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Post was received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetPostResponseDto.class))),
            @ApiResponse(description = "Post was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponseDto> getPostById(@PathVariable @Parameter(description = "Post's id") Long id) {
        Optional<GetPostResponseDto> post = postService.getPostById(id);
        if (post.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get post by username",
            description = "Getting user's all posts by his/her username.")
    @ApiResponses(value = {
            @ApiResponse(description = "Posts were received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetPostsResponseDto.class)))
    })
    @GetMapping("/find/{username}")
    public ResponseEntity<List<GetPostsResponseDto>> getPostsByUsername(@PathVariable @Parameter(description = "User's username") String username) {
        List<GetPostsResponseDto> posts = postService.getPostsByUsername(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(summary = "Get all posts",
            description = "Getting all user's posts in the app.")
    @ApiResponses(value = {
            @ApiResponse(description = "Posts were received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetPostsResponseDto.class)))
    })
    @GetMapping("/find/all")
    public ResponseEntity<List<GetPostsResponseDto>> getAllPosts() {
        List<GetPostsResponseDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(summary = "Edit post",
            description = "Editing user's post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Post was updated.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetPostResponseDto.class))),
            @ApiResponse(description = "Post updating was failed.", responseCode = "409", content = @Content)
    })
    @PutMapping()
    public ResponseEntity<GetPostResponseDto> updatePost(@RequestBody @Valid UpdatePostRequestDto dto) {
        Optional<GetPostResponseDto> updatedPost = postService.updatePost(dto);
        if (updatedPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedPost.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete post",
            description = "Deleting user's post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Post was deleted.", responseCode = "204", content = @Content),
            @ApiResponse(description = "Post deleting was failed.", responseCode = "409", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable @Parameter(description = "Post's id") Long id) {
        Boolean result = postService.deletePost(id);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Count likes of the post",
            description = "Getting amount of likes of the post by post's id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Likes were received.", responseCode = "200")
    })
    @GetMapping("/countLikes/{id}")
    public ResponseEntity<String> countLikes(@PathVariable @Parameter(description = "Post's id") Long id) {
        Long likes = postService.getLikesCountById(id);
        return new ResponseEntity<>("Likes: " + likes, HttpStatus.OK);
    }
}
