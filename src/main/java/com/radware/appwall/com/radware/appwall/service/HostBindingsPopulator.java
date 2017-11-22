package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.convertors.HostBindingsEntityConvertor;
import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.scrawler.HostMap;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.HostBindingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HostBindingsPopulator extends BasePopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Filters/Scrawler.adv";
    public static final String HOST_BIND = "HOST_BIND";

    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    HostBindingsEntityConvertor hostBindingsEntityConvertor;

    @Autowired
    HostBindingsRepository hostBindingsWebServersRepository;

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
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    public List<HostBindings> populate() {

        Reader reader = getReader();
        if(reader == null) {
            return Collections.emptyList();
        }
        Scrawler scrawler = null;
        List<HostBindings> bindingList = new ArrayList<>();
        try {
            scrawler = (Scrawler) unmarshaller.unmarshal(reader);
        } catch(JAXBException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_UNMARSHALLING");
        }

        try {
            bindingList = hostBindingsEntityConvertor.convertToDto(scrawler);
        } catch(Exception ex) {
            AppWallLogger.error(this.getClass(), ex, "ERROR_SAVING_ENTITYx1", bindingList);
        }
        return bindingList;
    }

    @Override
    public void dumpDB() throws IOException {
        Iterable<HostBindings> serverBindings = hostBindingsWebServersRepository.findAll();
        Scrawler scrawler = new Scrawler();
        scrawler.hostMap = new HostMap();
        scrawler.hostMap.bindings = hostBindingsEntityConvertor.convertToEntities(serverBindings);
        writeToFile(scrawler);

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
