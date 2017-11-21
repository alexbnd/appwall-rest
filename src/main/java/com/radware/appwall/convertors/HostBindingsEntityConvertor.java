package com.radware.appwall.convertors;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.domain.scrawler.Binding;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostBindingsEntityConvertor {

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Autowired
    private HostBindingsRepository hostBindingsRepository;


    public List<HostBindings> convertToDto(Scrawler scrawler) {
        List<HostBindings> hostBindingsList = new ArrayList<>();

        for(Binding binding : scrawler.hostMap.bindings) {
            HostBindings hostBinding = new HostBindings();
            hostBinding.setHostName(binding.name);
            hostBinding.setGetUserIPFromHTTPHeader(binding.getUserIPFromHTTPHeader);
            hostBinding = hostBindingsRepository.save(hostBinding);
            List<String> webServerInterfaceName = binding.getWebServerInterfaceName();
            List<WebServerBinding> webServerBindingSet = new ArrayList<>();
            for(String webServerName : webServerInterfaceName) {
                WebServerBinding webServerBinding = webServersRepository.findByWebServerNameIgnoreCase(webServerName);
/*                if(webServerBinding == null) {
                    webServerBinding = new WebServerBinding();
                    webServerBinding.setWebServerName(webServerName);
                }*/
                // webServerBinding.setHostBindings(hostBinding);
                //webServerBinding = webServersRepository.save(webServerBinding);
                webServerBindingSet.add(webServerBinding);
            }
            hostBinding.setWebServers(webServerBindingSet);
            hostBinding = hostBindingsRepository.save(hostBinding);
            hostBindingsList.add(hostBinding);
        }
        return hostBindingsList;
    }

    private Scrawler convertToEntities(List<HostBindings> hostBindings) {
        //Fixme
        return new Scrawler();
    }
}
