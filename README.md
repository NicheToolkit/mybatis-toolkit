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

|    annotation     |                       target                        |                         description                          |
| :---------------: | :-------------------------------------------------: | :----------------------------------------------------------: |
|    `RestName`     | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |    the annotation is used to annotate the name of colimn.    |
| `RestIdentityKey` |                 `ElementType.FIELD`                 | the annotation is used to annotate an identity key of entity. |
| `RestPrimaryKey`  |                 `ElementType.FIELD`                 | the annotation is used to annotate an primary key of table.  |
|  `RestAlertKey`   |                 `ElementType.FIELD`                 | the annotation is used to annotate an alert status key of table. |
|   `RestLinkKey`   |                 `ElementType.FIELD`                 |   the annotation is used to annotate a link key of table.    |
|  `RestUnionKey`   |                 `ElementType.FIELD`                 |   the annotation is used to annotate a union key of table.   |
|  `RestUniqueKey`  |                 `ElementType.FIELD`                 |  the annotation is used to annotate a unique key of table.   |
| `RestOperateKey`  |                 `ElementType.FIELD`                 |  the annotation is used to annotate a operate key of table.  |
|  `RestLogicKey`   |                 `ElementType.FIELD`                 |   the annotation is used to annotate a logic key of table.   |
|   `RestExclude`   |                 `ElementType.FIELD`                 | the annotation is used to annotate a field of need to exclude for the entity. |
|   `RestInsert`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to insert for the entity. |
|   `RestUpdate`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to update for the entity. |
|   `RestSelect`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to select for the entity. |
| `RestForceInsert` | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to force insert for the entity. |
| `RestForceUpdate` | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to force update for the entity. |
|    `RestOrder`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to order for the entity. |
|  `RestJdbcType`   | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a jdbc type of the entity field. |
|  `RestSortType`   | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a sort type of the entity field. |

* simple entity examples

```java
@Data
@RestEntity(name = "ntr_simple")
@EqualsAndHashCode(callSuper = true)
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {
    @RestLinkKey
    private String linkId;
    @RestForceInsert("now()")
    private Date time;
    @RestAlertKey
    private Integer status;

    public SimpleEntity() {
    }

    public SimpleEntity(String id) {
        super(id);
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        simpleModel.setOperate(OperateType.parseKey(this.operate));
        simpleModel.setStatus(SimpleStatus.parseKey(this.status));
        return simpleModel;
    }

}
```

* template entity examples

```java
@Data
@RestEntity(name = "ntr_template")
@EqualsAndHashCode(callSuper = true)
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {
    @RestLinkKey
    private TemplateLinkage linkage;
    @RestForceInsert("now()")
    private Date time;
    @RestAlertKey
    private TemplateAlertness status;

    public TemplateEntity() {
    }

    public TemplateEntity(TemplateIdentity id) {
        super(id);
    }

    @Override
    public TemplateModel toModel() {
        TemplateModel templateModel = new TemplateModel();
        BeanUtils.copyNonnullProperties(this,templateModel);
        templateModel.setOperate(OperateType.parseKey(this.operate));
        templateModel.setLinkId1(this.linkage.getLinkId1());
        templateModel.setLinkId2(this.linkage.getLinkId2());
        return templateModel;
    }

}
```

* special template identity examples

```java
@Data
@RestIdentity
public class TemplateIdentity implements Serializable {
    @RestUnionKey
    private String templatePk1;
    @RestUnionKey
    private String templatePk2;

    public TemplateIdentity() {
    }

    public TemplateIdentity(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }
}
```

* special template linkage examples

```java
@Data
@RestLinkage
public class TemplateLinkage implements Serializable {

    private String linkId1;

    private String linkId2;

    public TemplateLinkage() {
    }

    public TemplateLinkage(String linkId1, String linkId2) {
        this.linkId1 = linkId1;
        this.linkId2 = linkId2;
    }
}
```

* special template alertness examples

```java
@Data
@RestAlertness
public class TemplateAlertness implements Serializable {

    private Integer status1;

    private Integer status2;

    public TemplateAlertness() {
    }

    public TemplateAlertness(Integer status1, Integer status2) {
        this.status1 = status1;
        this.status2 = status2;
    }

    public TemplateAlertness(TemplateStatus1 status1, TemplateStatus2 status2) {
        this.status1 = Optional.ofNullable(status1).map(TemplateStatus1::getKey).orElse(null);
        this.status2 = Optional.ofNullable(status2).map(TemplateStatus2::getKey).orElse(null);
    }

}
```

## Mybatis Mapper

* default mapper

