* default whole annotation

|  annotation  |                                        target                                        |                                                                   description                                                                    |
|:------------:|:------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------:|
| `RestMapper` | `ElementType.TYPE`、 `ElementType.METHOD`、`ElementType.FIELD`、`ElementType.PARAMETER` | the annotation is used to annotate mybatis mapper interfaces there need to mark `entityType` 、`identityType` 、`linkageType` and `alertnessType`. |
| `RestTable`  |                                  `ElementType.TYPE`                                  |                      the annotation is used to annotate mybatis table entity objects there need to set table configuration.                      |
| `RestColumn` |                                 `ElementType.FIELD`                                  |                    the annotation is used to annotate mybatis entity  column objects there need to set column configuration.                     |

* default table annotation

|    annotation    |                                 target                                 |                                                                                                  description                                                                                                  |
|:----------------:|:----------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|   `RestEntity`   | `ElementType.TYPE`、 `ElementType.METHOD`、`ElementType.ANNOTATION_TYPE` | the annotation is used to annotate mybatis table entity objects there need to set table configuration. it is maybe `name`、`comment`、`alias` 、`entityType` 、`identityType` 、`linkageType` and `alertnessType`. |
|  `RestIdentity`  |                           `ElementType.TYPE`                           |                             the annotation is used to annotate table customize identity of entity. it must be a class that is included the primary key or union keys of database.                             |
|  `RestLinkage`   |                           `ElementType.TYPE`                           |                                    the annotation is used to annotate table customize link id of entity. it must be a class that is included the foreign keys of database.                                    |
| `RestAlertness`  |                           `ElementType.TYPE`                           |                             the annotation is used to annotate table customize alert status (it's better to enum) of entity. it must be a class that is included the status keys.                             |
|  `RestLinkKeys`  |                           `ElementType.TYPE`                           |                                  the annotation is used to annotate alert link keys of entity when the attribute of the parent class cannot be annotated with `RestLinkKey`.                                  |
| `RestAlertKeys`  |                           `ElementType.TYPE`                           |                                the annotation is used to annotate alert status keys of entity when the attribute of the parent class cannot be annotated with `RestAlertKey`.                                 |
|  `RestExcludes`  |                           `ElementType.TYPE`                           |                                                                   the annotation is used to annotate a field of need to exclude for entity.                                                                   |
|  `RestIgnores`   |                           `ElementType.TYPE`                           |                                                                   the annotation is used to annotate a field of need to ignore for entity.                                                                    |
| `RestProperties` |      `ElementType.TYPE`、`ElementType.FIELD`、`ElementType.METHOD`       |                                                                        the annotation is used to annotate a property field of entity.                                                                         |
| `RestResultMap`  |                           `ElementType.TYPE`                           |                                                                           the annotation is used to annotate a result map of table.                                                                           |
| `RestTableStyle` |                           `ElementType.TYPE`                           |                                                                            the annotation is used to annotate style of table name.                                                                            |
| `RestUnionKeys`  |                           `ElementType.TYPE`                           |                                    the annotation is used to annotate union keys of entity when the attribute of the parent class cannot be annotated with `RestUnionKey`.                                    |
| `RestUniqueKeys` |                           `ElementType.TYPE`                           |                                   the annotation is used to annotate unique keys of entity when the attribute of the parent class cannot be annotated with `RestUniqueKey`.                                   |

* default column annotation

|    annotation     |                       target                       |                                    description                                     |
|:-----------------:|:--------------------------------------------------:|:----------------------------------------------------------------------------------:|
|    `RestName`     | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |               the annotation is used to annotate the name of colimn.               |
| `RestIdentityKey` |                `ElementType.FIELD`                 |           the annotation is used to annotate an identity key of entity.            |
| `RestPrimaryKey`  |                `ElementType.FIELD`                 |            the annotation is used to annotate an primary key of table.             |
|  `RestAlertKey`   |                `ElementType.FIELD`                 |          the annotation is used to annotate an alert status key of table.          |
|   `RestLinkKey`   |                `ElementType.FIELD`                 |              the annotation is used to annotate a link key of table.               |
|  `RestUnionKey`   |                `ElementType.FIELD`                 |              the annotation is used to annotate a union key of table.              |
|  `RestUniqueKey`  |                `ElementType.FIELD`                 |             the annotation is used to annotate a unique key of table.              |
| `RestOperateKey`  |                `ElementType.FIELD`                 |             the annotation is used to annotate a operate key of table.             |
|  `RestLogicKey`   |                `ElementType.FIELD`                 |              the annotation is used to annotate a logic key of table.              |
|   `RestExclude`   |                `ElementType.FIELD`                 |   the annotation is used to annotate a field of need to exclude for the entity.    |
|   `RestInsert`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |    the annotation is used to annotate a field of need to insert for the entity.    |
|   `RestUpdate`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |    the annotation is used to annotate a field of need to update for the entity.    |
|   `RestSelect`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |    the annotation is used to annotate a field of need to select for the entity.    |
| `RestForceInsert` | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to force insert for the entity. |
| `RestForceUpdate` | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` | the annotation is used to annotate a field of need to force update for the entity. |
|    `RestOrder`    | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |    the annotation is used to annotate a field of need to order for the entity.     |
|  `RestJdbcType`   | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |        the annotation is used to annotate a jdbc type of the entity field.         |
|  `RestSortType`   | `ElementType.FIELD`、 `ElementType.ANNOTATION_TYPE` |        the annotation is used to annotate a sort type of the entity field.         |

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
        BeanUtils.copyNonnullProperties(this, simpleModel);
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
        BeanUtils.copyNonnullProperties(this, templateModel);
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