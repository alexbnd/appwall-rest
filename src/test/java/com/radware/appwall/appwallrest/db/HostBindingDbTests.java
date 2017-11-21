package com.radware.appwall.appwallrest.db;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest

public class HostBindingDbTests { //extends AbstractDBTests {


    @Autowired
    private HostBindingsRepository hostBindingsRepository;

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void contextLoads() {
    }

    @Test
    @DirtiesContext
    public void webServerCreateDelete() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        String newName = "aleks1.com";
        webServer.setWebServerName(newName);
        webServer = webServersRepository.save(webServer);
        Assert.assertEquals(newName, webServer.getWebServerName());
        webServersRepository.delete(webServer);
        webServer = webServersRepository.findOne(webServer.getId());
        Assert.assertNull(webServer);
    }

    @Test
    @DirtiesContext
    public void webServerCreateDuplicateName() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks23.com");
        webServer.setIp("1.1.1.1");
        webServer.setPort(80);
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        WebServerBinding webServer2 = new WebServerBinding();
        webServer2.setWebServerName("aleks23.com");

        exception.expect(DataIntegrityViolationException.class);
        webServersRepository.save(webServer2);
        Assert.assertTrue("Unreachable check if see it - its an error", false);
    }

    @Test
    public void webServerCreateDuplicateIPPort() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setWebServerName("aleks2.com");
        webServer.setIp("1.1.1.1");
        webServer.setPort(80);
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        WebServerBinding webServer2 = new WebServerBinding();
        webServer2.setWebServerName("aleks3.com");
        webServer.setIp("1.1.1.1");
        webServer.setPort(80);

        exception.expect(DataIntegrityViolationException.class);
        webServersRepository.save(webServer2);
        Assert.assertTrue("Unreachable check if see it - its an error", false);
    }


    @Test
    public void dbHostCreate() throws Exception {

        HostBindings hostBindings = new HostBindings();
        String webServerName = "www.google2.com";
        hostBindings.setHostName(webServerName);
        hostBindings.setGetUserIPFromHTTPHeader("127.0.0.1");
        hostBindings = hostBindingsRepository.save(hostBindings);
        Assert.assertNotNull(hostBindings);

        WebServerBinding webServer = new WebServerBinding();
        String hostName = "hackme11.com";
        webServer.setIp("1.1.1.2");
        webServer.setPort(81);
        webServer.setWebServerName(hostName);
        webServer = webServersRepository.save(webServer);
        WebServerBinding binding = webServersRepository.findById(webServer.getId());

        if(hostBindings.getWebServers() == null) {
            hostBindings.setWebServers(new ArrayList<WebServerBinding>());
        }

        hostBindings.getWebServers().add(binding);
        hostBindings = hostBindingsRepository.save(hostBindings);
        Assert.assertEquals(hostBindings.getWebServers().size(), 1);

    }

}