<table style="text-align: center;">
<tr>
<th>name</th>
<th>typeParams</th>
<th>methods</th>
<th>description</th>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisAlertMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, S, I&gt;</code></td>
<td><code>alertById(@Param("id") I id, @Param("status") S status)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>alertById</code>、<code>alertAll</code> and <code>alertAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAll(@Param("idList") Collection&lt;I&gt; idList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAllByWhere(@Param("whereSql") String whereSql, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("status") S status)</code></td>
</tr>    
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisDeleteMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, I&gt;</code></td>
<td><code>deleteById(@Param("id") I id)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>deleteById</code>、 <code>deleteAll</code> and <code>deleteAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id)</code></td>
</tr>
<tr>
<td><code>deleteAll(@Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>deleteAllByWhere(@Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisOperateMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, I&gt;</code></td>
<td><code>operateById(@Param("id") I id, @Param("operate") Integer operate)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>operate</code> and <code>operateAll</code> methods.</td>
</tr>
<tr>
<td><code>operateDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAll(@Param("idList") Collection&lt;I&gt; idList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAllByWhere(@Param("whereSql") String whereSql, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisRemoveMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>removeById(@Param("id") I id, @Param("logic") String logic)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>removeById</code> 、<code>removeAll</code> and <code>removeAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>removeDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAll(@Param("idList") Collection&lt;I&gt; idList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAllByWhere(@Param("whereSql") String whereSql, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql,@Param("logic") String logic)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisSaveMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>save(@Param("entity") E entity)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>save</code> and <code>saveAll</code> methods.</td>
</tr>
<tr>
<td><code>saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity)</code></td>
</tr>
<tr>
<td><code>saveAll(@Param("entityList") Collection&lt;E&gt; entityList)</code></td>
</tr>
<tr>
<td><code>saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection&lt;E&gt; entityList)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisFindMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>findById(@Param("id") I id)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>find</code>、 <code>findAll</code> and <code>findAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>findDynamicById(@Param("tablename") String tablename, @Param("id") I id)</code></td>
</tr>
<tr>
<td><code>findAll(@Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>findAllByWhere(@Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td><code>findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td style="vertical-align: middle;"><code>MybatisSuperMapper</code></td>
<td style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td></td>
<td style="vertical-align: middle;">the mapper is default super mapper.</td>
</tr>
<tr>
<td rowspan=8 style="vertical-align: middle;"><code>MybatisInfoMapper</code></td>
<td rowspan=8 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>findByName(@Param("name") String name, @Param("logic") String logic)</code></td>
<td rowspan=8 style="vertical-align: middle;">the mapper is used to handle <code>find</code> and <code>findAll</code> methods.</td>
</tr>
<tr>
<td><code>findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByEntity(@Param("entity") E entity, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByEntity(@Param("tablename") String tablename, @Param("entity") E entity, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByEntityAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByEntityAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
</table>

* link mapper

<table style="text-align: center;">
<tr>
<th>name</th>
<th>typeParams</th>
<th>methods</th>
<th>description</th>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisDeleteLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, I&gt;</code></td>
<td><code>deleteByLinkId(@Param("linkId") L linkId)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>deleteByLinkId</code> and <code>deleteAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>deleteDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId)</code></td>
</tr>
<tr>
<td><code>deleteAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisOperateLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, I&gt;</code></td>
<td><code>operateByLinkId(@Param("linkId") L linkId, @Param("operate") Integer operate)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>operateByLinkId</code> and <code>operateAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>operateDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisRemoveLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,L,I&gt;</code></td>
<td><code>removeByLinkId(@Param("linkId") L linkId, @Param("logic") String logic)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>removeByLinkId</code> and <code>removeAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisAlertLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, S, I&gt;</code></td>
<td><code>alertByLinkId(@Param("linkId") L linkId, @Param("status") S status)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>alertByLinkId</code> and <code>alertByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>alertDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId,  @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("status") S status)</code></td>
</tr>
</table>

* simple mapper examples

```java
@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>,
        MybatisAlertLinkMapper<SimpleEntity, String, Integer, String>,
        MybatisDeleteLinkMapper<SimpleEntity, String, String>,
        MybatisRemoveLinkMapper<SimpleEntity, String, String>,
        MybatisOperateLinkMapper<SimpleEntity, String, String> {
}
```

* template mapper examples

```java
@Mapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity>,
        MybatisAlertLinkMapper<TemplateEntity, TemplateLinkage, TemplateAlertness, TemplateIdentity>,
        MybatisDeleteLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisRemoveLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisOperateLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity> {
}
```

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

