package com.xhstormr.app;

import javax.servlet.*;
import java.io.IOException;

public class FilterImpl implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(request);
        System.out.println(response);
        System.out.println(request.getParameterMap());
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
