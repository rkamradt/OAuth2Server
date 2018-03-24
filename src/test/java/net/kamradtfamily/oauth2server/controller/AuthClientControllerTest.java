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

import java.util.List;
import net.kamradtfamily.oauth2server.data.AuthClient;
import net.kamradtfamily.oauth2server.data.AuthClientDAO;
import net.kamradtfamily.oauth2server.request.AuthClientRequest;
import net.kamradtfamily.oauth2server.request.ImmutableAuthClientRequest;
import net.kamradtfamily.oauth2server.response.AuthClientResponse;
import net.kamradtfamily.oauth2server.service.AuthClientService;
import net.kamradtfamily.oauth2server.service.AuthClientServiceTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author randalkamradt
 */
public class AuthClientControllerTest {
    AuthClientController instance;
    AuthClientService authClientService;
    public AuthClientControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new AuthClientController();
        authClientService = new AuthClientService();
        AuthClientDAO authClientDao = new AuthClientServiceTest.MockAuthClientDAO();
        ReflectionTestUtils.setField(authClientService, "authClientDao", authClientDao);
        ReflectionTestUtils.setField(instance, "authClientService", authClientService);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getClientById method, of class AuthClientController.
     */
    @Test
    public void testGetClientById() {
        System.out.println("getClientById");
        String name = "name";
        AuthClient authClient = authClientService.addAuthClient(new AuthClient(name));
        ResponseEntity<AuthClientResponse> response = instance.getClientById(authClient.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthClientResponse result = response.getBody();
        assertNotNull(result);
        assertEquals(name, result.name());
        assertNotNull(result.clientId());
        assertNotNull(result.clientSecret());
        assertNotNull(result.id());
    }

    /**
     * Test of getAllClients method, of class AuthClientController.
     */
    @Test
    public void testGetAllClients() {
        System.out.println("getAllClients");
        String name1 = "name1";
        String name2 = "name2";
        authClientService.addAuthClient(new AuthClient(name1));
        authClientService.addAuthClient(new AuthClient(name2));
        ResponseEntity<List<AuthClientResponse>> response = instance.getAllClients();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AuthClientResponse> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /**
     * Test of addClient method, of class AuthClientController.
     */
    @Test
    public void testAddClient() {
        System.out.println("addClient");
        String name = "name";
        AuthClientRequest req = ImmutableAuthClientRequest.builder()
                .name(name)
                .build();
        ResponseEntity<AuthClientResponse> response = instance.addClient(req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthClientResponse result = response.getBody();
        assertNotNull(result);
        assertEquals(name, result.name());
        assertNotNull(result.clientId());
        assertNotNull(result.clientSecret());
        assertNotNull(result.id());
    }

    /**
     * Test of deleteClient method, of class AuthClientController.
     */
    @Test
    public void testDeleteClient() {
        System.out.println("deleteClient");
        String name = "name";
        AuthClientRequest req = ImmutableAuthClientRequest.builder()
                .name(name)
                .build();
        ResponseEntity<AuthClientResponse> resp = instance.addClient(req);
        AuthClientResponse res = resp.getBody();
        instance.deleteClient(res.id());
        ResponseEntity<List<AuthClientResponse>> response = instance.getAllClients();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AuthClientResponse> result = response.getBody();
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
}
