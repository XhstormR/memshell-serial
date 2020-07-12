package com.xhstormr.app

enum class Payload(val clazz: Class<*>) {
    Payload1(clazz<TemplatesImplPayload1>()),
    Payload2(clazz<TemplatesImplPayload2>()),
    Payload3(clazz<TemplatesImplPayload3>());
}
