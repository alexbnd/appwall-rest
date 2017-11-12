package com.radware.appwall.appwallrest.db;

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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HostBindingDbTests {


    @Autowired
    private HostBindingsRepository hostBindingsRepository;

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private DataSource dataSource;


    @Test
    public void contextLoads() {
    }

    @After
    @Before
    public void tearDown() {
        try {
            clearDatabase();
        } catch(Exception e) {
            //TODO
            //fail(e.getMessage());
        }
    }


    public void clearDatabase() throws Exception {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try {
                Statement stmt = connection.createStatement();
                try {
                    stmt.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
                    connection.commit();
                } finally {
                    stmt.close();
                }
            } catch(SQLException e) {
                connection.rollback();
                throw new Exception(e);
            }
        } catch(SQLException e) {
            throw new Exception(e);
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
    }

    @Test
    public void webServerCreateDelete() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setHostName("aleks.com");
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
        webServer.setHostName("aleks.com");
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        WebServerBinding webServer2 = new WebServerBinding();
        webServer2.setHostName("aleks.com");

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

        WebServerBinding webServers = new WebServerBinding();
        String hostName = "hackme1.com";
        webServers.setHostName(hostName);
        webServers = webServersRepository.save(webServers);
        WebServerBinding binding = webServersRepository.findById(webServers.getId());

        webServers.setHostBindings(hostBindings);
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
