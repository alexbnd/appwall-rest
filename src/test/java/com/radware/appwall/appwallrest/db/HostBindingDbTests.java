package com.radware.appwall.appwallrest.db;

import com.radware.appwall.appwallrest.AbstractDBTests;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HostBindingDbTests extends AbstractDBTests {


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
    public void webServerCreateDelete() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setHostName("aleks.com");
        webServer.setPort(80);
        webServer.setIp("192.168.2.34");
        webServer.setDescription("Description");
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        String newName = "aleks1.com";
        webServer.setHostName(newName);
        webServer = webServersRepository.save(webServer);
        Assert.assertEquals(newName, webServer.getHostName());
        webServersRepository.delete(webServer);
        webServer = webServersRepository.findOne(webServer.getId());
        Assert.assertNull(webServer);
    }

    @Test
    public void webServerCreateDuplicateName() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setHostName("aleks2.com");
        webServer.setIp("1.1.1.1");
        webServer.setPort(80);
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        WebServerBinding webServer2 = new WebServerBinding();
        webServer2.setHostName("aleks2.com");

        exception.expect(DataIntegrityViolationException.class);
        webServersRepository.save(webServer2);

    }


    @Test
    public void dbHostCreate() throws Exception {

        HostBindings hostBindings = new HostBindings();
        String webServerName = "www.google2.com";
        hostBindings.setWebServerName(webServerName);
        hostBindings.setGetUserIPFromHTTPHeader("127.0.0.1");
        hostBindings = hostBindingsRepository.save(hostBindings);
        //HostBindings hostBindings = hostBindingsRepository.findOne(saved.getId());
        Assert.assertNotNull(hostBindings);

        WebServerBinding webServer = new WebServerBinding();
        String hostName = "hackme11.com";
        webServer.setIp("1.1.1.2");
        webServer.setPort(81);
        webServer.setHostName(hostName);
        webServer = webServersRepository.save(webServer);
        WebServerBinding binding = webServersRepository.findById(webServer.getId());

        webServer.setHostBindings(hostBindings);
        if (hostBindings.getWebServers() == null) {
            hostBindings.setWebServers(new HashSet<>());
        }

        hostBindings.getWebServers().add(binding);
        binding.setHostBindings(hostBindings);

        binding = webServersRepository.save(binding);
        Assert.assertEquals(webServerName, binding.getHostBindings().getWebServerName());
        hostBindings = hostBindingsRepository.findOne(hostBindings.getId());
        Assert.assertEquals(hostBindings.getWebServers().size(), 1);
        WebServerBinding serverBinding = hostBindings.getWebServers().iterator().next();
        Assert.assertEquals(hostBindings.getWebServerName(), serverBinding.getWebServerName());

    }

}
