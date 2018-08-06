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
package net.kamradtfamily.oauth2server.useridserver;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * This interface represents all or part of the REST contract of the useridservice API
 * 
 * @author rkamradt
 */
@FeignClient(name="users", url="${users.api.url}")
public interface UserIdServer {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    Optional<UserIdResponse> getUserId(@PathVariable("userId") String userId);
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/confirm")
    Optional<UserIdResponse> confirm(@PathVariable("userId") String userId, @RequestHeader(name="password") String password);
    @RequestMapping(method = RequestMethod.POST, value = "/users", consumes = "application/json")
    public void save(UserIdResponse scope);
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<UserIdResponse> findAll();
}
