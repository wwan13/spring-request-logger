package io.wwan13.springrequestlogger.context;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public record RequestContext(
        String method,
        String uri,
        HttpStatus status,
        double elapsed,
        KeyValueContext requestHeaders,
        KeyValueContext requestParams,
        JsonContext requestBody,
        JsonContext responseBody
) {

    public static RequestContext of(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            double elapsed
    ) {
        return new RequestContext(
                request.getMethod(),
                request.getRequestURI(),
                HttpStatus.valueOf(response.getStatus()),
                elapsed,
                KeyValueContext.of(request.getHeaderNames(), request::getHeader),
                KeyValueContext.of(request.getParameterNames(), request::getParameter),
                JsonContext.of(request.getContentAsByteArray()),
                JsonContext.of(response.getContentAsByteArray())
        );
    }
}
