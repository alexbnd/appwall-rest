package com.radware.appwall.com.radware.appwall.service;

public interface DBInitializer {

    void initDB();
    Integer getOrder();

    void dumpDB();

    String getTableName();

    String getXmlFileName();
}
