package com.radware.appwall.old.fileManagers;


import com.radware.appwall.xml.hosts.Hosts;
import com.radware.appwall.old.FileManager;
import com.radware.appwall.old.HostsResource;
import com.radware.appwall.old.ServerResource;
import org.springframework.stereotype.Service;

@Service
public class HostsFileManager extends FileManager<Hosts> {

    @Override
    protected Hosts createNewElement() {
        return new Hosts();
    }

    @Override
    public ServerResource getServerResource() {
        return new HostsResource();
    }

    @Override
    public Class<Hosts> getRootXmlClass() {
        return Hosts.class;
    }

}
