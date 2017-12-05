# helloworld-jms: Helloworld JMS Example

Author: Weston Price  
Level: Intermediate  
Technologies: JMS  
Summary: The `helloworld-jms` quickstart demonstrates the use of external JMS clients with WildFly.  
Target Product: WildFly  
Source: <https://github.com/wildfly/quickstart/>  

## What is it?

The `helloworld-jms` quickstart demonstrates the use of external JMS clients with WildFly Application Server.

It contains the following:

1. A message producer that sends messages to a JMS destination deployed to a WildFly server.

2. A message consumer that receives message from a JMS destination deployed to a WildFly server.

## Add an Application User

This quickstart uses secured management interfaces and requires that you create the following application user to access the running application.

| **UserName** | **Realm** | **Password** | **Roles** |
|:-----------|:-----------|:-----------|:-----------|
| guest | ApplicationRealm | guest | guest |

To add the application user, open a command prompt and type the following command:

        For Linux:   WILDFLY_HOME/bin/add-user.sh -a -u 'guest' -p 'guest' -g 'guest'

If you prefer, you can use the add-user utility interactively.
For an example of how to use the add-user utility, see the instructions located here: [Add an Application User](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CREATE_USERS.md#add-an-application-user).


## Configure the Server

* Start WildFly with Full profile (`bin/standalone.sh -c standalone-full.xml`).
* Create a test queue by using JBoss CLI:

```
jms-queue add --queue-address=testQueue --entries=queue/test,java:jboss/exported/jms/queue/test
```
## Build and Execute the Quickstart

To run the quickstart from the command line:

* Make sure you have started the WildFly server with Full profile
* use Maven to build the quickstart ueberjar
```
mvn clean package
```
* run as the JAR as
  * Producer 
```
java -jar target/hello-world.jms -producer
```
  * Consumer 
```
java -jar target/hello-world.jms -consumer
```



## Optional System Properties

You can use `java -D[propertyName]=[propertyValue` to provide custom values for some client properties:

* `username`

    This username is used for both the JMS connection and the JNDI look-up.  Instructions to set up the quickstart application user can be found here: [Add an Application User](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CREATE_USERS.md#add-an-application-user).

    Default: `guest`

* `password`

    This password is used for both the JMS connection and the JNDI look-up.  Instructions to set up the quickstart application user can be found here: [Add an Application User](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CREATE_USERS.md#add-an-application-user)

    Default: `guest`

* `connection.factory`

    The name of the JMS ConnectionFactory you want to use.

    Default: `jms/RemoteConnectionFactory`

* `destination`

    The name of the JMS Destination you want to use.

    Default: `jms/queue/test`

* `wait.time`

    The number of seconds between messages are sent/consumed

    Default: `1`

* `message.content`

    The content of the JMS TextMessage.

    Default: `"Hello, World!"`

* `java.naming.provider.url`

	  This property allows configuration of the JNDI directory used to lookup the JMS destination. This is useful when the client resides on another host.

    Default: `"localhost"`
