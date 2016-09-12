一、环境准备 
  1）准备两台rabbitmq服务器，一台用于中国，一台用于美国
  2）每台rabbitmq都安装好shovel plunin、shovel management plunin
  3）每台rabbitmq都新建好winit_send,winit_receive二个exchange,其中：
      winit_send用于发送消息，Type指定为topic，durable指定为true
      winit_receive用于接收消息，Type指定为headers，durable指定为true
  4）建立上面两个exchange的绑定关系
    中国rabbitmq:
    打winit_send这个exchange,使用#.CN这个bind routing key建立到winit_receive的绑定关系
    美国rabbitmq:
    打winit_send这个exchange,使用#.US这个bind routing key建立到winit_receive的绑定关系
  5)建立shovel关系
    中国rabbitmq:
    Name：US_TO_CN,
    Source: amqp//用户名:密码@美国服务器ip:端口/，选择Exchange后，填入winit_send,routing key填入#.CN
    Destination: amqp//用户名:密码@中国服务器ip:端口/，选择Exchange后，填入winit_receive,routing key不用填
    美国rabbitmq:
    Name：CN_TO_US,
    Source: amqp//用户名:密码@中国服务器ip:端口/，选择Exchange后，填入winit_send,routing key填入#.US
    Destination: amqp//用户名:密码@美国服务器ip:端口/，选择Exchange后，填入winit_receive,routing key不用填
    
二、启动消息接收程序
  安装jdk 7,打开receiver目录，根据需要修改config.properties，然后使用java -jar receiver.jar启动接收程序
三、启动消息发送程序
  安装jdk 7,打开sender目录，根据需要修改config.properties，然后使用java -jar sender.jar启动发送程序