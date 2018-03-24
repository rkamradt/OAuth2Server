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

import net.kamradtfamily.oauth2server.data.AuthClient;
import net.kamradtfamily.oauth2server.data.AuthClientDAO;
import net.kamradtfamily.oauth2server.exception.EntityNotFoundException;
import net.kamradtfamily.oauth2server.response.AccessTokenResponse;
import net.kamradtfamily.oauth2server.response.IdentityResponse;
import net.kamradtfamily.oauth2server.service.AuthClientServiceTest;
import net.kamradtfamily.oauth2server.service.AuthTokenService;
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
    AuthClientDAO authTokenDAO;
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
        authTokenDAO = new AuthClientServiceTest.MockAuthClientDAO();
        authTokenService = new AuthTokenService();
        ReflectionTestUtils.setField(authTokenService, "authClientDao", authTokenDAO);
        ReflectionTestUtils.setField(instance, "authClientDao", authTokenDAO);
        authTokenDAO.save(new AuthClient("name1"));

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
        AuthClient authClient = authTokenDAO.findAll().iterator().next();
        AccessTokenResponse token = authTokenService.getClientCredentialToken(authClient.getClientId(), authClient.getScope());
        IdentityResponse result = instance.getIdentity(token.access_token());
        assertNotNull(result);
        assertEquals(authClient.getClientId(), result.clientId());
        try {
            instance.getIdentity("badtoken");
            fail("expeceted exception not thrown");
        } catch(EntityNotFoundException ex) {
            assertEquals("token not valid", ex.getMessage());
        }
    }
    
}
