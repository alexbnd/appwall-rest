package com.radware.appwall.appwallrest.server;

import com.radware.appwall.domain.scrawler.Binding;
import com.radware.appwall.domain.scrawler.Scrawler;
import com.radware.appwall.old.AppWall;
import com.radware.appwall.old.ClientContent;
import com.radware.appwall.old.UserPass;
import com.radware.appwall.old.fileManagers.HostsBindingsFileManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HostBindingFileManagerTests {

    @Autowired
    private HostsBindingsFileManager hostsBindingsFileManager;


    @Test
    public void hostCreate() throws Exception {
        UserPass up = new UserPass("admin", "kavado");
        AppWall appWall = new AppWall("10.206.159.35", 8200);
        hostsBindingsFileManager.setAppWall(appWall);
        ClientContent clientContent = new ClientContent();
        hostsBindingsFileManager.refresh(up, clientContent);
        Scrawler scrawler = hostsBindingsFileManager.getCache();
        Assert.assertNotNull(scrawler);
        List<Binding> bindings = scrawler.hostMap.bindings;
        Assert.assertNotNull(bindings);
        int number = bindings.size();
        Binding binding = new Binding();
        binding.setName("test1");
        binding.setLink("");
        binding.setWebServerInterfaceName(bindings.get(0).getWebServerInterfaceName());
        bindings.add(binding);
        hostsBindingsFileManager.save(up, clientContent);
        hostsBindingsFileManager.refresh(up, clientContent);
        Scrawler scrawler1 = hostsBindingsFileManager.getCache();
        Assert.assertEquals(number + 1, scrawler1.hostMap.bindings.size());
        scrawler1.hostMap.bindings.remove(binding);
        hostsBindingsFileManager.save(up, clientContent);
        hostsBindingsFileManager.refresh(up, clientContent);
        Scrawler scrawler2 = hostsBindingsFileManager.getCache();
        Assert.assertEquals(number, scrawler2.hostMap.bindings.size());

    }

}
