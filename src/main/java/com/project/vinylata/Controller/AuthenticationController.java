package com.project.vinylata.Controller;

import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Exception.UserAlreadyExistsException;
import com.project.vinylata.Exception.UserNotFoundException;
import com.project.vinylata.Repository.UserRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Security.jwt.JWTAuthenticationRequest;
import com.project.vinylata.Security.jwt.JWTService;
import com.project.vinylata.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api")
    public ResponseEntity<Object> Test(){
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED,"ahihi");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        if (authentication.isAuthenticated()){
            return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED,this.jwtService.generateToken(authenticationRequest.getEmail()));
        }else {
            return ResponseHandler.errorResponseBuilder("failure", HttpStatus.BAD_REQUEST, "Invalid user credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto, BindingResult result) throws UserAlreadyExistsException{
        if (result.hasErrors()) {
            return ResponseHandler.responseBuilder("failure", HttpStatus.BAD_REQUEST,userDto);
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
            return ResponseHandler.errorResponseBuilder("failure", HttpStatus.BAD_REQUEST, "User Already Exists Exception");
        }

        userService.add(userDto);

        return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, "register successfully");
    }

}
