package io.wwan13.springrequestlogger.configure;

import io.wwan13.springrequestlogger.filter.RequestLogger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

public class RequestLoggerRegistrar {

    private final LogMessageConfigurer logMessageConfigurer;

    public RequestLoggerRegistrar(LogMessageConfigurer logMessageConfigurer) {
        this.logMessageConfigurer = logMessageConfigurer;
    }

    @Bean
    FilterRegistrationBean<RequestLogger> requestLogger() {
        FilterRegistrationBean<RequestLogger> filterRegistration = new FilterRegistrationBean<>();
        LogProperties properties = new LogProperties(
                logMessageConfigurer.format(),
                logMessageConfigurer.excludePathPatterns()
        );
        RequestLogger logger = new RequestLogger(properties);

        filterRegistration.setFilter(logger);
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistration;
    }
}
