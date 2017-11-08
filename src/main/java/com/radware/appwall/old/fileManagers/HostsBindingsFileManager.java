package com.radware.appwall.old.fileManagers;

import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.old.FileManager;
import com.radware.appwall.old.ServerResource;
import com.radware.appwall.old.serverResources.HostsBindingsResource;
import org.springframework.stereotype.Service;

@Service
public class HostsBindingsFileManager extends FileManager<Scrawler> {

	@Override
	protected Scrawler createNewElement() {
		return new Scrawler();
	}

	@Override
	public ServerResource getServerResource() {
		return new HostsBindingsResource();
	}

	@Override
	public Class<Scrawler> getRootXmlClass() {
		return Scrawler.class;
	}

}
