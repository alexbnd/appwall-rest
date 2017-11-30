package com.radware.appwall.appwallrest.rest;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.WebServersRepository;
import com.radware.appwall.rest.wrappers.WebServerCollectionWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebServerRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebServersRepository webServersRepository;


    @DirtiesContext
    @Test
    public void testFetchAll() {

        ResponseEntity<WebServerCollectionWrapper> entity =
                this.restTemplate.getForEntity("/v3/config/aw/WebServers/", WebServerCollectionWrapper.class);
        System.out.println();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        WebServerCollectionWrapper body = entity.getBody();

        List<WebServerBinding> collection = body.getCollection();
        int size = collection.size();
        Assert.assertNotNull(collection);
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        webServersRepository.save(webServer);
        entity = this.restTemplate.getForEntity("/v3/config/aw/WebServers/", WebServerCollectionWrapper.class);
        body = entity.getBody();

        Assert.assertEquals(size + 1, body.getCollection().size());

    }
}
