package com.xhstormr.app

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Base64
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun getSystemResource(name: String): Path =
    Paths.get(ClassLoader.getSystemResource(name).toURI())

fun getSystemResourceAsStream(name: String): InputStream =
    ClassLoader.getSystemResourceAsStream(name)!!

fun String.toHexString(charset: Charset) = this.toByteArray(charset)
    .joinToString("""\x""", """\x""") { "%02x".format(it) }

fun Class<*>.asFile(suffix: Boolean = true): String {
    var path = if (enclosingClass == null) name.replace('.', '/')
    else enclosingClass.asFile(false) + "$" + simpleName
    if (suffix) path += ".class"
    return path
}

fun Class<*>.asByte() =
    getSystemResourceAsStream(this.asFile()).readBytes()

fun Class<*>.asBase64(gzip: Boolean = false): String {
    val bytes = this.asByte()
    return if (gzip) Base64.getEncoder().encode(gzip(bytes)).toString(Charsets.UTF_8)
    else Base64.getEncoder().encode(bytes).toString(Charsets.UTF_8)
}

fun Any.setFieldValue(fieldName: String, value: Any) {
    this.javaClass.getDeclaredField(fieldName).apply { isAccessible = true }[this] = value
}

fun Any.getFieldValue(fieldName: String): Any =
    this.javaClass.getDeclaredField(fieldName).apply { isAccessible = true }[this]

fun Any.serialize(): ByteArray = ByteArrayOutputStream()
    .also { this.serialize(it) }
    .use { it.toByteArray() }

fun Any.serialize(out: OutputStream) {
    ObjectOutputStream(out).use { it.writeObject(this) }
}

fun gzip(bytes: ByteArray): ByteArray = ByteArrayOutputStream()
    .apply { GZIPOutputStream(this).use { it.write(bytes) } }
    .toByteArray()

fun ungzip(bytes: ByteArray): ByteArray = ByteArrayInputStream(bytes)
    .run { GZIPInputStream(this).use { it.readBytes() } }

inline fun <reified T> clazz() = T::class.java
