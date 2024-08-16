package io.wwan13.springrequestlogger.configure;

import io.wwan13.springrequestlogger.context.RequestContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@ConditionalOnMissingBean(
        value = LogMessageConfigurer.class,
        ignored = DefaultLogMessageConfigurer.class
)
public class DefaultLogMessageConfigurer implements LogMessageConfigurer {

    @Override
    public Function<RequestContext, String> format() {
        return requestContext -> """
                \n
                %s '%s' - %s (%.2f s)
                >> Request Headers : %s
                >> Request Params  : %s
                >> Request Body    : %s
                >> Response Body   : %s
                """
                .formatted(
                        requestContext.method(), requestContext.uri(),
                        requestContext.status(), requestContext.elapsed(),
                        requestContext.requestHeaders(),
                        requestContext.requestParams(),
                        requestContext.requestBody(),
                        requestContext.responseBody()
                );
    }
}
