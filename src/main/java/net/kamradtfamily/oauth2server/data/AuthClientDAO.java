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
package net.kamradtfamily.oauth2server.data;

import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * A simple delagate object that narrows down the redis functions
 * 
 * @author randalkamradt
 */
@Component
public class AuthClientDAO {
    
    @Autowired
    AuthClientRepository repository;

    public Optional<AuthClient> findById(String id) {
        return repository.findById(id);
    }

    public Iterable<AuthClient> findAll() {
        return repository.findAll();
    }

    public AuthClient save(AuthClient authClient) {
        return repository.save(authClient);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Optional<AuthClient> findByClientId(String clientId) {
        // todo optimize
        return StreamSupport.stream(findAll().spliterator(),true)
                .filter(c -> c.getClientId().equals(clientId))
                .findFirst();
    }

    public Optional<AuthClient> findByToken(String token) {
        // todo optimize
        return StreamSupport.stream(findAll().spliterator(),true)
                .filter(c -> c.getAuthToken().equals(token))
                .findFirst();
    }
    
}
