package com.xhstormr.app

enum class Payload(val clazz: Class<*>) {
    Payload1(clazz<TemplatesImplPayload1>()),
    Payload3(clazz<TemplatesImplPayload3>());
}
