package io.wwan13.springrequestlogger.filter;

import io.wwan13.springrequestlogger.configure.LogProperties;
import io.wwan13.springrequestlogger.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestLogger extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(RequestLogger.class);
    private final static AntPathMatcher matcher = new AntPathMatcher();

    private final LogProperties properties;

    public RequestLogger(LogProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        long requestOccurred = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long requestCompleted = System.currentTimeMillis();
        double requestElapsed = (requestCompleted - requestOccurred) / 1000.0;

        RequestContext context =
                RequestContext.of(requestWrapper, responseWrapper, requestElapsed);
        String logMessage = properties.format().apply(context);
        log.info(logMessage);
        responseWrapper.copyBodyToResponse();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestUri = request.getRequestURI();
        return properties.excludePathPatterns().stream()
                .anyMatch(it -> matcher.match(it, requestUri));
    }
}
