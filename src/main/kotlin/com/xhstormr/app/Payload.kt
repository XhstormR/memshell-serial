package com.xhstormr.app

enum class Payload(val clazz: Class<*>) {
    CommonsCollections2ForTomcatShell(clazz<TomcatShellFilterTemplatesImpl>()),
    CommonsCollections2ForTomcatBehinder(clazz<TomcatBehinderFilterTemplatesImpl>());
}
