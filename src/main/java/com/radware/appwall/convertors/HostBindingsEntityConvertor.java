package com.radware.appwall.convertors;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.domain.scrawler.Binding;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.repository.WebServersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostBindingsEntityConvertor {

    @Autowired
    private WebServersRepository webServersRepository;

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
                webServerBindingSet.add(webServerBinding);
            }
            hostBinding.setWebServers(webServerBindingSet);
            hostBinding = hostBindingsRepository.save(hostBinding);
            hostBindingsList.add(hostBinding);
        }
        return hostBindingsList;
    }

    public List<Binding> convertToEntities(Iterable<HostBindings> hostBindings) {
        List<Binding> bindingsList = new ArrayList<>();
        for(HostBindings bindings : hostBindings) {
            Binding binding = createHostBindings(bindings);
            bindingsList.add(binding);
        }
        return bindingsList;
    }

    private Binding createHostBindings(HostBindings bindings) {
        Binding binding = new Binding();
        binding.setName(bindings.getHostName());
        List<String> webserverNames = new ArrayList<>();
        for(WebServerBinding webServerBinding : bindings.getWebServers()) {
            webserverNames.add(webServerBinding.getWebServerName());
        }
        binding.setWebServerInterfaceName(webserverNames);
        binding.setGetUserIPFromHTTPHeader("");
        return binding;
    }

}
