package com.radware.appwall.com.radware.appwall.service;

import com.radware.appwall.logging.AppWallLogger;
import com.radware.appwall.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class BasePopulator {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${appwall.server.config.base.path}")
    private String basePath;

    public abstract String getXmlFileName();

    protected void writeToFile(Object objectToWrite) throws IOException {
        String xml = "";
        try {
            xml = XmlUtil.toXml(objectToWrite);
        } catch(Exception e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_CONVERT_ENTITY_TO_XMLx1", objectToWrite);
        }
        String fullPath = basePath + getXmlFileName();
        if(fullPath.startsWith("file:")) {
            fullPath = fullPath.substring(5);
        }
        Path path = Paths.get(fullPath);
        if(!Files.exists(path)) {
            File file = new File(path.toString());
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        //Use try-with-resource to get auto-closeable writer instance
        try(BufferedWriter writer = Files
                .newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(xml);
        } catch(IOException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_WRITING_ENTITY_TO_XMLx1", objectToWrite);
            throw e;
        }
    }

    protected Reader getReader() {
        Resource resource = resourceLoader.getResource(basePath + getXmlFileName());
        if(!resource.exists()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(resource.getInputStream());
            reader = new BufferedReader(in);
        } catch(IOException e) {
            AppWallLogger.error(this.getClass(), e, "ERROR_INITIALIZING_RESOURCEx1", basePath + getXmlFileName());
        }
        return reader;
    }

}
