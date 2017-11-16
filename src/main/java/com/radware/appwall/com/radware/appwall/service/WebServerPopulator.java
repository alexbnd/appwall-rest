package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.convertors.WebServerConvertor;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.old.fileManagers.ProtectedEntitiesFileManager;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.xml.XmlUtil;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import com.radware.appwall.xml.protectedEnities.WebServerInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class WebServerPopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Config/ProtectedEntities.cfg";
    public static final String WEB_SERVERS = "WebServers";


    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    WebServerConvertor webServerConvertor;

    @Autowired
    HostBindingsWebServersRepository hostBindingsWebServersRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ProtectedEntitiesFileManager protectedEntitiesFileManager;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller = null;
    private Marshaller marshaller = null;


    public WebServerPopulator() {
        try {
            jaxbContext = JAXBContext.newInstance(ProtectedEntities.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
        } catch(JAXBException ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_JAXB_INITIALIZING");
        }

    }

    @Override
    public void initDB() {
        List<WebServerBinding> webServerBindings = populate();
        hostBindingsWebServersRepository.save(webServerBindings);
        //dumpDB();
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

    @Override
    public void dumpDB() {
        Iterable<WebServerBinding> serverBindings = hostBindingsWebServersRepository.findAll();
        /*
        ProtectedEntities protectedEntities = protectedEntitiesFileManager.getCache();
        List<WebServerInterface> WebServerInterface = protectedEntities.webServerInterfaces.webServerInterface;
        WebServerInterfaces WebServerInterfaces = protectedEntities.webServerInterfaces;
        */
        ProtectedEntities protectedEntities = new ProtectedEntities();
        protectedEntities.webServerInterfaces = new WebServerInterfaces();
        protectedEntities.webServerInterfaces.webServerInterface = webServerConvertor.convertToEntities(serverBindings);
        String xml = "";
        try {
            xml = XmlUtil.toXml(protectedEntities);
        } catch(Exception e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_CONVERT_ENTITY_TO_XMLx1", protectedEntities);
        }
        String fullPath = basePath + PROTECTED_ENTITIES_FILE + 1;
        if(fullPath.startsWith("file:")) {
            fullPath = fullPath.substring(5);
        }
        Path path = Paths.get(fullPath);

        //Use try-with-resource to get auto-closeable writer instance
        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(xml);
        } catch(IOException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_WRITING_ENTITY_TO_XMLx1", protectedEntities);
        }


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
