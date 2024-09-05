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

#### bean configuration

* prefix

>
> nichetoolkit.rice.bean
>

* values

|             value             |     type     | defaultValue |                                                             description                                                             |
|:-----------------------------:|:------------:|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
|    `model.unique-enabled`     |  `Boolean`   |   `false`    |                                   the switch of unique model check on rest service saving handle.                                   |
| `model.dynamic-table-enabled` |  `Boolean`   |   `false`    |                                         the switch of dynamic table on rest service handle.                                         |
|     `name.unique-enabled`     |  `Boolean`   |    `true`    |                      the switch of unique name check on rest service saving handle.(model name must be unique)                      |
|    `name.nonnull-enabled`     |  `Boolean`   |    `true`    |                     the switch of nonnull name check on rest service saving handle.(model name can not be null)                     |
|     `key.invade-enabled`      |  `Boolean`   |   `false`    |                  the switch of invade model key on rest service saving handle.(the model id can be external input)                  |
|      `key.exist-enabled`      |  `Boolean`   |    `true`    | the switch of model key exist check on rest service updating handle.(the model of key not exist maybe to save when updating handle) |
|    `partition.query-size`     |  `Integer`   |    `2000`    |          the partition size of `in` sql usage on rest service query handle.(the size of sql param: in (p1,p2,p3...p2000))           |
|     `partition.save-size`     |  `Integer`   |    `500`     |                                      the partition size of  model on rest service save handle.                                      |
|    `partition.delete-size`    |  `Integer`   |    `1000`    |          the partition size of `in` sql usage on rest service delete handle.(the size of sql param: in (p1,p2,p3...p1000))          |
|     `delete.delete-mode`      | `DeleteMode` |    `none`    |                   the delete mode of data on rest service delete handle.(the logical delete implementation mode)                    |
|     `delete.remove-mode`      | `RemoveMode` |   `number`   |                   the remove mode of data on rest service remove handle.(the logical remove implementation mode)                   |
|     `delete.before-skip`      |  `Boolean`   |    `true`    |                               the switch of skip delete before handle on rest service delete handle.                                |
|      `delete.after-skip`      |  `Boolean`   |    `true`    |                                the switch of skip delete after handle on rest service delete handle.                                |
|     `delete.pinpoint-sign`     |  `Boolean`   |   `false`    |                               the switch of remove data with pinpoint sign on rest service query handle.                               |
|     `delete.boolean-sign`     |  `Boolean`   |    `true`    |                                   the sign of delete data when logical remove model is `boolean`.                                   |
|    `delete.boolean-value`     |  `Boolean`   |   `false`    |                                  the value of default data when logical remove model is `boolean`.                                  |
|     `delete.number-sign`      |  `Integer`   |     `2`      |                                   the sign of delete data when logical remove model is `number`.                                    |
|     `delete.number-value`     |  `Integer`   |     `1`      |                                  the value of default data when logical remove model is `number`.                                   |

* properties

```properties
nichetoolkit.rice.bean.model.unique-enabled=false
nichetoolkit.rice.bean.model.dynamic-table-enabled=true
nichetoolkit.rice.bean.name.unique-enabled=true
nichetoolkit.rice.bean.name.nonnull-enabled=true
nichetoolkit.rice.bean.key.invade-enabled=false
nichetoolkit.rice.bean.key.exist-enabled=true
nichetoolkit.rice.bean.partition.query-size=2000
nichetoolkit.rice.bean.partition.save-size=500
nichetoolkit.rice.bean.partition.delete-size=1000
nichetoolkit.rice.bean.delete.delete-mode=none
nichetoolkit.rice.bean.delete.remove-mode=number
nichetoolkit.rice.bean.delete.before-skip=true
nichetoolkit.rice.bean.delete.after-skip=true
nichetoolkit.rice.bean.delete.pinpoint-sign=false
nichetoolkit.rice.bean.delete.boolean-sign=true
nichetoolkit.rice.bean.delete.boolean-value=false
nichetoolkit.rice.bean.delete.number-sign=2
nichetoolkit.rice.bean.delete.number-value=1
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
