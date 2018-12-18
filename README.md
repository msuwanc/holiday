# Holiday Application

Holiday Application is an application that provide a single endpoint to get the next holiday from 2 countries based.

### Installation

Holiday Application requires
- [Java](https://www.java.com/en/download/) v11+ to run.
- [Gradle](https://gradle.org/) v5+ to run.

Build jar and run the server

```sh
$ cd holiday
$ gradle build
$ cd holiday/build/libs
$ java -jar holiday-0.0.1-SNAPSHOT.jar
```

For production environment, you can override default config such as API key by writing a new one based on application.properties but different file name which is appliation-prod.properties

```sh
$ cd holiday
$ gradle build
$ cd holiday/build/libs
$ java -jar -Dspring.profiles.active=prod holiday-0.0.1-SNAPSHOT.jar
```

Now, you can try it on swagger link below.
[Swagger](http://localhost:8080/swagger-ui.html)
