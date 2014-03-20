==================
Akita
==================
Akita is a template for Java API server. It utilizes:

- Gradle
- Jetty
- Spring Boot
- Lombok

=======================
Development Environment
=======================

```
createuser -DRS akita
createdb -EUTF-8 -Oakita akita
```

```
gradle idea
```

==================
Run
==================

```
gradle -Dapp.env=dev start
```

