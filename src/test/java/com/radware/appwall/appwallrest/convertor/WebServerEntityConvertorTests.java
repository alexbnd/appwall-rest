package com.radware.appwall.appwallrest.convertor;


import com.radware.appwall.appwallrest.AbstractDBTests;
import com.radware.appwall.convertors.WebServerConvertor;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import com.radware.appwall.xml.protectedEnities.WebServerInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebServerEntityConvertorTests extends AbstractDBTests {

    @Autowired
    WebServerConvertor webServerConvertor;
    private static final String protectedEntity =
            "<?xml version=\"1.0\" encoding=\"ASCII\" standalone=\"yes\"?>\n" + "<ProtectedEntities>\n" +
                    "    <WebServerInterfaces>\n" + "        <WebServerInterface>\n" + "            <ID>255</ID>\n" +
                    "            <Name>entity1</Name>\n" + "            <Description>Test Description</Description>\n" +
                    "            <IP>10.10.25.206</IP>\n" + "            <Port>80</Port>\n" +
                    "            <Protocol>HTTP</Protocol>\n" + "            <SSLClientMethod>1</SSLClientMethod>\n" +
                    "        </WebServerInterface>\n" + "    </WebServerInterfaces>\n" + "    <WebFarms/>\n" +
                    "</ProtectedEntities>\n";

    private JAXBContext jaxbContext;

    @Test
    public void convertToDtoSingleTest() throws Exception {
        jaxbContext = JAXBContext.newInstance(ProtectedEntities.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(protectedEntity);
        ProtectedEntities protectedEntities = (ProtectedEntities) unmarshaller.unmarshal(reader);
        Assert.assertNotNull(protectedEntities);
        List<WebServerBinding> bindingList = webServerConvertor.convertToDto(protectedEntities);
        Assert.assertEquals(1, bindingList.size());
        WebServerInterface serverInterface = protectedEntities.webServerInterfaces.webServerInterface.get(0);
        Assert.assertEquals("entity1", serverInterface.name);
        Assert.assertEquals("Test Description", serverInterface.description);
        Assert.assertEquals("10.10.25.206", serverInterface.ip);
        Assert.assertEquals("80", serverInterface.port);
        Assert.assertEquals("1", serverInterface.sslClientMethod);
        Assert.assertEquals("HTTP", serverInterface.protocol);
    }

    @Test
    public void convertToDtoTest() throws Exception {
        jaxbContext = JAXBContext.newInstance(ProtectedEntities.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream("/ProtectedEntities.cfg")));
        ProtectedEntities protectedEntities = (ProtectedEntities) unmarshaller.unmarshal(reader);
        Assert.assertNotNull(protectedEntities);
        List<WebServerBinding> bindingList = webServerConvertor.convertToDto(protectedEntities);
        Assert.assertEquals(6, bindingList.size());
        System.out.println(protectedEntities);
    }
}
