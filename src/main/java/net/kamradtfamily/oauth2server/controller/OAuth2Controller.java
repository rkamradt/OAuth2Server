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
package net.kamradtfamily.oauth2server.controller;

import net.kamradtfamily.oauth2server.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import net.kamradtfamily.oauth2server.data.Sample;

@RestController
@RequestMapping(path = "/sample")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service sampleService;

    @GetMapping("/{id}")
    public ResponseEntity<Sample> getById(@PathVariable Integer id) {
        Sample sample = sampleService.sampleById(id);
        return ResponseEntity.ok(sample);
    }

    @GetMapping("/")
    public ResponseEntity<List<Sample>> getByIds() {
        List<Sample> list = sampleService.allSamples();
        return ResponseEntity.ok(list);
    }

}
