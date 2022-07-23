package com.simple.blogger.user;

import javax.servlet.http.HttpServletResponse;

import com.simple.blogger.exception.UserAlreadyExistException;
import com.simple.blogger.security.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final Logger logger;

    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(UserController.class);
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletResponse response) {
        try {
            logger.info("creating user : " + user.getUsername());
            boolean returnedUser = userService.createUser(user);
            ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(returnedUser, HttpStatus.OK);
            logger.info("created user : " + user.getUsername());
            return responseEntity;
        } catch (UserAlreadyExistException ex) {
            logger.warn(ex.getMessage());
            ResponseEntity<String> responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
            return responseEntity;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Erorr creating User", HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Server error :(", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
