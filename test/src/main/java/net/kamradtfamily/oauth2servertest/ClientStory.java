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
package net.kamradtfamily.oauth2servertest;

import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author randalkamradt
 */
public class ClientStory {
    String name;
    String id;
    
    @Given("a client named $name that will require authorization")
    public void givenClient(String name) {
        System.out.println("*remeber client " + name + "*");
        this.name = name;
    }
    
    @When("the POST verb is issued to the server along with the name")
    public void whenPost() throws IOException {
    String html = new ApacheRequest("http://localhost:8888")
      .uri().path("/client").back()
      .method(Request.POST)
      .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
      .fetch()
      .as(RestResponse.class)
      .assertStatus(HttpURLConnection.HTTP_OK)
      .body();
        this.id = UUID.randomUUID().toString();
    }
    @Then("a complete client with the name $name is created and returned")
    public void thenCreateClient(String name) {
        assertThat(name, equalTo(this.name));
    }
    
    @Given("a that a client named $name no longer needs authentication")
    public void givenClientToDelete(String name) {
        System.out.println("*remeber client " + name + "*");
        this.name = name;
    }
    
    @When("the DELETE verb is issued along with the record id")
    public void whenDelete() {
        System.out.println("delete client " + name);
        this.id = null;
    }
    @Then("all information about the client named $name should be removed")
    public void thenDeleteClient(String name) {
        assertThat(name, equalTo(this.name));
        assertThat(this.id, nullValue());
    }
    
}
