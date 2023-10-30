package com.example.eventOrganizer.Security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user details from your data source (e.g., database)
        // Replace this with your actual user retrieval logic
        // For simplicity, we're returning a hard-coded user
        if (username.equals("hanzala")) {
            return new User("hanzala", "root", new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User Not Found Exception !");
        }
    }
}
