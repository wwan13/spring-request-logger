package io.wwan13.springrequestlogger.context;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonContextTest {

    @Test
    void should_CreatedByByteArray() {
        byte[] value = """
                {
                    "name" : "Kim",
                    "age" : 24
                }
                """.getBytes();

        JsonContext context = JsonContext.of(value);

        assertThat(context.value()).isEqualTo(new String(value));
    }

    @Test
    void should_ReturnFormattedJsonString() {
        byte[] value = """
                {
                    "name" : "Kim"
                }
                """.getBytes();

        JsonContext context = JsonContext.of(value);

        assertThat(context.toString()).isEqualTo("{\"name\":\"Kim\"}");
    }

    @Test
    void should_ReturnDefaultString_when_NullContentEntered() {
        byte[] value = "".getBytes();

        JsonContext context = JsonContext.of(value);

        assertThat(context.toString()).isEqualTo("{}");
    }

    @Test
    void should_ThrowException_when_InvalidJsonContentEntered() {
        byte[] value = """
                {
                    "name" : "Kim
                }
                """.getBytes();

        JsonContext context = JsonContext.of(value);

        assertThatThrownBy(context::toString)
                .isInstanceOf(IllegalArgumentException.class);
    }
}