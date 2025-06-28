# todo-boot

#### 介绍
​	todo-boot是一个开源的todo系统，实现了基础的todo功能。

后端地址：[zjy-DK/todo-boot： todo-boot是一个开源的todo项目，实现todo基础功能](https://github.com/zjy-DK/todo-boot)

app端地址：[zjy-DK/todo-uni](https://github.com/zjy-DK/todo-uni)

桌面端地址：[zjy-DK/todo-electron](https://github.com/zjy-DK/todo-electron)

#### 系统架构
- Spring Boot
- Mysql
- MyBatis Plus
- Redis
- ELK（可选）

#### APP端展示

![APP端展示](https://github.com/zjy-DK/todo-uni/blob/main/images/images.png)

#### 桌面端展示



#### 使用说明

##### 使用ELK

解开application-prod.yml这段注释并配置logstash地址

```yml
# elk日志配置
#logging:
#  level:
#    org.springframework: warn
#  config: classpath:logback-spring.xml
#  # logstash 配置
#  logstash:
#    url: xxxxxxx
```

logback-spring.xml配置

```xml
<!-- Spring 属性注入 -->
<springProperty name="LOG_STASH_URL" scope="context" source="logging.logstash.url" defaultValue="192.168.31.114:4560"/>
<springProperty name="app" scope="context" source="spring.application.name" defaultValue="springboot-server"/>
```

##### 系统异常邮箱预警

application-prod.yml配置

```yaml
...
  mail:
    # 配置 SMTP 服务器地址
    host: smtp.163.com
    # 发送者邮箱
    username: xxxxxx@163.com
    # 配置密码，注意不是真正的密码，而是刚刚申请到的授权码
    password: xxxxxxx
    # 端口号SSL：465/994，无SSL：25
    port: 25
    # 默认的邮件编码为UTF-8
    default-encoding: UTF-8
    # 配置SSL 加密工厂
    properties: # 设置邮件超时时间防止服务器阻塞
      timeout: 5000
      connection-timeout: 5000
      write-timeout: 5000
      mail:
        smtp:
          socketFactoryClass: javax.net.ssl.SSLSocketFactory
        #表示开启 DEBUG 模式，这样，邮件发送过程的日志会在控制台打印出来，方便排查错误
        debug: true
...
#预警通知
warningNotice:
  exceptionNotice: true
  slowRequestLog: true
  receiver: xxxx@qq.com
```