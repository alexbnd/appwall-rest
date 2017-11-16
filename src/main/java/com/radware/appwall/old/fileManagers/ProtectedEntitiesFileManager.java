package com.radware.appwall.old.fileManagers;


import com.radware.appwall.old.FileManager;
import com.radware.appwall.old.ServerResource;
import com.radware.appwall.old.serverResources.ProtectedEntitiesResource;
import com.radware.appwall.xml.protectedEnities.ProtectedEntities;
import org.springframework.stereotype.Service;


@Service
public class ProtectedEntitiesFileManager extends FileManager<ProtectedEntities> {

    @Override
    protected ProtectedEntities createNewElement() {
        return new ProtectedEntities();
    }

    @Override
    public Class<ProtectedEntities> getRootXmlClass() {
        return ProtectedEntities.class;
    }

    @Override
    public ServerResource getServerResource() {
        return new ProtectedEntitiesResource();
    }

}
