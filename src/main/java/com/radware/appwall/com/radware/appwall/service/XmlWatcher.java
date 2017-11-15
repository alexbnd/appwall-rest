package com.radware.appwall.com.radware.appwall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class XmlWatcher {

    private List<DBInitializer> dbInitializers;

    @Autowired
    public XmlWatcher(List<DBInitializer> dbInitializers) {
        this.dbInitializers = dbInitializers;
        Collections.sort(dbInitializers, new Comparator<DBInitializer>() {
            @Override
            public int compare(DBInitializer o1, DBInitializer o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
        System.out.println(dbInitializers.size());
        for (DBInitializer initializer: dbInitializers) {
            initializer.initDB();
        }
    }

    //this method starts on application start and fill the DB according values from appwall config files
    public void initDB() {

    }
}
