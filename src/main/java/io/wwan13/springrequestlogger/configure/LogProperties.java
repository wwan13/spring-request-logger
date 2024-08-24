package io.wwan13.springrequestlogger.configure;

import io.wwan13.springrequestlogger.context.RequestContext;

import java.util.List;
import java.util.function.Function;

public record LogProperties(
        Function<RequestContext, String> format,
        List<String> excludePathPatterns
) {
}
