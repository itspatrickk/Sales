package com.patrick.restapi.service;


import com.patrick.restapi.entity.*;
import com.patrick.restapi.object.Login;
import com.patrick.restapi.object.RegisterRequest;
import com.patrick.restapi.repository.MstRoleRepository;
import com.patrick.restapi.repository.MstUserRepository;
import com.patrick.restapi.response.CustomApiResponse;
import com.patrick.restapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl {
    private final PasswordEncoder encoder;
    private final MstRoleRepository mstRole;
    private final MstUserRepository mstUser;
    private final AuthenticationManager authenticationManager;

    private  final JwtTokenProvider utils;

    public ResponseEntity<Object> registerUser(RegisterRequest request) {
        CustomApiResponse response = new CustomApiResponse();
        MstUserEntity user = new MstUserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));

        MstRoleEntity role = mstRole.findByRoleAccess(request.getRole()).orElseThrow(null);
        if (role == null)
        {
            // Handle the case where the "USER" role doesn't exist
            response.setMessage("Role 'USER' not found");
            response.setStatus(false);
            response.setReferenceNo(System.currentTimeMillis() / 1000);
            //response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setRoles(Collections.singletonList(role));
        mstUser.save(user);
       // String token = utils.generateToken(user);
        response.setMessage("USER REGISTERED SUCCESS");
        response.setStatus(true);
        response.setReferenceNo(System.currentTimeMillis() / 1000);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> login(Login request) {
        CustomApiResponse response = new CustomApiResponse();
        try {
            System.out.println("-------SIGN-IN");

            // Authenticate user
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Get UserDetails from authentication
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // Generate token
            String token = utils.generateToken(userDetails);

            // Set response
            response.setMessage("Login successful");
            response.setToken(token);
            response.setTokenType("Bearer ");
            response.setStatus(true);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Authentication failed
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(false);
            response.setReferenceNo(System.currentTimeMillis() / 1000);
            return new ResponseEntity<Object>(response , HttpStatus.NOT_FOUND);
        }
    }

}
