package com.example.spontanactivities.Helpers;

import com.example.spontanactivities.Logic.TagBehaviors.TagBehavior;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class SerialaizerDeserializer {

    public static String serialize(TagBehavior tagBehavior){
        String JSON="";

        ObjectMapper mapper= new ObjectMapper();

        try {
            JavaType t=mapper.getTypeFactory()
                    .constructType(TagBehavior.class);
            JSON= mapper.writerFor(t)
                    .writeValueAsString(tagBehavior);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return JSON;
    }

    public static TagBehavior deserialize(String JSON){
        ObjectMapper mapper = new ObjectMapper();

        TagBehavior tagBehavior= null;

        JavaType type=mapper.getTypeFactory().constructType(TagBehavior.class);
        try {
            tagBehavior=mapper.readValue(JSON,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tagBehavior;
    }
}
