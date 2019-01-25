//package com.corgo.authentication;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.corgo.DTO.UserDTO;
//import com.corgo.model.User;
//import com.corgo.repository.UserRepository;
//import com.corgo.service.UserService;
//import com.corgo.transformer.UserTransformer;
//
//@Service("userDetailsService")
//public class SimpleUserDetailsService implements UserDetailsService {
//
//    private UserService userService;
//    private UserTransformer userTransformer;
//    private UserRepository userRepository;
// 
//    public SimpleUserDetailsService(UserService userService, UserTransformer userTransformer, UserRepository userRepository) {
//        this.userService = userService;
//        this.userTransformer = userTransformer;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String[] usernameAndDomain = StringUtils.split(username, String.valueOf(Character.LINE_SEPARATOR));
//        if (usernameAndDomain == null || usernameAndDomain.length != 2) {
//            throw new UsernameNotFoundException("Username and domain must be provided");
//        }
//        User user = userRepository.findByUserId("").get();
//        if (user == null) {
//            throw new UsernameNotFoundException(
//                String.format("Username not found for domain, username=%s, domain=%s", 
//                    usernameAndDomain[0], usernameAndDomain[1]));
//        }
//        return (UserDetails) user;
//    }
//}