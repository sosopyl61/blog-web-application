package com.rymtsou.controller;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;
import com.rymtsou.model.response.GetUsersResponseDto;
import com.rymtsou.service.UserService;
import com.rymtsou.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by id",
            description = "Getting user by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(description = "User was received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Parameter(description = "User's id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get user by username",
            description = "Getting user by his/her username.")
    @ApiResponses(value = {
            @ApiResponse(description = "User was received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetUserResponseDto.class))),
            @ApiResponse(description = "User was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/find/{username}")
    public ResponseEntity<GetUserResponseDto> getUserByUsername(@PathVariable @Parameter(description = "User's username") String username) {
        Optional<GetUserResponseDto> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Operation(summary = "Find all users",
            description = "Getting all users in the app.")
    @ApiResponses(value = {
            @ApiResponse(description = "Users were received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetUsersResponseDto.class))),
            @ApiResponse(description = "Users were not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/find/all")
    public ResponseEntity<List<GetUsersResponseDto>> getAllUsers() {
        Optional<List<GetUsersResponseDto>> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @Operation(summary = "Edit user",
            description = "Editing user information by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(description = "User was updated.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetUsersResponseDto.class))),
            @ApiResponse(description = "Users updating was failed.", responseCode = "409", content = @Content)
    })
    @PutMapping
    public ResponseEntity<GetUsersResponseDto> updateUser(@RequestBody @Valid UpdateUserRequestDto dto) {
        Optional<GetUsersResponseDto> userResponse = userService.updateUser(dto);
        if (userResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userResponse.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete user",
            description = "Deleting user by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(description = "User was deleted.", responseCode = "204", content = @Content),
            @ApiResponse(description = "Users deleting was failed.", responseCode = "409", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable @Parameter(description = "User's id.") Long id) {
        Boolean result = userService.deleteUser(id);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
