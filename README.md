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

### Rice & Mybatis Toolkit Usages

* Maven (`pom.xml`)

```xml

<dependencies>
    <!-- use rice super/info service -->
    <dependency>
        <groupId>io.github.nichetoolkit</groupId>
        <artifactId>rice-toolkit-starter</artifactId>
    </dependency>

    <!-- use mybatis super/info mapper -->
    <dependency>
        <groupId>io.github.nichetoolkit</groupId>
        <artifactId>mybatis-toolkit-starter</artifactId>
    </dependency>

    <!-- set mybatis default configuration -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

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

|    value    |   type    | defaultValue |                          description                          |
|:-----------:|:---------:|:------------:|:-------------------------------------------------------------:|
| `init-size` | `Integer` |    `1024`    |   the initiate size of table cache on mybatis `sql` handle.   |
| `use-once`  | `Boolean` |   `false`    | the switch of table cache used once on mybatis `sql ` handle. |

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

|      value      |      type      |   defaultValue    |                         description                          |
| :-------------: | :------------: | :---------------: | :----------------------------------------------------------: |
|    `catalog`    |    `String`    |                   |    the global catalog of table on mybatis configuration.     |
|    `schema`     |    `String`    |                   |     the global schema of table on mybatis configuration.     |
| `update-logic`  |   `Boolean`    |      `false`      |     the switch of update logic on mybatis configuration.     |
| `database-type` | `DatabaseType` |   `postgresql`    | the global database type of table on mybatis configuration.  |
|  `style-type`   |  `StyleType`   | `lower_underline` | the global sql style type of table on mybatis configuration. |
|  `properties`   |     `Map`      |                   |   the global properties of table on mybatis configuration.   |
|   `excludes`    |   `String[]`   |                   |    the global excludes of table on mybatis configuration.    |
|    `ignores`    |   `String[]`   |                   |    the global ignores of table on mybatis configuration.     |

* properties

```properties
nichetoolkit.mybatis.table.catalog=
nichetoolkit.mybatis.table.schema=
nichetoolkit.mybatis.table.update-logic=false
nichetoolkit.mybatis.table.database-type=postgresql
nichetoolkit.mybatis.table.style-type=lower_underline
nichetoolkit.mybatis.table.excludes=
nichetoolkit.mybatis.table.ignores=
```

#### record configuration

* prefix

>
> nichetoolkit.mybatis.record
>

* values

|   value   |   type    | defaultValue |                          description                          |
|:---------:|:---------:|:------------:|:-------------------------------------------------------------:|
| `enabled` | `Boolean` |    `true`    | the switch of record auto configure on mybatis configuration. |

* properties

```properties
nichetoolkit.mybatis.record.enabled=false
```

### Stereotype Annotation

* default whole annotation

|  annotation  |                            target                            |                         description                          |
| :----------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| `RestMapper` | `ElementType.TYPE`、 `ElementType.METHOD`、`ElementType.FIELD`、`ElementType.PARAMETER` | the annotation is used to annotate mybatis mapper interfaces there need to mark `entityType` 、`identityType` 、`linkageType` and `alertnessType`. |
| `RestTable`  |                      `ElementType.TYPE`                      | the annotation is used to annotate mybatis table entity objects there need to set table configuration. |
| `RestColumn` |                     `ElementType.FIELD`                      | the annotation is used to annotate mybatis entity  column objects there need to set column configuration. |

* default table annotation

|    annotation    |                            target                            |                         description                          |
| :--------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|   `RestEntity`   | `ElementType.TYPE`、 `ElementType.METHOD`、`ElementType.ANNOTATION_TYPE` | the annotation is used to annotate mybatis table entity objects there need to set table configuration. it is maybe `name`、`comment`、`alias` 、`entityType` 、`identityType` 、`linkageType` and `alertnessType`. |
|  `RestIdentity`  |                      `ElementType.TYPE`                      | the annotation is used to annotate table customize identity of entity. it must be a class that is included the primary key or union keys of database. |
|  `RestLinkage`   |                      `ElementType.TYPE`                      | the annotation is used to annotate table customize link id of entity. it must be a class that is included the foreign keys of database. |
| `RestAlertness`  |                      `ElementType.TYPE`                      | the annotation is used to annotate table customize alert status (it's better to enum) of entity. it must be a class that is included the status keys. |
|  `RestLinkKeys`  |                      `ElementType.TYPE`                      | the annotation is used to annotate alert link keys of entity when the attribute of the parent class cannot be annotated with `RestLinkKey`. |
| `RestAlertKeys`  |                      `ElementType.TYPE`                      | the annotation is used to annotate alert status keys of entity when the attribute of the parent class cannot be annotated with `RestAlertKey`. |
|  `RestExcludes`  |                      `ElementType.TYPE`                      | the annotation is used to annotate a field of need to exclude for entity. |
|  `RestIgnores`   |                      `ElementType.TYPE`                      | the annotation is used to annotate a field of need to ignore for entity. |
| `RestProperties` | `ElementType.TYPE`、`ElementType.FIELD`、`ElementType.METHOD` | the annotation is used to annotate a property field of entity. |
| `RestResultMap`  |                      `ElementType.TYPE`                      |  the annotation is used to annotate a result map of table.   |
| `RestTableStyle` |                      `ElementType.TYPE`                      |   the annotation is used to annotate style of table name.    |
| `RestUnionKeys`  |                      `ElementType.TYPE`                      | the annotation is used to annotate union keys of entity when the attribute of the parent class cannot be annotated with `RestUnionKey`. |
| `RestUniqueKeys` |                      `ElementType.TYPE`                      | the annotation is used to annotate unique keys of entity when the attribute of the parent class cannot be annotated with `RestUniqueKey`. |

* default column annotation




## Test Example

[mybatis-toolkit-example](https://github.com/NicheToolkit/mybatis-toolkit/tree/master/mybatis-toolkit-example)

## License

[Apache License](https://www.apache.org/licenses/LICENSE-2.0)

## Dependencies

[Rest-toolkit](https://github.com/NicheToolkit/rest-toolkit)

[Rice-toolkit](https://github.com/NicheToolkit/rice-toolkit)

[Spring Boot](https://github.com/spring-projects/spring-boot)

[Mybatis](https://github.com/mybatis/mybatis-3)

[Mybatis-spring](https://github.com/mybatis/spring)

