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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.kamradtfamily.oauth2server.data.Token;
import net.kamradtfamily.oauth2server.data.TokenDAO;
import net.kamradtfamily.oauth2server.exception.EntityNotFoundException;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.response.IdentityResponse;
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
public class IdentityControllerTest {
    IdentityController instance;
    UserIdServer userIdService;
    AuthTokenService authTokenService;

    public IdentityControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new IdentityController();
        userIdService = new AuthTokenServiceTest.MockUserIdServer();
        authTokenService = new AuthTokenService();
        TokenDAO tokenDao = new MockTokenDAO();
        ReflectionTestUtils.setField(authTokenService, "tokenDao", tokenDao);
        ReflectionTestUtils.setField(authTokenService, "userIdService", userIdService);
        ReflectionTestUtils.setField(instance, "tokenDao", tokenDao);
        ReflectionTestUtils.setField(instance, "userIdService", userIdService);
        userIdService.save(ImmutableUserIdResponse.builder()
            .id("id1")
            .clientId("clientId")
            .clientSecret("clientSecret")
            .name("name")
            .scope("scope")
            .build());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getIdentity method, of class IdentityController.
     */
    @Test
    public void testGetIdentity() {
        System.out.println("getIdentity");
        UserIdResponse userId = userIdService.findAll().iterator().next();
        AccessTokenResponse token = authTokenService.getClientCredentialToken(userId.clientId(), userId.scope());
        IdentityResponse result = instance.getIdentity(token.access_token());
        assertNotNull(result);
        assertEquals(userId.clientId(), result.clientId());
        try {
            instance.getIdentity("badtoken");
            fail("expeceted exception not thrown");
        } catch(EntityNotFoundException ex) {
            assertEquals("token not found", ex.getMessage());
        }
    }
    public static class MockTokenDAO extends TokenDAO {
        Map<String, Token> data = new HashMap<>();
        public MockTokenDAO() {
        }
        @Override
        public Optional<Token> findById(String id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public Iterable<Token> findAll() {
            return data.values();
        }

        @Override
        public Token save(Token token) {
            data.put(token.getId(), token);
            return token;
        }

        @Override
        public void deleteById(String id) {
            data.remove(id);
        }

    }
    
}
   
