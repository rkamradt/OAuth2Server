/*
 * The MIT License
 *
 * Copyright 2018 Randal Kamradt
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
package net.kamradtfamily.oauth2server.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.kamradtfamily.oauth2server.data.AuthClient;
import org.immutables.value.Value;

/**
 *
 * @author randalkamradt
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableAuthClientResponse.class)
public interface AuthClientResponse {
    String id();
    String name();
    String clientId();
    String clientSecret();
    static AuthClientResponse fromAuthClient(AuthClient authClient) {
        return ImmutableAuthClientResponse.builder()
                .name(authClient.getName())
                .clientId(authClient.getClientId())
                .clientSecret(authClient.getClientSecret())
                .id(authClient.getId())
                .build();
    }
}
