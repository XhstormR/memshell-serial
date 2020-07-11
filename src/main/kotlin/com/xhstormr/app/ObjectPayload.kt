package com.xhstormr.app

interface ObjectPayload {
    fun getObject(clazz: Class<*>): Any
}
