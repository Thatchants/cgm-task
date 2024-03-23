package cgm.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();



    public static String convert(Object o) throws JsonProcessingException{
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(o);
    }
    
}
