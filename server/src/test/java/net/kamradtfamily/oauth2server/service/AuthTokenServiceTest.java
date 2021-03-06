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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kamradtfamily.oauth2server.controller.IdentityControllerTest.MockTokenDAO;
import net.kamradtfamily.oauth2server.data.TokenDAO;
import net.kamradtfamily.oauth2server.exception.BadRequestException;
import net.kamradtfamily.oauth2server.exception.EntityNotFoundException;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.useridserver.ImmutableUserIdResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author randalkamradt
 */
public class AuthTokenServiceTest {
    
    private AuthTokenService instance;
    private UserIdServer userIdService;

    public AuthTokenServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new AuthTokenService();
        userIdService = new MockUserIdServer();
        TokenDAO tokenDao = new MockTokenDAO();
        ReflectionTestUtils.setField(instance, "tokenDao", tokenDao);
        ReflectionTestUtils.setField(instance, "userIdService", userIdService);
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
        String refreshToken = "";
        String scope = "scope";
        try {
            instance.getRefreshToken(refreshToken, Optional.empty());
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
        String code = "";
        String redirectUri = "";
        String clientId = "";
        try {
            instance.getAuthCodeToken(code, redirectUri, clientId);
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
        AccessTokenResponse response = instance.getClientCredentialToken(clientId, Optional.empty());
        assertNotNull(response);
        assertNotNull(response.access_token());
        assertEquals("bearer", response.token_type());
        assertEquals(3600, response.expires_in());
        assertTrue(!response.scope().isPresent());
        try {
            instance.getClientCredentialToken(null, null);
            fail("expected exception not thrown");
        } catch(BadRequestException ex) {
            assertEquals("client_id must be present", ex.getMessage());
        }
        try {
            instance.getClientCredentialToken("badclientid", null);
            fail("expected exception not thrown");
        } catch(EntityNotFoundException ex) {
            assertEquals("badclientid", ex.getMessage());
        }
    }

    /**
     * Test of authorize method, of class AuthTokenService.
     */
    @Test
    public void testAuthorize() {
        System.out.println("authorize");
        String response_type = "";
        String client_id = "";
        String redirect_uri = "";
        try {
            instance.authorize(response_type, client_id, redirect_uri);
            fail("expected operation failed");
        } catch(UnsupportedOperationException ex) {
            
        }
    }
    public static class MockUserIdServer implements UserIdServer {
        Map<String, UserIdResponse> data = new HashMap<>();
        public MockUserIdServer() {
        }
        @Override
        public Optional<UserIdResponse> getUserId(String id) {
            if(data.containsKey(id)) {
                return Optional.of(data.get(id));
            }
            return Optional.empty();
        }

        @Override
        public Optional<UserIdResponse> confirm(String userId, String password) {
            return getUserId(userId);
        }

        @Override
        public void save(UserIdResponse user) {
            data.put(user.username(), user);
        }

        @Override
        public List<UserIdResponse> findAll() {
            return data.values().stream().collect(Collectors.toList());
        }

    }
    
}
