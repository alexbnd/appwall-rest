package com.radware.appwall.convertors;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import com.radware.appwall.xml.protectedEnities.WebServerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebServerConvertor {

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    public List<WebServerBinding> convertToDto(ProtectedEntities protectedEntities) {
        List<WebServerBinding> bindingList = new ArrayList<>();
        for(WebServerInterface serverInterface : protectedEntities.webServerInterfaces.webServerInterface) {
            WebServerBinding webServerBinding = webServersRepository.findByHostNameIgnoreCase(serverInterface.name);
            if(webServerBinding == null) {
                webServerBinding = new WebServerBinding();
                webServerBinding.setHostName(serverInterface.name);
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
            }
            bindingList.add(webServerBinding);
        }
        return bindingList;
    }
}
