package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.convertors.HostBindingsEntityConvertor;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HostBindingsPopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Filters/Scrawler.adv";
    public static final String HOST_BIND = "HOST_BIND";


    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    HostBindingsEntityConvertor webServerConvertor;

    @Autowired
    HostBindingsRepository hostBindingsWebServersRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller = null;
    private Marshaller marshaller = null;


    public HostBindingsPopulator() {
        try {
            jaxbContext = JAXBContext.newInstance(Scrawler.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
        } catch(JAXBException ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_JAXB_INITIALIZING");
        }

    }

    @Override
    @Transactional
    public void initDB() {
        List<HostBindings> webServerBindings = populate();
        hostBindingsWebServersRepository.save(webServerBindings);
        //dumpDB();
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    public List<HostBindings> populate() {
        List<HostBindings> bindingList = new ArrayList<>();
        Resource resource = resourceLoader.getResource(basePath + PROTECTED_ENTITIES_FILE);
        if(!resource.exists()) {
            return Collections.emptyList();
        }
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(resource.getInputStream());
            reader = new BufferedReader(in);
        } catch(IOException e) {
            AppWallLogger
                    .error(this.getClass(), e, "ERROR_INITIALIZING_RESOURCEx1", basePath + PROTECTED_ENTITIES_FILE);
        }
        Scrawler scrawler = null;
        try {
            scrawler = (Scrawler) unmarshaller.unmarshal(reader);
        } catch(JAXBException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_UNMARSHALLING");
        }

        try {
            bindingList = webServerConvertor.convertToDto(scrawler);
        } catch(Exception ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_SAVING_ENTITYx1", bindingList);
        }
        return bindingList;
    }

    @Override
    public void dumpDB() throws IOException {
        //TODO
       /* Iterable<WebServerBinding> serverBindings = hostBindingsWebServersRepository.findAll();
        ProtectedEntities protectedEntities = new ProtectedEntities();
        protectedEntities.webServerInterfaces = new WebServerInterfaces();
        protectedEntities.webServerInterfaces.webServerInterface = webServerConvertor.convertToEntities(serverBindings);
        String xml = "";
        try {
            xml = XmlUtil.toXml(protectedEntities);
        } catch(Exception e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_CONVERT_ENTITY_TO_XMLx1", protectedEntities);
        }
        String fullPath = basePath + PROTECTED_ENTITIES_FILE;
        if(fullPath.startsWith("file:")) {
            fullPath = fullPath.substring(5);
        }
        Path path = Paths.get(fullPath);
        if (!Files.exists(path)) {
            File file = new File(path.toString());
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        //Use try-with-resource to get auto-closeable writer instance
        try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(xml);
        } catch(IOException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_WRITING_ENTITY_TO_XMLx1", protectedEntities);
            throw e;
        }

*/
    }

    @Override
    public String getTableName() {
        return HOST_BIND;
    }

    @Override
    public String getXmlFileName() {
        return PROTECTED_ENTITIES_FILE;
    }

}
