package io.wwan13.springrequestlogger.configure;

import io.wwan13.springrequestlogger.context.RequestContext;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface LogMessageConfigurer {

    default Function<RequestContext, String> format() {
        return requestContext -> """
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

    default List<String> excludePathPatterns() {
        return Collections.emptyList();
    }
}
