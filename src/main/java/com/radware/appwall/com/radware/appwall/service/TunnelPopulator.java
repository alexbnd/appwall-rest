package com.radware.appwall.com.radware.appwall.service;

//import com.radware.appwall.convertors.WebServerConvertor;
//import com.radware.appwall.domain.entities.WebServerBinding;

import api.connect.AppWall;
import api.connect.ClientContent;
import api.connect.DefaultAppWall;
import api.tables.ProviderArgs;
import api.tables.commTunnels.CommTunnelsUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radware.appwall.domain.entities.HttpTunnel;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.json.JsonFormatter;
import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.repository.TunnelRepository;
import com.radware.appwall.repository.WebServersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NameTransformer;
import org.modelmapper.spi.NameableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pojos.HttpsTunnelsPojo;
import shared.UserPass;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

//import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
//import com.radware.appwall.xml.protectedEnities.WebServerInterfaces;

@Service
public class TunnelPopulator extends BasePopulator implements DBInitializer {

    public static final String PROTECTED_ENTITIES_FILE = "Config/Http.cfg";
    public static final String WEB_SERVERS = "WebServers";
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Value("${appwall.server.config.base.path}")
    private String basePath;

    @Autowired
    CommTunnelsUtil commTunnelsUtil;

    @Autowired
    WebServerPopulator webServerPopulator;


    @Autowired
    private TunnelRepository tunnelRepository;

    @Autowired
    private JsonFormatter jsonFormatter;

    @Autowired
    private WebServersRepository webServersRepository;
  //  private JAXBContext jaxbContext;
    //private Unmarshaller unmarshaller = null;
    //private Marshaller marshaller = null;

    private ModelMapper modelMapper;

    public TunnelPopulator() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        modelMapper.getConfiguration().setSourceNameTransformer(new NameTransformer() {
            @Override
            public String transform(String name, NameableType nameableType) {
                return name.toLowerCase();
            }
        });
        modelMapper.getConfiguration().setDestinationNameTransformer(new NameTransformer() {
            @Override
            public String transform(String name, NameableType nameableType) {
                return name.toLowerCase();
            }
        });
/*        try {
            jaxbContext = JAXBContext.newInstance(HttpsTunnelsPojo.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();

        } catch(JAXBException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_JAXB_INITIALIZING");

        }*/
    }
    @Override
    @Transactional
    public void initDB() {
        List<HttpTunnel> httpTunnels = populate();
        tunnelRepository.save(httpTunnels);
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    public List<HttpTunnel> populate() {
        List<HttpTunnel> result = new ArrayList<>();
        try {
            UserPass userPass = new UserPass("admin", "kavado");
            DefaultAppWall.setAddress("https:10.206.159.35:8200");
            AppWall instance = DefaultAppWall.getInstance();
            System.out.println(instance);
            ClientContent clientContent = new ClientContent();
            List<HttpsTunnelsPojo> commTunnel = commTunnelsUtil.getCommTunnel(userPass, false, clientContent);
            System.out.println(commTunnel.size());
            for(HttpsTunnelsPojo pojo : commTunnel) {
                HttpTunnel tunnel = modelMapper.map(pojo, HttpTunnel.class);
                if(tunnel.getServerName() != null && !tunnel.getServerName().isEmpty()) {
                    WebServerBinding webServerBinding =
                            webServersRepository.findByWebServerNameIgnoreCase(tunnel.getServerName());
                    tunnel.setWebServer(webServerBinding);
                }
                result.add(tunnel);
            }

        } catch(Exception e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_INITIALIZING_RESOURCEx1");
        }
        return result;
    }

    @Override
    public void dumpDB() throws IOException {
        Iterable<HttpTunnel> httpTunnels = tunnelRepository.findAll();

        UserPass userPass = new UserPass("admin", "kavado");
        DefaultAppWall.setAddress("https:10.206.159.35:8200");
        AppWall instance = DefaultAppWall.getInstance();
        System.out.println(instance);
        ClientContent clientContent = new ClientContent();

        String body = "";
        ProviderArgs args = new ProviderArgs(null, null, null, "", body, userPass);
        args.setClientContent(clientContent);
        try {
            List<HttpsTunnelsPojo> commTunnel = CommTunnelsUtil.getCommTunnel(userPass, false, clientContent);
            for (HttpsTunnelsPojo pojo: commTunnel) {
                //StringWriter writer = new StringWriter();
                args.body =  GSON.toJson(pojo);
                args.path = new ArrayList<>();
                args.path.add(pojo.Name);
                CommTunnelsUtil.deleteCommTunnel(args, false);
            }

            for(HttpTunnel tunnel: httpTunnels) {
                StringWriter writer = new StringWriter();
                HttpsTunnelsPojo pojo = modelMapper.map(tunnel, HttpsTunnelsPojo.class);
                //marshaller.marshal(pojo, writer);
                args.path = new ArrayList<>();
                args.path.add(pojo.Name);
                args.body =  GSON.toJson(pojo);
                CommTunnelsUtil.addCommTunnel(args, false);
            }

        } catch(Exception e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_SAVING_ENTITYx1");
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
