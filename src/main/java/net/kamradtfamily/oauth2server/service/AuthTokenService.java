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
package net.kamradtfamily.oauth2server.service;

import java.util.UUID;
import net.kamradtfamily.oauth2server.data.AuthClientRepository;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.response.ImmutableAccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author randalkamradt
 */
@Component
public class AuthTokenService {

    @Autowired
    private AuthClientRepository authClientRepository;
    /**
     * 
     * when grant_type is refresh_token, this is called
     * 
     * as described in RFC 6749 section 6
     * 
     * @param refreshToken 
     * @param scope 
     * @return  
     */
    public AccessTokenResponse getRefreshToken(String refreshToken, String scope) {
        // todo store access token
        return ImmutableAccessTokenResponse.builder()
                .access_token(UUID.randomUUID().toString())
                .expires_in(3600)
                .token_type("bearer")
                .scope(scope)
                .build();
    }
    /**
     * 
     * when grant_type is authorization_code, this is called
     * 
     * as described in RFC 6749 section 4.1.3
     * 
     * @param code
     * @param redirectUri
     * @param clientId
     * @return 
     */
    public AccessTokenResponse getAuthCodeToken(String code, String redirectUri, String clientId) {
        // todo store access token
        return ImmutableAccessTokenResponse.builder()
                .access_token(UUID.randomUUID().toString())
                .expires_in(3600)
                .token_type("bearer")
                .scope("all")
                .build();
    }

    /**
     * 
     * when grant_type is password, this is called
     * 
     * as described in RFC 6749 section 4.3.2
     * 
     * @param scope
     * @return 
     */
    public AccessTokenResponse getPasswordToken(String scope) {
        // todo store access token
        return ImmutableAccessTokenResponse.builder()
                .access_token(UUID.randomUUID().toString())
                .expires_in(3600)
                .token_type("bearer")
                .scope(scope)
                .build();
    }

    /**
     * 
     * when grant_type is client_credential, this is called
     * 
     * as described in RFC 6749 section 4.3.2
     * 
     * @param scope
     * @return 
     */
    public AccessTokenResponse getClientCredentialToken(String scope) {
        // todo store access token
        return ImmutableAccessTokenResponse.builder()
                .access_token(UUID.randomUUID().toString())
                .expires_in(3600)
                .token_type("bearer")
                .scope(scope)
                .build();
    }

    public void authorize(String response_type, String client_id, String redirect_uri) {
    }

}
