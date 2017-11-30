package com.radware.appwall.com.radware.appwall.service;

import api.tables.HostBindingsProvider;
import com.radware.appwall.convertors.WebServerConvertor;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.WebServersRepository;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import com.radware.appwall.xml.protectedEnities.WebServerInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

@Service
public class WebServerPopulator extends BasePopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Config/ProtectedEntities.cfg";
    public static final String WEB_SERVERS = "WebServers";


    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    WebServerConvertor webServerConvertor;

    @Autowired
    WebServersRepository webServersRepository;

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
    @Transactional
    public void initDB() {
        List<WebServerBinding> webServerBindings = populate();
        webServersRepository.save(webServerBindings);
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    public List<WebServerBinding> populate() {
        Reader reader = getReader();
        if(reader == null) {
            return Collections.emptyList();
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

    @Override
    public void dumpDB() throws IOException {
        Iterable<WebServerBinding> serverBindings = webServersRepository.findAll();
        ProtectedEntities protectedEntities = new ProtectedEntities();
        protectedEntities.webServerInterfaces = new WebServerInterfaces();
        protectedEntities.webServerInterfaces.webServerInterface = webServerConvertor.convertToEntities(serverBindings);
        writeToFile(protectedEntities);

    }

    @Override
    public String getTableName() {
        return WEB_SERVERS;
    }

    @Override
    public String getXmlFileName() {
        return PROTECTED_ENTITIES_FILE;
    }

}
