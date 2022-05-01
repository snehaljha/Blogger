package com.simple.blogger.user;

import java.util.List;

import com.simple.blogger.exception.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private List<Character> tokenChars;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

        for(int i=0; i<26; i++) {
            tokenChars.add((char)('a' + i));
            tokenChars.add((char)('A' + i));
            if(i<10) {
                tokenChars.add((char)('0'+i));
            }
        }
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
}
