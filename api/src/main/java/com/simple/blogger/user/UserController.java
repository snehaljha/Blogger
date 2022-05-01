package com.simple.blogger.user;

import com.simple.blogger.exception.UserAlreadyExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(UserController.class);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
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

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
        username = ((UserDetails)principal).getUsername();
        } else {
        username = principal.toString();
        }

        return username;
    }
}
