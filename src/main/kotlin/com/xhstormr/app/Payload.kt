package com.xhstormr.app

enum class Payload(val clazz: Class<*>) {
    TomcatShell(clazz<TomcatShellFilterTemplatesImpl>()),
    TomcatBehinder(clazz<TomcatBehinderFilterTemplatesImpl>());
}
