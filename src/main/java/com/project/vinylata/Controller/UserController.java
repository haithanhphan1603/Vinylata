package com.project.vinylata.Controller;

import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Exception.UserNotFoundException;
import com.project.vinylata.Repository.UserRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseHandler.responseBuilder("success", HttpStatus.FOUND, this.userService.getAllUsers());
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody UserDto userDto){
        userService.add(userDto);
        return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, "added new user successfully");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable("email") String email){
        if (userRepository.findByEmail(email).isEmpty()){
            throw new UserNotFoundException("user not found");
        }
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseHandler.responseBuilder("success", HttpStatus.FOUND, userDto);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> delete(@PathVariable("email") String email){
        userService.delete(email);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, "User has been deleted");
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> update(@PathVariable("email") String email, @RequestBody UserDto userDto){
        userService.update(email , userDto);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, "User has been deleted");
    }

}
