# Messaging example

## Requirements

RabbitMQ installed and message broker running on localhost, default port.

## Description

Messaging example project consisting of 3 separate parts:

1. _message-gateway_: webapp receiving messages in json format POSTed to http://localhost:8080/message and publishing them to 'messageGatewayQueue'

2. _message-queue_: rabbitmq message queue broker running on localhost (default port)

3. _message-processor_: standalone java app which consumes messages from 'messageGatewayQueue' and prints them to stdout

## Run

### build
```
mvn clean package
```

### run rabbitmq (on localhost)
```
$ docker run -it --rm -p 5672:5672 rabbitmq
```

RabbitMQ should log something like this when ready to accept connection:
```
=INFO REPORT==== 13-Nov-2017::13:16:41 ===
started TCP Listener on [::]:5672
 completed with 0 plugins.

=INFO REPORT==== 13-Nov-2017::13:16:41 ===
Server startup complete; 0 plugins started.
```

### run message-processor console app
```
cd message-processor
java -jar target/message-processor-1.0-SNAPSHOT.jar etc/config.properties &
```

RabbitMQ should log something like when message-processor connects:
```
=INFO REPORT==== 13-Nov-2017::13:17:19 ===
accepting AMQP connection <0.372.0> (172.17.0.1:33538 -> 172.17.0.2:5672)

=INFO REPORT==== 13-Nov-2017::13:17:19 ===
connection <0.372.0> (172.17.0.1:33538 -> 172.17.0.2:5672): user 'guest' authenticated and granted access to vhost '/'

```

### run message-gateway web app
```
cd message-gateway
mvn tomcat7:run
```

RabbitMQ should log something like when message-gateway connects:
```
=INFO REPORT==== 13-Nov-2017::13:17:44 ===
accepting AMQP connection <0.389.0> (172.17.0.1:33540 -> 172.17.0.2:5672)

=INFO REPORT==== 13-Nov-2017::13:17:44 ===
connection <0.389.0> (172.17.0.1:33540 -> 172.17.0.2:5672): user 'guest' authenticated and granted access to vhost '/'
```


### POST messages
```
curl http://localhost:8080/message -X POST -d '{"messageId":1, "timestamp":1234, "protocolVersion":"1.0.0", "messageData":{"mMX":1234, "mPermGen":1234}}'
curl http://localhost:8080/message -X POST -d '{"messageId":2, "timestamp":2234, "protocolVersion":"1.0.1", "messageData":{"mMX":1234, "mPermGen":5678, "mOldGen":22222}}'
curl http://localhost:8080/message -X POST -d '{"messageId":3, "timestamp":3234, "protocolVersion":"2.0.0", "payload":{"mMX":1234, "mPermGen":5678, "mOldGen":22222, "mYoungGen":333333}'
```

message-processor should log something like this after receiving above messages from rabbitmq:
```
Message: id=1, timestamp=1234, protocolVersion=1.0.0, messageData: mMx=1234, mPermGen=1234, mOldGen=null, mYoungGen=null
Message: id=2, timestamp=2234, protocolVersion=1.0.1, messageData: mMx=1234, mPermGen=5678, mOldGen=22222, mYoungGen=null
Message: id=3, timestamp=3234, protocolVersion=2.0.0, messageData: mMx=1234, mPermGen=5678, mOldGen=22222, mYoungGen=333333
```


## Message formats


Message can be POSTed in 3 different json formats, structured as follows:

_message v1.0.0_
```
{"messageId":1, "timestamp":1234, "protocolVersion":"1.0.0", "messageData":{"mMX":1234, "mPermGen":1234}}
```

_message v1.0.1_
```
{"messageId":2, "timestamp":2234, "protocolVersion":"1.0.1", "messageData":{"mMX":1234, "mPermGen":5678, "mOldGen":22222}}
```

_message v2.0.0_
```
{"messageId":3, "timestamp":3234, "protocolVersion":"2.0.0", "payload":{"mMX":1234, "mPermGen":5678, "mOldGen":22222, "mYoungGen":333333}}
```

