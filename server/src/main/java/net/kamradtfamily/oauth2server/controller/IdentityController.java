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

import net.kamradtfamily.oauth2server.data.Token;
import net.kamradtfamily.oauth2server.data.TokenDAO;
import net.kamradtfamily.oauth2server.exception.EntityNotFoundException;
import net.kamradtfamily.oauth2server.response.IdentityResponse;
import net.kamradtfamily.oauth2server.response.ImmutableIdentityResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdResponse;
import net.kamradtfamily.oauth2server.useridserver.UserIdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author randalkamradt
 */
@RestController("/identity")
public class IdentityController {
    @Autowired
    private UserIdServer userIdService;

    @Autowired
    private TokenDAO tokenDao;
    
    @GetMapping("/{token}")
    IdentityResponse getIdentity(@PathVariable String token) {
        @SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})
        Token t = tokenDao.findById(token).orElseThrow(() -> new EntityNotFoundException("token not found"));
        @SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})
        UserIdResponse userId = userIdService.getUserId(t.getClientId()).orElseThrow(() -> new EntityNotFoundException(t.getClientId()));
        return IdentityController.fromUserId(userId, t.getClientId());
    }
    private static IdentityResponse fromUserId(UserIdResponse userId, String clientId) {
        return ImmutableIdentityResponse.builder()
                .clientId(clientId)
                .id(userId.id())
                .name(userId.name())
                .role(userId.scope())
                .build();
    }
    
}
