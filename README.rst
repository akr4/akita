==================
Akita
==================
Akita is a template for Java API server. It utilizes:

- Gradle
- Jetty
- Spring Boot

==================
setup dev database
==================

```
createuser -DRS akita
createdb -EUTF-8 -Oakita akita
```


==================
Run
==================

```
gradle -Dapp.env=dev start
```
