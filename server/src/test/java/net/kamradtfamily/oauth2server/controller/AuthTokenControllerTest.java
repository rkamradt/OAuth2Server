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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
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
        String clientId = userId.clientId();
        String scope = userId.scope();
        HttpServletRequest request = new HttpServletRequest() {

            @Override
            public String getAuthType() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Cookie[] getCookies() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public long getDateHeader(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getHeader(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Enumeration<String> getHeaders(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getIntHeader(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getMethod() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getPathInfo() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getPathTranslated() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getContextPath() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getQueryString() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getRemoteUser() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isUserInRole(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            @SuppressWarnings("Convert2Lambda")
            public Principal getUserPrincipal() {
                return new Principal() {

                    @Override
                    public String getName() {
                        return clientId;
                    }
                };
            }

            @Override
            public String getRequestedSessionId() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getRequestURI() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public StringBuffer getRequestURL() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getServletPath() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public HttpSession getSession(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public HttpSession getSession() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String changeSessionId() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean authenticate(HttpServletResponse hsr) throws IOException, ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void login(String string, String string1) throws ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void logout() throws ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Part getPart(String string) throws IOException, ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> type) throws IOException, ServletException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object getAttribute(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getCharacterEncoding() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setCharacterEncoding(String string) throws UnsupportedEncodingException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getContentLength() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public long getContentLengthLong() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getContentType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getParameter(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Enumeration<String> getParameterNames() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String[] getParameterValues(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getProtocol() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getScheme() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getServerName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getServerPort() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public BufferedReader getReader() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getRemoteAddr() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getRemoteHost() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setAttribute(String string, Object o) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void removeAttribute(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Locale getLocale() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Enumeration<Locale> getLocales() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isSecure() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getRealPath(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getRemotePort() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getLocalName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getLocalAddr() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getLocalPort() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ServletContext getServletContext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public AsyncContext startAsync(ServletRequest sr, ServletResponse sr1) throws IllegalStateException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isAsyncStarted() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isAsyncSupported() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public AsyncContext getAsyncContext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DispatcherType getDispatcherType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        AccessTokenResponse response = instance.getToken("client_credentials", null, null, null, null, null, null, null, request);
        assertNotNull(response);
        assertNotNull(response.access_token());
        assertEquals("bearer", response.token_type());
        assertEquals(3600, response.expires_in());
//        assertEquals(scope, response.scope());
    }

    /**
     * Test of getClientCredentialToken method, of class AuthTokenService.
     */
    @Test
    public void testGetPasswordToken() {
        System.out.println("getClientCredentialToken");
        UserIdResponse userId = userIdService.findAll().iterator().next();
        String clientId = userId.clientId();
        String clientSecret = userId.clientSecret();
        String scope = userId.scope();
        AccessTokenResponse response = instance.getToken("password", null, null, null, clientId, clientSecret, null, null, null);
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
