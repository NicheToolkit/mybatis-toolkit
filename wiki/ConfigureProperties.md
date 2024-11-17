# cache configuration

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

# table configuration

* prefix

>
> nichetoolkit.mybatis.table
>

* values

|       value       |      type      |   defaultValue    |                         description                         |
|:-----------------:|:--------------:|:-----------------:|:-----------------------------------------------------------:|
|     `catalog`     |    `String`    |                   |    the global catalog of table on mybatis configuration.    |
|     `schema`      |    `String`    |                   |    the global schema of table on mybatis configuration.     |
|  `update-logic`   |   `Boolean`    |      `false`      |    the switch of update logic on mybatis configuration.     |
| `sql-script-show` |   `Boolean`    |      `false`      |   the switch of sql script show on mybatis configuration.   |
|  `datetime-now`   |    `String`    |      `now()`      |    he function of datetime now on mybatis configuration.    |
|  `database-type`  | `DatabaseType` |   `postgresql`    | the global database type of table on mybatis configuration. |
|   `style-type`    |  `StyleType`   | `lower_underline` |        the global sql style type of table on mybatis        
  configuration.   |
|  `excluded-type`  | `ExcludedType` |    `excluded`     |      the global sql excluded type of table on mybatis       
  configuration.   |
|   `properties`    |     `Map`      |                   |  the global properties of table on mybatis configuration.   |
|    `excludes`     |   `String[]`   |                   |   the global excludes of table on mybatis configuration.    |
|     `ignores`     |   `String[]`   |                   |    the global ignores of table on mybatis configuration.    |

* properties

```properties
nichetoolkit.mybatis.table.catalog=
nichetoolkit.mybatis.table.schema=
nichetoolkit.mybatis.table.update-logic=false
nichetoolkit.mybatis.table.sql-script-show=true
nichetoolkit.mybatis.table.datetime-now=now()
nichetoolkit.mybatis.table.database-type=postgresql
nichetoolkit.mybatis.table.style-type=lower_underline
nichetoolkit.mybatis.table.excluded-type=excluded
nichetoolkit.mybatis.table.excludes=
nichetoolkit.mybatis.table.ignores=
```

# record configuration

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