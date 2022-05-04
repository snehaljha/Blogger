package com.simple.blogger.user;

import com.simple.blogger.exception.UserAlreadyExistException;
import com.simple.blogger.security.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private Encoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, Encoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    
    public boolean createUser(User user) throws Exception {
        if(userRepository.isUserExist(user)) {
            throw new UserAlreadyExistException(user.getUsername());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.registerUser(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user found with username " + username);
        }

        return user;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = null;
        if (principal instanceof User) {
            user = ((User)principal);
        }

        return user;
    }
}
