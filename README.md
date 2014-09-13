# Messaging example

## Requirements

RabbitMQ installed and message broker running on localhost, default port.

## Description

Messaging example project consisting of 3 separate parts:
1. _message-gateway_: webapp receiving messages in json format POSTed to http://<base_url>/message and publishing them to 'messageGatewayQueue'
3. _message-queue_: rabbitmq message queue broker running on localhost (default port)
2. _message-processor_: standalone java app which consumes messages from 'messageGatewayQueue' and prints them to stdout

Message can be POSTed in 3 different json formats, structured as follows:
1. message v1.0.0
```
{"messageId":1, "timestamp":1234, "protocolVersion":"1.0.0", "messageData":{"mMX":1234, "mPermGen":1234}}
```
2. message v1.0.1
```
{"messageId":2, "timestamp":2234, "protocolVersion":"1.0.1", "messageData":{"mMX":1234, "mPermGen":5678, "mOldGen":22222}}
```
3. message v2.0.0
```
{"messageId":3, "timestamp":3234, "protocolVersion":"2.0.0", "payload":{"mMX":1234, "mPermGen":5678, "mOldGen":22222, "mYoungGen":333333}}
```

## Run

### build
```
mvn clean package
```

### run message-processor console app
```
cd message-processor
java -jar target/message-processor-1.0-SNAPSHOT.jar etc/config.properties &
```

### run message-gateway web app
```
cd message-gateway
mvn tomcat7:run
```

### POST messages
```
curl http://localhost:8080/message -X POST -d '{"messageId":1, "timestamp":1234, "protocolVersion":"1.0.0", "messageData":{"mMX":1234, "mPermGen":1234}}'
curl http://localhost:8080/message -X POST -d '{"messageId":2, "timestamp":2234, "protocolVersion":"1.0.1", "messageData":{"mMX":1234, "mPermGen":5678, "mOldGen":22222}}'
curl http://localhost:8080/message -X POST -d '{"messageId":3, "timestamp":3234, "protocolVersion":"2.0.0", "payload":{"mMX":1234, "mPermGen":5678, "mOldGen":22222, "mYoungGen":333333}'
```
