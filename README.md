# gakkiRPC
RPC是不同JVM之间的方法调用过程。

通过RPC将调用过程封装为黑盒，供服务使用方使用。

为了达到最终的调用目的，需要具体实现几个模块

**序列化模块**：将java pojo对象转化为json格式 字节数组，或其他的方便网络传输的格式

**编解码模块**：为了避免黏包半包等问题，规定的服务端和客户端交流的协议

**网络通信模块**：实现网络通信

**代理模块**：通过代理的方式进行方法调用和增强

### 2.序列化模块

fastjson：序列化时间比较快，序列化的字节数组会很大，可能会有安全漏洞

jdk自带序列化：序列化的字节数组会很大，必须要求序列化的对象实现了Serializable接口

protostuff：序列化时间一般，字节数组比较小

### 3.编解码模块

魔数 4字节

版本号 1字节

序列化方式 1字节 0--protostuff 1--fastjson 2--jdk

指令类型 1字节

序列号 4字节

字节数组长度 4字节

字节数组 实际传输的对象RPC框架

## 1.RPC简介

![image-20221021203931934](D:\vuepress\docs\article\distrubutedsystem\project\rpc\image-20221021203931934.png)

RPC是不同JVM之间的方法调用过程。

通过RPC将调用过程封装为黑盒，供服务使用方使用。

为了达到最终的调用目的，需要具体实现几个模块

序列化模块：将java pojo对象转化为json格式 字节数组，或其他的方便

### 2.序列化模块

fastjson：序列化时间比较快，序列化的字节数组会很大，可能会有安全漏洞

jdk自带序列化：序列化的字节数组会很大，必须要求序列化的对象实现了Serializable接口

protostuff：序列化时间一般，字节数组比较小

### 3.编解码模块

协议的几种方法

固定消息长度：[FixedLengthFrameDecoder](https://netty.io/5.0/api/io.netty5.codec/io/netty5/handler/codec/FixedLengthFrameDecoder.html)

按换行来区分消息：[FixedLengthFrameDecoder](https://netty.io/5.0/api/io.netty5.codec/io/netty5/handler/codec/FixedLengthFrameDecoder.html)

长度字段标明消息的长度：[LengthFieldBasedFrameDecoder](https://netty.io/5.0/api/io.netty5.codec/io/netty5/handler/codec/LengthFieldBasedFrameDecoder.html)

特殊符号规定标明长度：[DelimiterBasedFrameDecoder](https://netty.io/5.0/api/io.netty5.codec/io/netty5/handler/codec/DelimiterBasedFrameDecoder.html)



魔数 4字节

版本号 1字节

序列化方式 1字节 0--protostuff 1--fastjson 2--jdk

指令类型 1字节

序列号 4字节

字节数组长度 4字节

字节数组 实际传输的对象
