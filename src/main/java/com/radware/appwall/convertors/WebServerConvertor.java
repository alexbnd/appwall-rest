package com.radware.appwall.convertors;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.old.Bits;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import com.radware.appwall.xml.protectedEnities.WebServerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
public class WebServerConvertor {

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    public List<WebServerBinding> convertToDto(ProtectedEntities protectedEntities) {
        List<WebServerBinding> bindingList = new ArrayList<>();
        for(WebServerInterface serverInterface : protectedEntities.webServerInterfaces.webServerInterface) {
            WebServerBinding webServerBinding =
                    webServersRepository.findByWebServerNameIgnoreCase(serverInterface.name);
            if(webServerBinding == null) {
                webServerBinding = createWebServerBinding(serverInterface);
            }
            bindingList.add(webServerBinding);
        }
        return bindingList;
    }

    private WebServerBinding createWebServerBinding(WebServerInterface serverInterface) {
        WebServerBinding webServerBinding;
        webServerBinding = new WebServerBinding();
        webServerBinding.setWebServerName(serverInterface.name);
        webServerBinding.setDescription(serverInterface.description);
        webServerBinding.setIp(serverInterface.ip);
        webServerBinding.setPort(Integer.parseInt(serverInterface.port));
        webServerBinding.setProtocol(WebServerBinding.ProtocolEnum.valueOf(serverInterface.protocol));
        String sslOpString = serverInterface.sslClientMethod;
        if(sslOpString == null) {
            sslOpString = "0";
        }
        int sslOp = Integer.parseInt(sslOpString);
        webServerBinding.setSupportSSLv2((sslOp & 1) == 0);
        webServerBinding.setSupportSSLv3((sslOp & 2) == 0);
        webServerBinding.setSupportTLSv10((sslOp & 4) == 0);
        webServerBinding.setSupportTLSv11((sslOp & 8) == 0);
        webServerBinding.setSupportTLSv12((sslOp & 16) == 0);
        return webServerBinding;
    }

    public List<WebServerInterface> convertToEntities(Iterable<WebServerBinding> serverBindings) {
        List<WebServerInterface> result = new ArrayList<>();
        for(WebServerBinding binding : serverBindings) {
            WebServerInterface serverInterface = createWebServerInterface(binding);
            result.add(serverInterface);
        }

        return result;
    }

    private WebServerInterface createWebServerInterface(WebServerBinding binding) {
        WebServerInterface serverInterface = new WebServerInterface();
        serverInterface.setId(binding.getId().toString());
        serverInterface.setName(binding.getWebServerName());
        serverInterface.setDescription(binding.description);
        serverInterface.setIp(binding.ip);
        serverInterface.setPort(binding.port.toString());
        serverInterface.setProtocol(binding.protocol.name());
        if(binding.supportSSLv2 == null) {
            binding.supportSSLv2 = false;
        }
        if(binding.supportSSLv3 == null) {
            binding.supportSSLv3 = false;
        }
        if(binding.supportTLSv10 == null) {
            binding.supportTLSv10 = false;
        }
        if(binding.supportTLSv11 == null) {
            binding.supportTLSv11 = false;
        }
        if(binding.supportTLSv12 == null) {
            binding.supportTLSv12 = false;
        }
        BitSet base = Bits.convert(1);
        base.set(0, !binding.supportSSLv2);
        base.set(1, !binding.supportSSLv3);
        base.set(2, !binding.supportTLSv10);
        base.set(3, !binding.supportTLSv11);
        base.set(4, !binding.supportTLSv12);
        Integer newSSLClienrt = (int) Bits.convert(base);
        serverInterface.sslClientMethod = newSSLClienrt.toString();
        return serverInterface;
    }
}
