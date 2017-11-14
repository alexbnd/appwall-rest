package com.radware.appwall.convertors;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.domain.scrawler.Binding;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HostBindingsEntityConvertor {

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;


    public List<HostBindings> convertToDto(Scrawler scrawler) {
        List<HostBindings> hostBindings = new ArrayList<>();

        for(Binding binding : scrawler.hostMap.bindings) {
            HostBindings hostBinding = new HostBindings();
            hostBinding.setWebServerName(binding.name);
            hostBinding.setGetUserIPFromHTTPHeader(binding.getUserIPFromHTTPHeader);
            List<String> webServerInterfaceName = binding.getWebServerInterfaceName();
            Set<WebServerBinding> webServerBindingSet = new HashSet<>();
            for(String webServerName : webServerInterfaceName) {
                WebServerBinding webServerBinding = webServersRepository.findByHostNameIgnoreCase(webServerName);
                if(webServerBinding == null) {
                    webServerBinding = new WebServerBinding();
                    webServerBinding.setHostName(webServerName);
                }
                webServerBindingSet.add(webServerBinding);
            }
            hostBinding.setWebServers(webServerBindingSet);
        }
        return hostBindings;
    }

    private Scrawler convertToEntity(List<HostBindings> hostBindings) {
        //Fixme
        return new Scrawler();
    }
}
