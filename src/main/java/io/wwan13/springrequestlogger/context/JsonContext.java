package io.wwan13.springrequestlogger.context;

import com.fasterxml.jackson.databind.ObjectMapper;

public record JsonContext(
        String value
) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toString() {
        if (value.isBlank()) {
            return "{}";
        }
        return toJsonFormat(value);
    }

    private String toJsonFormat(String value) {
        try {
            return objectMapper.readTree(value).toString();
        } catch (Exception e) {
            return value;
        }
    }

    public static JsonContext of(byte[] content) {
        return new JsonContext(new String(content));
    }
}
