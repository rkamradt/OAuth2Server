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
package net.kamradtfamily.oauth2server.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import net.kamradtfamily.oauth2server.annotation.Nullable;

import java.util.Optional;

/**
 *   {
 *     "access_token":"2YotnFZFEjr1zCsicMWpAA",
 *     "token_type":"example",
 *     "expires_in":3600,
 *     "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
 *     "example_parameter":"example_value"
 *   }
 * @author randalkamradt
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableAccessTokenResponse.class)
public interface AccessTokenResponse {
    String access_token();
    String token_type();
    int expires_in();
    Optional<String> refresh_token();
    Optional<String> scope();
}
