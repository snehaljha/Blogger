package com.simple.blogger;

import com.simple.blogger.user.User;
import com.simple.blogger.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<String> afterLogin() {
        User loggedUser = userService.getCurrentUser();
        if(loggedUser != null) {
            return new ResponseEntity<>("Hi " + loggedUser.getName() + "/" + loggedUser.getUsername(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Unknown User", HttpStatus.UNAUTHORIZED);
    }
}
