package pt.uc.sob.defektor.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class JsonUtils {
    /**
     * Serializes an annotated object to JSON using jackson.
     * @param obj Annotated object.
     * @return JSON string.
     */
    public static String ObjectToJson(Object obj) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    /**
     * Converts JSON to an object of an annotated class using jackson.
     * @param value JSON string.
     * @param valueType Class
     * @return Instance of Class retrieved from JSON
     */
    public static Object JsonToObject(String value, Class valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(value, valueType);
        } catch (IOException e) {
            return null;
        }
    }
}
