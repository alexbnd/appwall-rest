package com.radware.appwall.com.radware.appwall.service;

import java.io.IOException;

public interface DBInitializer {

    void initDB();
    Integer getOrder();

    void dumpDB() throws IOException;

    String getTableName();

    String getXmlFileName();
}
