package io.wwan13.springrequestlogger.configure;

import io.wwan13.springrequestlogger.context.RequestContext;

import java.util.function.Function;

public interface LogMessageConfigurer {

    Function<RequestContext, String> format();
}
