package com.myblog.controller;

import com.myblog.dto.LoginDto;
import com.myblog.dto.SignUpDto;
import com.myblog.entity.Role;
import com.myblog.entity.User;
import com.myblog.repository.RoleRepository;
import com.myblog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
   private RoleRepository roleRepository;

   @Autowired
   private AuthenticationManager authenticationManager;

    /*public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }*/

    @PostMapping ("/signin") //(Recorded class ->09/02/24 - 32.00)
        public ResponseEntity <String> authenticateUser (@RequestBody LoginDto loginDto){
       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
               UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());

       Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

       SecurityContextHolder.getContext().setAuthentication(authenticate);
       return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
   }


@PostMapping("/signup")
    //create method for register user->
    public ResponseEntity<?> registerUser (@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByusername(signUpDto.getUsername())){
            return new ResponseEntity<>("User already exists by this name:", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exists..!!",HttpStatus.BAD_REQUEST);
        }
        //if both statement is false, then register user in DB->

        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName(signUpDto.getRoleType()).get();
        Set<Role> convertRoleToSet = new HashSet<>();
        convertRoleToSet.add(roles);


        userRepository.save(user);
        return new ResponseEntity<>("User Register Successfully", HttpStatus.OK);
    }

}