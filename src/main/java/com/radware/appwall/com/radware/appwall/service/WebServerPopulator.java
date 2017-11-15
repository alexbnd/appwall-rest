package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.convertors.WebServerConvertor;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class WebServerPopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Config/ProtectedEntities.cfg";

    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    WebServerConvertor webServerConvertor;

    @Autowired
    HostBindingsWebServersRepository hostBindingsWebServersRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller = null;

    public WebServerPopulator() {
        try {
            jaxbContext = JAXBContext.newInstance(ProtectedEntities.class);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch(JAXBException ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_JAXB_INITIALIZING");
        }

    }

    @Override
    public void initDB() {
        List<WebServerBinding> webServerBindings = populate();
        hostBindingsWebServersRepository.save(webServerBindings);
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    public List<WebServerBinding> populate() {
        Resource resource = resourceLoader.getResource(basePath + PROTECTED_ENTITIES_FILE);
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(resource.getInputStream());
            reader = new BufferedReader(in);
        } catch(IOException e) {
            AppWallLogger
                    .error(this.getClass(), e, "ERROR_INITIALIZING_RESOURCEx1", basePath + PROTECTED_ENTITIES_FILE);
        }
        ProtectedEntities protectedEntities = null;
        try {
            protectedEntities = (ProtectedEntities) unmarshaller.unmarshal(reader);
        } catch(JAXBException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_UNMARSHALLING");
        }
        List<WebServerBinding> bindingList = webServerConvertor.convertToDto(protectedEntities);
        return bindingList;
    }


}
