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

import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.service.AuthTokenService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author randalkamradt
 */
public class AuthTokenControllerTest {
    
    @Autowired
    private AuthTokenController instance;

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
        ReflectionTestUtils.setField(instance, "authTokenService", new AuthTokenService());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getToken method, of class AuthTokenController.
     */
    @Test
    public void testGetToken() {
        System.out.println("getToken");
        String grant_type = "authorization_code";
        String code = "";
        String redirect_uri = "";
        String client_id = "";
        String client_secret = "";
        String username = "";
        String password = "";
        String scope = "";
        String refresh_token = "";
        AccessTokenResponse response = instance.getToken(grant_type, code, redirect_uri, client_id, client_secret, username, password, scope, refresh_token);
        assertNotNull(response);
        assertNotNull(response.access_token());
        assertEquals("bearer", response.token_type());
        assertEquals(3600, response.expires_in());
    }

    /**
     * Test of authorize method, of class AuthTokenController.
     */
    @Test
    public void testAuthorize() {
        System.out.println("authorize");
        String response_type = "code";
        String client_id = "";
        String redirect_uri = "";
        String scope = "";
        String state = "";
        instance.authorize(response_type, client_id, redirect_uri, scope, state);
    }
    
}
