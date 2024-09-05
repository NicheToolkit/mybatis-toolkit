## [Mybatis-Toolkit](https://github.com/NicheToolkit/mybatis-toolkit)

[![GitHub License](https://img.shields.io/badge/license-Apache-blue.svg)](https://github.com/NicheToolkit/rice-toolkit/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.nichetoolkit/mybatis-toolkit-starter)](https://central.sonatype.com/search?smo=true&q=rice-toolkit-starter&namespace=io.github.nichetoolkit)
[![Nexus Release](https://img.shields.io/nexus/r/io.github.nichetoolkit/mybatis-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/releases/io/github/nichetoolkit/rice-toolkit-starter/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/io.github.nichetoolkit/mybatis-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/nichetoolkit/rice-toolkit-starter/)
![Tests](https://github.com/NicheToolkit/mybatis-toolkit/workflows/Tests/badge.svg)

## Toolkit Usages

- [Rice-Toolkit](https://github.com/NicheToolkit/rice-toolkit)

- [File-Toolkit](https://github.com/NicheToolkit/file-toolkit)

## Maven Central Repository

- [Maven Central Repository](https://search.maven.org/search?q=io.github.nichetoolkit)

- [Sonatype Central Repository](https://central.sonatype.dev/search?q=io.github.nichetoolkit)

## Dependent & Environment

> [Spring Boot](https://spring.io/projects/spring-boot) 2.7.18.RELEASE\
> [Maven](https://maven.apache.org/) 3.6.3+\
> [JDK](https://www.oracle.com/java/technologies/downloads/#java8) 1.8

## Wiki Reference

[Wiki Reference](https://github.com/NicheToolkit/mybatis-toolkit/wiki): https://github.com/NicheToolkit/mybatis-toolkit/wiki


## Instructions

### Maven Usages

#### mybatis-toolkit-core

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>mybatis-toolkit-core</artifactId>
    <version>1.1.0</version>
</dependency>
```

#### mybatis-toolkit-context

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>mybatis-toolkit-context</artifactId>
    <version>1.1.0</version>
</dependency>
```

#### mybatis-toolkit-autoconfigure

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>mybatis-toolkit-autoconfigure</artifactId>
    <version>1.1.0</version>
</dependency>
```

#### mybatis-toolkit-starter

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>mybatis-toolkit-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Configure Properties

#### cache configuration

* prefix

>
> nichetoolkit.mybatis.cache
>

* values

|             value             |     type     | defaultValue |                                                             description                                                             |
|:-----------------------------:|:------------:|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
| `init-size` | `Integer` |   `1024`   |                                   the initiate size of table cache on mybatis `sql` handle.                                   |
| `use-once` |  `Boolean`   |   `false`    |                                         the switch of table cache used once on mybatis `sql ` handle.                                         |

* properties

```properties
nichetoolkit.mybatis.cache.init-size=1024
nichetoolkit.mybatis.cache.use-once=false
```

#### table configuration

* prefix

>
> nichetoolkit.mybatis.table
>

* values

|             value             |     type     | defaultValue |                                                             description                                                             |
|:-----------------------------:|:------------:|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
| `catalog` | `String` |      |                                   the global catalog of table on mybatis configuration.                                   |
| `schema` | `String` |       |                                         the global schema of table on mybatis configuration.                                         |
| `database-type` | `DatabaseType` | `postgresql` | the global database type of table on mybatis configuration. |
| `style-type` | `StyleType` | `lower_underline` | the global sql style type of table on mybatis configuration. |
| `properties` | `Map` |  | the global properties of table on mybatis configuration. |
| `excludes` | `String[]` |  | the global excludes of table on mybatis configuration. |

* properties

```properties
nichetoolkit.mybatis.table.catalog=
nichetoolkit.mybatis.table.schema=
nichetoolkit.mybatis.table.database-type=postgresql
nichetoolkit.mybatis.table.style-type=lower_underline
nichetoolkit.mybatis.table.excludes=operate
```

#### datasource configuration

* prefix

>
> nichetoolkit.mybatis.datasource
>

* values

|             value             |     type     | defaultValue |                                                             description                                                             |
|:-----------------------------:|:------------:|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
| `enabled` | `String` | `true` |                                   the switch of datasource auto config on mybatis configuration.                                   |
| `connect-pool-type` | `ConnectPoolType` | `hikari` |                                         the connect pool type of datasource on mybatis configuration.                                         |

* properties

```properties
nichetoolkit.mybatis.datasource.enabled=true
nichetoolkit.mybatis.datasource.connect-pool-type=hikari
```

#### alibaba druid configuration

* reference

[alibaba druid configuration](https://github.com/alibaba/druid/blob/master/druid-spring-boot-starter/README_EN.md): https://github.com/alibaba/druid/blob/master/druid-spring-boot-starter

* prefix

>
> spring.datasource.druid
>

* values

|             value             |     type     | defaultValue |                                                             description                                                             |
|:-----------------------------:|:------------:|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
| `initial-size` | `Integer` | `5` |                                   the initial size of druid connection pool on mybatis configuration.                                   |
| `min-idle` | `Integer` | `10` |                                         the min idle size of druid connection pool on mybatis configuration.                                         |
| `max-active` | `Integer` | `20` | the max active size of druid connection pool on mybatis configuration. |
| `max-wait` | `Integer` | `60000` | the max wait time of druid connection pool on mybatis configuration. |
| `time-between-eviction-runs-millis` | `Integer` | `60000` | the time between eviction runs of druid connection pool on mybatis configuration. |
| `min-evictable-idle-time-millis` | `Integer` | `300000` | the min evictable idle of druid connection pool on mybatis configuration. |
| `max-evictable-idle-time-millis` | `Integer` | `900000` | the max evictable idle of druid connection pool on mybatis configuration. |
| `validation-query` | `String` | `SELECT 1` | the validation query of druid connection pool on mybatis configuration. |
| `test-while-idle` | `Boolean` | `true` | the idle test switch of druid connection pool on mybatis configuration. |
| `test-on-borrow` | `Boolean` | `false` | the borrow test switch of druid connection pool on mybatis configuration. |
| `test-on-return` | `Boolean` | `false` | the return test switch of druid connection pool on mybatis configuration. |
| `master.enabled` | `Boolean` | `false` | the master datasource switch of druid connection pool on mybatis configuration. |
| `slave.enabled` | `Boolean` | `false` | the slave datasource  switch of druid connection pool on mybatis configuration. |

* properties

```properties
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.druid.initial-size=5
spring.datasource.druid.min-idle=10
spring.datasource.druid.max-active=20
spring.datasource.druid.max-wait=60000
spring.datasource.druid.time-between-eviction-runs-millis=60000
spring.datasource.druid.min-evictable-idle-time-millis=300000
spring.datasource.druid.max-evictable-idle-time-millis=900000
spring.datasource.druid.validation-query=SELECT 1
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.test-on-return=false
spring.datasource.druid.test-while-idle=false
spring.datasource.druid.master.enabled=true
spring.datasource.druid.master.url=jdbc:postgresql://localhost:5432/simple_db?stringtype=unspecified
spring.datasource.druid.master.username=postgres
spring.datasource.druid.master.password=2314
spring.datasource.druid.slave.enabled=false
```

## Test Example

[mybatis-toolkit-test-web](https://github.com/NicheToolkit/rice-toolkit/tree/master/rice-toolkit-test-web)

## License

[Apache License](https://www.apache.org/licenses/LICENSE-2.0)

## Dependencies

[Rest-toolkit](https://github.com/NicheToolkit/rest-toolkit)

[Rice-toolkit](https://github.com/NicheToolkit/rice-toolkit)

[Spring Boot](https://github.com/spring-projects/spring-boot)

[Mybatis](https://github.com/mybatis/mybatis-3)

[Mybatis-spring](https://github.com/mybatis/spring)

[Alibaba-druid](https://github.com/alibaba/druid)
