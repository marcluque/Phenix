# Phenix

Phenix is an in-memory key-value caching system

## Description



# Installing

 * Install Maven 3
 * Clone/Download this repo
 * Install it with: mvn clean install

### Maven local dependency

```xml
<dependency>
    <groupId>de.datasec</groupId>
    <artifactId>phenix</artifactId>
    <version>1.0.2-SNAPSHOT</version>
</dependency>
```

### Maven central

__It is planned to push this project into the maven central.__


_If you don't use maven you can download a release version and include it in your project._

# Examples

## Client

```java
PhenixClient phenixClient = new PhenixClient("localhost", 8888);
```

This is an easy example of how to create a Phenix client.
For detailed information and examples see the [client example](https://github.com/DataSecs/Phenix/blob/master/client/src/test/java/ExampleClient.java).

## Server

```java
PhenixServer phenixServer = new PhenixServer("localhost", 8888);
```

This here is an example of how to create a Phenix server.
For detailed information and examples see the [server example](https://github.com/DataSecs/Phenix/blob/master/server/src/test/java/ExampleServer.java).

# License

Licensed under the GNU General Public License, Version 3.0 - see the [LICENSE](LICENSE) file for details.