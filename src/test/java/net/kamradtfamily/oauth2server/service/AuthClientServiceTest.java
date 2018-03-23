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
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.kamradtfamily.oauth2server.data.AuthClient;
import net.kamradtfamily.oauth2server.data.AuthClientDAO;
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
public class AuthClientServiceTest {
    
    AuthClientService instance;
    
    
    public AuthClientServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new AuthClientService();
        ReflectionTestUtils.setField(instance, "authClientRepository", new MockAuthClientDAO());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of authClientById method, of class AuthClientService.
     */
    @Test
    public void testAuthClientById() {
        System.out.println("authClientById");
        String name = "name";
        AuthClient authClient = instance.addAuthClient(new AuthClient(name));
        Optional<AuthClient> optional = instance.authClientById(authClient.getId());
        assertTrue(optional.isPresent());
        AuthClient result = optional.get();
        assertEquals(name, result.getName());
        assertNotNull(result.getClientId());
        assertNotNull(result.getClientSecret());
        assertNotNull(result.getId());
    }

    /**
     * Test of allAuthClients method, of class AuthClientService.
     */
    @Test
    public void testAllAuthClients() {
        System.out.println("allAuthClients");
        String name1 = "name1";
        String name2 = "name2";
        instance.addAuthClient(new AuthClient(name1));
        instance.addAuthClient(new AuthClient(name2));
        List<AuthClient> result = StreamSupport.stream(instance.allAuthClients().spliterator(), true)
                .collect(Collectors.toList());
        assertEquals(2,result.size());
    }

    /**
     * Test of addAuthClient method, of class AuthClientService.
     */
    @Test
    public void testAddAuthClient() {
        System.out.println("addAuthClient");
        String name = "name";
        AuthClient authClient = instance.addAuthClient(new AuthClient(name));
        assertEquals(name, authClient.getName());
        assertNotNull(authClient.getClientId());
        assertNotNull(authClient.getClientSecret());
        assertNotNull(authClient.getId());
    }

    /**
     * Test of deleteAuthClient method, of class AuthClientService.
     */
    @Test
    public void testDeleteAuthClient() {
        System.out.println("deleteAuthClient");
        String name = "name";
        AuthClient authClient = instance.addAuthClient(new AuthClient(name));
        instance.deleteAuthClient(authClient.getId());
        Optional<AuthClient> optional = instance.authClientById(authClient.getId());
        assertTrue(!optional.isPresent());
        List<AuthClient> result = StreamSupport.stream(instance.allAuthClients().spliterator(), true)
                .collect(Collectors.toList());
        assertEquals(0,result.size());
    }

    public static class MockAuthClientDAO extends AuthClientDAO {
        Map<String, AuthClient> data = new HashMap<>();
        public MockAuthClientDAO() {
        }
        @Override
        public Optional<AuthClient> findById(String id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public Iterable<AuthClient> findAll() {
            return data.values();
        }

        @Override
        public AuthClient save(AuthClient authClient) {
            data.put(authClient.getId(), authClient);
            return authClient;
        }

        @Override
        public void deleteById(String id) {
            data.remove(id);
        }

    }
    
}
