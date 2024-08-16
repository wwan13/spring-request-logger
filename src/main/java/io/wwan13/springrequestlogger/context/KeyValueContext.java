package io.wwan13.springrequestlogger.context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record KeyValueContext(
        Map<String, String> values
) {

    private static final String MESSAGE_FORMAT = "[%s]";
    private static final String ELEMENT_FORMAT = "%s=%s";
    private static final String ELEMENT_DELIMITER = ",";

    @Override
    public String toString() {
        List<String> message = new ArrayList<>();
        values.forEach((key, value) ->
                message.add(ELEMENT_FORMAT.formatted(key, value))
        );
        return MESSAGE_FORMAT.formatted(String.join(ELEMENT_DELIMITER, message));
    }

    public static KeyValueContext of(
            Enumeration<String> names,
            Function<String, String> extractAction
    ) {
        Map<String, String> values = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            values.put(name, extractAction.apply(name));
        }
        return new KeyValueContext(values);
    }
}
