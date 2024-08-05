package io.wwan13.springrequestlogger.context;

import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.Vector;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class KeyValueContextTest {

    @Test
    void should_CreatedByKeysAndExtractAction() {
        Vector<String> vectorKeys = new Vector<String>();
        vectorKeys.add("key1");
        vectorKeys.add("key2");
        Enumeration<String> keys = vectorKeys.elements();
        Function<String, String> extractAction = (key) -> "value_" + key;

        KeyValueContext context = KeyValueContext.of(keys, extractAction);

        assertThat(context.values().keySet()).contains("key1", "key2");
        assertThat(context.values().values()).contains("value_key1", "value_key2");
    }

    @Test
    void should_ReturnFormattedString() {
        Vector<String> vectorKeys = new Vector<String>();
        vectorKeys.add("key1");
        vectorKeys.add("key2");
        Enumeration<String> keys = vectorKeys.elements();
        Function<String, String> extractAction = (key) -> "value_" + key;
        KeyValueContext context = KeyValueContext.of(keys, extractAction);

        String result = context.toString();

        assertThat(result).isEqualTo("[key1=value_key1,key2=value_key2]");
    }

    @Test
    void should_ReturnDefaultFormattedString_when_EmptyElementsEntered() {
        Vector<String> vectorKeys = new Vector<String>();
        Enumeration<String> keys = vectorKeys.elements();
        Function<String, String> extractAction = (key) -> "value_" + key;
        KeyValueContext context = KeyValueContext.of(keys, extractAction);

        String result = context.toString();

        assertThat(result).isEqualTo("[]");
    }
}