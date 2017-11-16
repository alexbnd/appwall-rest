package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.logging.AppWallLogger;
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
        initDB();
    }

    //this method starts on application start and fill the DB according values from appwall config files
    public void initDB() {
        for (DBInitializer initializer: dbInitializers) {
            initializer.initDB();
        }
    }


    //thi method runs on apply command applied
    //it call all initializer method dump
    public void dumpDB() {
        for (DBInitializer initializer: dbInitializers) {
            AppWallLogger.trace(this.getClass(), "DUMPING_TABLE_TO_XMLx2", initializer.getTableName(), initializer.getXmlFileName());
            initializer.dumpDB();
        }
    }
}
