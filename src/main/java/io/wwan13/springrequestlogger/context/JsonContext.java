package io.wwan13.springrequestlogger.context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

record JsonContext(
        String value
) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toString() {
        if (value.isBlank()) {
            return "{}";
        }
        JsonNode jsonNode = readValueWithExceptionHandling();
        return jsonNode.toString();
    }

    private JsonNode readValueWithExceptionHandling() {
        try {
            return objectMapper.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot parsing json node");
        }
    }

    public static JsonContext of(byte[] content) {
        return new JsonContext(new String(content));
    }
}
