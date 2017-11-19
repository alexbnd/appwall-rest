package com.radware.appwall.appwallrest.validator;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.rest.providers.WebServerValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidWebServerBindingTests {

    @Autowired
    private WebServerValidator validator;

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Test
    @DirtiesContext
    public void webServerCreateDuplicateIPPort() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setHostName("aleks2.com");
        webServer.setIp("1.1.1.1");
        webServer.setPort(80);
        webServer.setProtocol(WebServerBinding.ProtocolEnum.HTTP);
        webServer = webServersRepository.save(webServer);
        Assert.assertNotNull(webServer);
        WebServerBinding webServer2 = new WebServerBinding();
        webServer2.setHostName("aleks3.com");
        webServer2.setIp("1.1.1.1");
        webServer2.setPort(81);
        webServer2.setProtocol(WebServerBinding.ProtocolEnum.HTTP);

        String validationErrors = validator.validate(webServer2);
        Assert.assertNull(validationErrors);
        webServer2 = webServersRepository.save(webServer2);
        webServer2.setPort(80);
        validationErrors = validator.validate(webServer2);
        Assert.assertEquals(validator.IP_AND_PORT_COMBINATION_ALREADY_EXIST, validationErrors);
        webServer.setDescription("dududu");
        validationErrors = validator.validate(webServer);
        Assert.assertNull(validationErrors);
        webServer2.setHostName("aleks2.com");
        validationErrors = validator.validate(webServer2);
        Assert.assertEquals(validator.HOST_NAME_ALREADY_EXIST, validationErrors);
    }

    @Test
    public void webServerEmptyRequiredFields() throws Exception {
        WebServerBinding webServer = new WebServerBinding();
        webServer.setIp("1.1.1.2");
        webServer.setPort(83);
        webServer.setProtocol(WebServerBinding.ProtocolEnum.HTTP);
        String validate = validator.validate(webServer);
        Assert.assertEquals(validator.HOST_NAME_EMPTY, validate);
        webServer.setHostName("aleks2.com");
        webServer.setIp(null);
        validate = validator.validate(webServer);
        Assert.assertEquals(validator.IP_IS_EMPTY, validate);
        webServer.setPort(null);
        webServer.setIp("33.3.3.3");
        validate = validator.validate(webServer);
        Assert.assertEquals(validator.PORT_IS_EMPTY, validate);
        webServer.setPort(99);
        webServer.setProtocol(null);
        validate = validator.validate(webServer);
        Assert.assertEquals(validator.PROTOCOL_IS_ILLEGAL, validate);
        webServer.setProtocol(WebServerBinding.ProtocolEnum.HTTP);
        validate = validator.validate(webServer);
        Assert.assertNull("everything is ok", validate);

    }

}
