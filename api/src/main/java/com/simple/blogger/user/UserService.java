package com.simple.blogger.user;

import com.simple.blogger.exception.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public boolean createUser(User user) throws Exception {
        if(userRepository.isUserExist(user)) {
            throw new UserAlreadyExistException(user.getUsername());
        }

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
