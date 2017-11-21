package com.radware.appwall.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonFormatter {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().
            excludeFieldsWithoutExposeAnnotation().create();

    public JsonFormatter () {
    }

    public String toJson(Object src) {
        return GSON.toJson(src);
    }

}
