# memshell-serial
Java 反序列化内存 Shell 利用工具。

## `--chain` 选择利用链
* CommonsCollections2Chain
* CommonsCollections4Chain

## `--payload` 选择负载类型
* TomcatShell: 简易 WebShell，任意 URL，密码为 34aa503f95bb。
  * eg: http://1.1.1.1:8080/login.jsp?34aa503f95bb&cmd=id
* TomcatBehinder: 定制版冰蝎负载，需要使用定制版冰蝎客户端，任意 URL，密码为 34aa503f95bb。
