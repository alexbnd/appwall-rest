package com.radware.appwall.appwallrest;

import com.radware.appwall.dao.hosts.Hosts;
import com.radware.appwall.old.AppWall;
import com.radware.appwall.old.ClientContent;
import com.radware.appwall.old.HostsFileManager;
import com.radware.appwall.old.UserPass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppwallRestApplicationTests {

    @Autowired
    private HostsFileManager hostsFileManager;

    @Test
    public void contextLoads() {
    }

    @Test
    public void hostCreate() throws Exception {
        UserPass up = new UserPass("admin", "kavado");
        AppWall appWall = new AppWall("10.206.159.35", 8200);
        hostsFileManager.setAppWall(appWall);
        hostsFileManager.refresh(up, new ClientContent());
        Hosts cache = hostsFileManager.getCache();
        Assert.assertNotNull(cache);
    }

}
