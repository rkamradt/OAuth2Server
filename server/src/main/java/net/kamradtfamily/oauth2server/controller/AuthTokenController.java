/*
 * The MIT License
 *
 * Copyright 2018 randalkamradt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.kamradtfamily.oauth2server.controller;

import javax.servlet.http.HttpServletRequest;
import net.kamradtfamily.oauth2server.exception.BadRequestException;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import net.kamradtfamily.oauth2server.exception.ForbiddenException;

/**
 *
 * @author randalkamradt
 */
@RestController
public class AuthTokenController {
    @Autowired
    private AuthTokenService authTokenService;

    @PostMapping(value="/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AccessTokenResponse getToken(String grant_type, 
            String code, 
            String redirect_uri, 
            String client_id, 
            String username, 
            String password, 
            String scope, 
            String refresh_token,
            HttpServletRequest request) {
        if(null == grant_type) {
            throw new BadRequestException("grant_type must be present");
        }
        switch (grant_type) {
            case "authorization_code":
                return authTokenService.getAuthCodeToken(code, redirect_uri, client_id);
            case "password":
                return authTokenService.getPasswordToken(username, password, Optional.ofNullable(scope));
            case "refresh_token":
                return authTokenService.getRefreshToken(refresh_token, Optional.ofNullable(scope));
            case "client_credentials":
                if(request.getUserPrincipal() == null) {
                    throw new ForbiddenException("forbidden");
                }
                return authTokenService.getClientCredentialToken(request.getUserPrincipal().getName(), Optional.ofNullable(scope));
            default:
                throw new BadRequestException("grant_type must be authorization_code, password, client_credentials, or refresh_token");
        }
    }
    
    @GetMapping(value="/authorize")
    public void authorize(@RequestParam String response_type, 
            @RequestParam String client_id, 
            @RequestParam(required=false) String redirect_uri,
            @RequestParam(required=false) String scope,
            @RequestParam(required=false) String state) {
        if(null == response_type) {
            throw new BadRequestException("respose_type must be present");
        }
        switch (response_type) {
            case "code":
                authTokenService.authorize(response_type, client_id, redirect_uri);
                break;
            case "token":
                authTokenService.authorize(response_type, client_id, redirect_uri);
                break;
            default:
                throw new BadRequestException("response_type must be code or token");
        }
    }
    
}
