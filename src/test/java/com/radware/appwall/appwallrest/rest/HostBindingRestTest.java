package com.radware.appwall.appwallrest.rest;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.WebServersRepository;
import com.radware.appwall.rest.wrappers.HostBindingsCollectionWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HostBindingRestTest {

    public static final String HOST_BINDINGS_URL = "/v3/config/aw/HostBindings";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebServersRepository webServersRepository;

    @Autowired
    private HostBindingsRepository hostBindingsRepository;


    @DirtiesContext
    @Test
    public void testFetchAll() {

        ResponseEntity<HostBindingsCollectionWrapper> entity =
                this.restTemplate.getForEntity(HOST_BINDINGS_URL, HostBindingsCollectionWrapper.class);
        System.out.println();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        HostBindingsCollectionWrapper body = entity.getBody();

        List<HostBindings> collection = body.getCollection();
        int size = collection.size();
        Assert.assertNotNull(collection);
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        webServersRepository.save(webServer);
        HostBindings hostBindings = new HostBindings();
        hostBindings.setHostName("newHB");
        hostBindings.setWebServers(new ArrayList<>());
        hostBindings.getWebServers().add(webServer);
        hostBindingsRepository.save(hostBindings);
        entity = this.restTemplate.getForEntity(HOST_BINDINGS_URL, HostBindingsCollectionWrapper.class);
        body = entity.getBody();

        Assert.assertEquals(size + 1, body.getCollection().size());
    }


    @DirtiesContext
    @Test
    public void testPost() throws URISyntaxException, IOException {
        WebServerBinding testEntity = webServersRepository.findByWebServerNameIgnoreCase("testEntity");
        Assert.assertNull(testEntity);
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("testEntity");
        webServer.setPort(80);
        webServer.setIp("10.10.25.207");
        webServer.setDescription("Description");
        WebServerBinding webServerBinding = webServersRepository.save(webServer);


        HostBindings hostBindings = hostBindingsRepository.findByHostNameIgnoreCase("testHostBinding");
        Assert.assertNull(hostBindings);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson =
                new String(Files.readAllBytes(Paths.get(getClass().getResource("/hostbinding.json").toURI())));
        requestJson = requestJson.replaceAll("null", webServerBinding.getId().toString());
        HttpEntity<String> entity1 = new HttpEntity<String>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

/*
        testEntity = webServersRepository.findByWebServerNameIgnoreCase("testEntity");
        Assert.assertNotNull(testEntity);
        Assert.assertEquals("1.1.1.1", testEntity.ip);
        Assert.assertEquals(Integer.valueOf(80), testEntity.port);
        Assert.assertEquals(WebServerBinding.ProtocolEnum.HTTP, testEntity.protocol);
        Assert.assertEquals("descr 1", testEntity.description);
        Assert.assertEquals(false, testEntity.supportSSLv2);
        Assert.assertEquals(true, testEntity.supportSSLv3);
        Assert.assertEquals(false, testEntity.supportTLSv10);
        Assert.assertEquals(true, testEntity.supportTLSv11);
        Assert.assertEquals(false, testEntity.supportTLSv12);*/

    }
/*
    @Test
    @DirtiesContext
    public void testInvalidPost() throws URISyntaxException, IOException {
        WebServerBinding testEntity = webServersRepository.findByWebServerNameIgnoreCase("testEntity");
        Assert.assertNull(testEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String originalJson = new String(Files.readAllBytes(Paths.get(getClass().getResource("/webserver1.json").toURI())));
        String requestJson = originalJson.replaceAll(  "WebServerName", "ServerName");
        HttpEntity<String> entity1 = new HttpEntity<String>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(HOST_NAME_EMPTY, response.getBody());

        createWebServerBinding();
        requestJson = originalJson.replaceAll("testEntity", "aleks.com");
        entity1 = new HttpEntity<String>(requestJson, headers);
        response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(HOST_NAME_ALREADY_EXIST, response.getBody());


        requestJson = originalJson.replaceAll("1.1.1.1", "192.168.2.34");
        entity1 = new HttpEntity<String>(requestJson, headers);
        response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(IP_AND_PORT_COMBINATION_ALREADY_EXIST, response.getBody());

        requestJson = originalJson.replaceAll("HTTP", "HTTP2");
        entity1 = new HttpEntity<String>(requestJson, headers);
        response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(PROTOCOL_IS_ILLEGAL, response.getBody());

        requestJson = originalJson.replaceAll("1.1.1.1", "");
        entity1 = new HttpEntity<String>(requestJson, headers);
        response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(IP_IS_EMPTY, response.getBody());


        requestJson = originalJson.replaceAll("Port", "Shmort");
        entity1 = new HttpEntity<String>(requestJson, headers);
        response = restTemplate.postForEntity(HOST_BINDINGS_URL, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assert.assertEquals(PORT_IS_EMPTY, response.getBody());

    }


    @Test
    @DirtiesContext
    public void testPut() throws URISyntaxException, IOException {
        WebServerBinding testEntity = webServersRepository.findByWebServerNameIgnoreCase("aleks.com");
        Assert.assertNull(testEntity);

        testEntity = createWebServerBinding();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson2 =
                new String(Files.readAllBytes(Paths.get(getClass().getResource("/webserver2.json").toURI())));
        requestJson2 = requestJson2.replaceAll("UNKNOWN_IP", testEntity.getId().toString());
        HttpEntity<String> entity1 = new HttpEntity<String>(requestJson2, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(HOST_BINDINGS_URL, HttpMethod.PUT, entity1, String.class, "");
        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        testEntity = webServersRepository.findByWebServerNameIgnoreCase("aleks.com");
        Assert.assertNull(testEntity);
        testEntity = webServersRepository.findByWebServerNameIgnoreCase("testEntity2");
        Assert.assertNotNull(testEntity);
        Assert.assertEquals("2.2.2.2", testEntity.ip);
        Assert.assertEquals(Integer.valueOf(81), testEntity.port);
        Assert.assertEquals(WebServerBinding.ProtocolEnum.HTTPS, testEntity.protocol);
        Assert.assertEquals(true, testEntity.supportSSLv2);
        Assert.assertEquals(false, testEntity.supportSSLv3);
        Assert.assertEquals(true, testEntity.supportTLSv10);
        Assert.assertEquals(false, testEntity.supportTLSv11);
        Assert.assertEquals(true, testEntity.supportTLSv12);
    }

    private WebServerBinding createWebServerBinding() {
        WebServerBinding testEntity;
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        testEntity = webServersRepository.save(webServer);
        return testEntity;
    }

    @Test
    public void testDelete() throws URISyntaxException, IOException {

        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        WebServerBinding testEntity = webServersRepository.save(webServer);
        ResponseEntity<String> response = restTemplate
                .exchange(HOST_BINDINGS_URL + "/" + testEntity.getId() + "/", HttpMethod.DELETE, null, String.class, "");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        testEntity = webServersRepository.findByWebServerNameIgnoreCase("aleks.com");
        Assert.assertNull(testEntity);

        //trying to delete not existing entity
        response = restTemplate
                .exchange(HOST_BINDINGS_URL + "/100000/", HttpMethod.DELETE, null, String.class, "");
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    */


}
