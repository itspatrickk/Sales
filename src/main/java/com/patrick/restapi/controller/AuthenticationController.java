package com.patrick.restapi.controller;

import com.patrick.restapi.object.Login;
import com.patrick.restapi.object.RegisterRequest;
import com.patrick.restapi.repository.MstUserRepository;

import com.patrick.restapi.response.CustomApiResponse;
import com.patrick.restapi.service.ApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/auth")
@RequiredArgsConstructor
public class AuthenticationController  extends BasedController{

    private final ApiServiceImpl authenticationServiceImp;
    private final MstUserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){

        CustomApiResponse response = new CustomApiResponse();
        displayObject(new JSONObject(request));
        try {
            if (userRepository.existsByUsername(request.getUsername())){
                response.setMessage("USER ALREADY EXISTS");
                response.setStatus(false);
                response.setReferenceNo(System.currentTimeMillis() / 1000);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> registrationResponse = authenticationServiceImp.registerUser(request);
            return registrationResponse;
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(false);
            response.setReferenceNo(System.currentTimeMillis() / 1000);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

       // return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody Login request){

        CustomApiResponse response = new CustomApiResponse();
        displayObject(new JSONObject(request));
        try {
            if (userRepository.existsByUsername(request.getUsername()) == false){
                response.setMessage("USERNAME NOT  EXISTS");
                response.setStatus(false);
                response.setReferenceNo(System.currentTimeMillis() / 1000);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> registrationResponse = authenticationServiceImp.login(request);
            return registrationResponse;
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(false);
            response.setReferenceNo(System.currentTimeMillis() / 1000);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
