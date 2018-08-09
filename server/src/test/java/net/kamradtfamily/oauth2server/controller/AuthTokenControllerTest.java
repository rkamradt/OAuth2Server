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

import net.kamradtfamily.oauth2server.controller.IdentityControllerTest.MockTokenDAO;
import net.kamradtfamily.oauth2server.data.TokenDAO;
import net.kamradtfamily.oauth2server.exception.BadRequestException;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.service.AuthTokenService;
import net.kamradtfamily.oauth2server.service.AuthTokenServiceTest;
import net.kamradtfamily.oauth2server.useridserver.ImmutableUserIdResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author randalkamradt
 */
public class AuthTokenControllerTest {
    
    AuthTokenController instance;
    UserIdServer userIdService;
    
    public AuthTokenControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new AuthTokenController();
        userIdService = new AuthTokenServiceTest.MockUserIdServer();
        AuthTokenService authTokenService = new AuthTokenService();
        TokenDAO tokenDao = new MockTokenDAO();
        ReflectionTestUtils.setField(authTokenService, "tokenDao", tokenDao);
        ReflectionTestUtils.setField(authTokenService, "userIdService", userIdService);
        ReflectionTestUtils.setField(instance, "authTokenService", authTokenService);
        ReflectionTestUtils.setField(instance, "clientId", "id1");
        ReflectionTestUtils.setField(instance, "clientSecret", "password");
        userIdService.save(ImmutableUserIdResponse.builder()
            .username("id1")
            .fullname("name")
            .email("email@email.com")
            .build());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRefreshToken method, of class AuthTokenService.
     */
    @Test
    public void testGetRefreshToken() {
        System.out.println("getRefreshToken");
        try {
            instance.getToken("refresh_token", null, null, null, null, null, null, null, null);
            fail("expected operation failed");
        } catch(UnsupportedOperationException ex) {
            
        }
    }

    /**
     * Test of getAuthCodeToken method, of class AuthTokenService.
     */
    @Test
    public void testGetAuthCodeToken() {
        System.out.println("getAuthCodeToken");
        try {
            instance.getToken("authorization_code", null, null, null, null, null, null, null, null);
            fail("expected operation failed");
        } catch(UnsupportedOperationException ex) {
            
        }
    }

    /**
     * Test of getClientCredentialToken method, of class AuthTokenService.
     */
    @Test
    public void testGetClientCredentialToken() {
        System.out.println("getClientCredentialToken");
        UserIdResponse userId = userIdService.findAll().iterator().next();
        String clientId = userId.username();
        String clientSecret = "password";
        String scope = "test";
        AccessTokenResponse response = instance.getToken("client_credentials", null, null, clientId, clientSecret, null, null, scope, null);
        assertNotNull(response);
        assertNotNull(response.access_token());
        assertEquals("bearer", response.token_type());
        assertEquals(3600, response.expires_in());
        assertEquals(scope, response.scope().get());
    }

    /**
     * Test of getClientCredentialToken method, of class AuthTokenService.
     */
    @Test
    public void testGetPasswordToken() {
        System.out.println("getClientCredentialToken");
        UserIdResponse userId = userIdService.findAll().iterator().next();
        String username = userId.username();
        String password = "password";
        String scope = "";
        AccessTokenResponse response = instance.getToken("password", null, null, null, null, username, password, scope, null);
        assertNotNull(response);
        assertNotNull(response.access_token());
        assertEquals("bearer", response.token_type());
        assertEquals(3600, response.expires_in());
//        assertEquals(scope, response.scope());
    }

    /**
     * Test of getAuthCodeToken method, of class AuthTokenService.
     */
    @Test
    public void testGetBadToken() {
        System.out.println("getBadToken");
        try {
            instance.getToken("badtype", null, null, null, null, null, null, null, null);
            fail("expected operation failed");
        } catch(BadRequestException ex) {
            assertEquals("grant_type must be authorization_code, password, client_credentials, or refresh_token", ex.getMessage());
        }
        try {
            instance.getToken(null, null, null, null, null, null, null, null, null);
            fail("expected operation failed");
        } catch(BadRequestException ex) {
            assertEquals("grant_type must be present", ex.getMessage());
        }
    }

    /**
     * Test of authorize method, of class AuthTokenService.
     */
    @Test
    public void testAuthorizeCode() {
        System.out.println("authorizeCode");
        String response_type = "code";
        try {
            instance.authorize(response_type, null, null, null, null);
            fail("expected operation failed");
        } catch(UnsupportedOperationException ex) {
            
        }
    }
    
    /**
     * Test of authorize method, of class AuthTokenService.
     */
    @Test
    public void testAuthorizeToken() {
        System.out.println("authorizeToken");
        String response_type = "token";
        String client_id = "";
        String redirect_uri = "";
        try {
            instance.authorize(response_type, null, null, null, null);
            fail("expected operation failed");
        } catch(UnsupportedOperationException ex) {
            
        }
    }
    
    /**
     * Test of authorize method, of class AuthTokenService.
     */
    @Test
    public void testAuthorizeBad() {
        System.out.println("authorizeToken");
        String response_type = "badtype";
        String client_id = "";
        String redirect_uri = "";
        try {
            instance.authorize(response_type, null, null, null, null);
            fail("expected exception not thrown");
        } catch(BadRequestException ex) {
            assertEquals("response_type must be code or token", ex.getMessage());
        }
        try {
            instance.authorize(null, null, null, null, null);
            fail("expected exception not thrown");
        } catch(BadRequestException ex) {
            assertEquals("respose_type must be present", ex.getMessage());
        }
    }
    
}
