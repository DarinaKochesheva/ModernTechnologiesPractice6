# Анализ зависимостей Maven

## Инструкция

Выполните команду в директории `part1/p1_1`:

```bash
mvn dependency:tree
```

Скопируйте вывод команды ниже:

```
[INFO] com.movies:movie-app:jar:1.0-SNAPSHOT
[INFO] +- com.h2database:h2:jar:2.2.224:compile
[INFO] \- org.hibernate.orm:hibernate-core:jar:6.4.0.Final:compile
[INFO]    +- jakarta.persistence:jakarta.persistence-api:jar:3.1.0:compile
[INFO]    +- jakarta.transaction:jakarta.transaction-api:jar:2.0.1:compile
[INFO]    +- org.jboss.logging:jboss-logging:jar:3.5.0.Final:runtime
[INFO]    +- org.hibernate.common:hibernate-commons-annotations:jar:6.0.6.Final:runtime
[INFO]    +- io.smallrye:jandex:jar:3.1.2:runtime
[INFO]    +- com.fasterxml:classmate:jar:1.5.1:runtime
[INFO]    +- net.bytebuddy:byte-buddy:jar:1.14.7:runtime
[INFO]    +- jakarta.xml.bind:jakarta.xml.bind-api:jar:4.0.0:runtime
[INFO]    |  \- jakarta.activation:jakarta.activation-api:jar:2.1.0:runtime
[INFO]    +- org.glassfish.jaxb:jaxb-runtime:jar:4.0.2:runtime
[INFO]    |  \- org.glassfish.jaxb:jaxb-core:jar:4.0.2:runtime
[INFO]    |     +- org.eclipse.angus:angus-activation:jar:2.0.0:runtime
[INFO]    |     +- org.glassfish.jaxb:txw2:jar:4.0.2:runtime
[INFO]    |     \- com.sun.istack:istack-commons-runtime:jar:4.1.1:runtime
[INFO]    +- jakarta.inject:jakarta.inject-api:jar:2.0.1:runtime
[INFO]    \- org.antlr:antlr4-runtime:jar:4.13.0:runtime
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.344 s
[INFO] Finished at: 2026-05-22T19:49:32+03:00
[INFO] ------------------------------------------------------------------------

```

---

## Вопрос 1: Прямые зависимости

**Вопрос:** Сколько прямых (direct) зависимостей имеет ваш проект?

**Ваш ответ:** 2

**Объяснение:** Прямые зависимости - это те, которые вы явно добавили в секцию `<dependencies>` в pom.xml. В проекте добавлены:

1. com.h2database:h2:jar:2.2.224
2. org.hibernate.orm:hibernate-core:jar:6.4.0.Final

---

## Вопрос 2: Транзитивные зависимости Hibernate

**Вопрос:** Сколько транзитивных зависимостей добавляет Hibernate Core?

**Ваш ответ:** 16

**Подсказка:** Посчитайте строки под `org.hibernate.orm:hibernate-core:jar:6.4.0.Final` в дереве зависимостей.

Подсчет: Под строкой org.hibernate.orm:hibernate-core:jar:6.4.0.Final:compile находятся следующие транзитивные зависимости:
- jakarta.persistence-api
- jakarta.transaction-api
- jboss-logging
- hibernate-commons-annotations
- jandex
- classmate
- byte-buddy
- jakarta.xml.bind-api
- jakarta.activation-api 
- jaxb-runtime 
- jaxb-core 
- angus-activation 
- txw2 
- istack-commons-runtime 
- jakarta.inject-api 
- antlr4-runtime



---

## Вопрос 3: Транзитивные зависимости

**Вопрос:** Перечислите 3 транзитивных зависимости, которые подтягивает Hibernate.

1. jakarta.persistence:jakarta.persistence-api:3.1.0
2. net.bytebuddy:byte-buddy:1.14.7
3. org.antlr:antlr4-runtime:4.13.0

---

## Примерные ответы (для самопроверки)

<details>
<summary>Нажмите, чтобы увидеть ответы</summary>

### Ответ 1:
2 прямые зависимости: H2 Database и Hibernate Core.

### Ответ 2:
Hibernate Core добавляет около 15-20 транзитивных зависимостей (зависит от версии).

### Ответ 3:
Примеры транзитивных зависимостей:
- jakarta.persistence-api (JPA API)
- jakarta.transaction-api (JTA)
- jakarta.xml.bind-api (JAXB)
- antlr4-runtime (парсер HQL)
- byte-buddy ( bytecode instrumentation)
- jboss-logging

</details>
