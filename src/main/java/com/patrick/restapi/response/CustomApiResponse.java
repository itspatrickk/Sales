package com.patrick.restapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse {

    private String message;
    private Boolean status;
    private Long referenceNo;
    private String token;
    private String username;
    private String email;
    private Long Id;
    private List<String> roles;
    private String tokenType;

}
