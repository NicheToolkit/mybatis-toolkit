package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypes;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.enums.SortType;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import lombok.Data;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
public class MybatisTable extends MybatisProperty<MybatisTable> {
    public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
    public static final String DEFAULT_RESULT_MAP_NAME = "defaultResultMap";
    protected String table;
    protected String alias;
    protected String comment;
    protected Class<?> entity;
    protected Class<?> identity;
    protected Class<?> linkage;
    protected Class<?> alertness;

    protected List<String> unionKeys;
    protected List<String> linkKeys;
    protected List<String> uniqueKeys;
    protected List<String> alertKeys;

    protected String catalog;
    protected String schema;
    protected String styleName;
    protected StyleType styleType;
    protected String resultMap;
    protected boolean autoResultMap = true;
    protected List<ResultMap> autoResultMaps;

    protected List<String> excludeFields;
    protected List<Class<?>> excludeFieldTypes;
    protected List<Class<?>> excludeSuperClasses;

    protected List<String> ignoreFields;
    protected List<Class<?>> ignoreFieldTypes;
    protected List<Class<?>> ignoreSuperClasses;

    protected List<MybatisColumn> tableColumns;
    protected List<MybatisColumn> identityColumns;

    protected List<MybatisColumn> forceInsertColumns;
    protected List<MybatisColumn> forceUpdateColumns;

    protected MybatisColumn logicColumn;
    protected MybatisColumn operateColumn;
    protected MybatisColumn alertColumn;
    protected MybatisColumn linkColumn;

    protected List<MybatisColumn> uniqueColumns;
    protected List<MybatisColumn> unionColumns;
    protected boolean ready;
    protected int identityIndex;
    protected Set<Configuration> initiates = new HashSet<>();
    protected Map<String, MybatisColumn> fieldColumns = new HashMap<>();

    public MybatisTable(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType) {
        this.entity = entityType;
        this.identity = identityType;
        this.linkage = linkageType;
        this.alertness = alertnessType;
        this.identityColumns = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
        this.forceInsertColumns = new ArrayList<>();
        this.forceUpdateColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    public MybatisTable(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType, Map<String, String> properties) {
        super(properties);
        this.entity = entityType;
        this.identity = identityType;
        this.linkage = linkageType;
        this.alertness = alertnessType;
        this.identityColumns = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
        this.forceInsertColumns = new ArrayList<>();
        this.forceUpdateColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    public static MybatisTable of(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType) {
        return new MybatisTable(entityType, identityType, linkageType, alertnessType);
    }

    public static MybatisTable of(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisTable(entityType, identityType, linkageType, alertnessType, properties);
        } else {
            return new MybatisTable(entityType, identityType, linkageType, alertnessType);
        }
    }

    public String tablename() {
        return Stream.of(this.catalog, this.schema, this.table)
                .filter(GeneralUtils::isNotEmpty)
                .collect(Collectors.joining(SQLConstants.PERIOD));
    }

    public String tablename(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename());
    }

    public String tablenameAsAlias(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename()) + SQLConstants.BLANK + SQLConstants.AS + SQLConstants.BLANK + this.alias;
    }

    protected void readyColumns() {
        List<MybatisColumn> identityColumns = new ArrayList<>();
        List<MybatisColumn> identityKeyColumns = new ArrayList<>();
        List<MybatisColumn> primaryKeyColumns = new ArrayList<>();
        List<MybatisColumn> alertKeyColumns = new ArrayList<>();
        List<MybatisColumn> linkKeyColumns = new ArrayList<>();
        List<MybatisColumn> logicKeyColumns = new ArrayList<>();
        List<MybatisColumn> operateKeyColumns = new ArrayList<>();
        this.tableColumns.forEach(column -> {
            if (column.isForceInsert()) {
                refreshColumn(this.forceInsertColumns, column);
            }
            if (column.isForceUpdate()) {
                refreshColumn(this.forceUpdateColumns, column);
            }
            if (column.isIdentity()) {
                identityColumns.remove(column);
                identityColumns.add(0, column);
            }
            if (column.isIdentityKey()) {
                identityKeyColumns.remove(column);
                identityKeyColumns.add(0, column);
            }
            if (column.isPrimaryKey()) {
                primaryKeyColumns.remove(column);
                primaryKeyColumns.add(0, column);
            }
            if (column.isLogicKey()) {
                logicKeyColumns.remove(column);
                logicKeyColumns.add(0, column);
            }
            if (column.isOperateKey()) {
                operateKeyColumns.remove(column);
                operateKeyColumns.add(0, column);
            }
            /* 如果是联合主键 */
            String fieldName = column.getField().fieldName();
            if (column.isUnionKey() || (GeneralUtils.isNotEmpty(this.unionKeys) && this.unionKeys.contains(fieldName))) {
                column.setUnionKey(true);
                this.unionColumns.remove(column);
                refreshColumn(this.unionColumns, column);
            }
            /* 如果是链接外键 */
            if (column.isLinkKey() || (GeneralUtils.isNotEmpty(this.linkKeys) && this.linkKeys.contains(fieldName))) {
                column.setLinkKey(true);
                linkKeyColumns.remove(column);
                linkKeyColumns.add(0, column);
            }
            /* 如果是修改外键 */
            if (column.isAlertKey() || (GeneralUtils.isNotEmpty(this.alertKeys) && this.alertKeys.contains(fieldName))) {
                column.setAlertKey(true);
                alertKeyColumns.remove(column);
                alertKeyColumns.add(0, column);
            }
            /* 如果是链接外键 */
            if (column.isUniqueKey() || (GeneralUtils.isNotEmpty(this.uniqueKeys) && this.uniqueKeys.contains(fieldName))) {
                column.setUniqueKey(true);
                this.uniqueColumns.remove(column);
                this.uniqueColumns.add(0, column);
            }
            if (column.isIdentity() && (column.isPrimaryKey() || column.isIdentityKey() || column.isUnionKey())) {
                this.identityColumns.remove(column);
                refreshColumn(this.identityColumns, column);
            }
        });

        Optional<MybatisColumn> firstLogic = logicKeyColumns.stream().findFirst();
        firstLogic.ifPresent(logicKeyColumn -> this.logicColumn = logicKeyColumn);
        Optional<MybatisColumn> firstOperate = operateKeyColumns.stream().findFirst();
        firstOperate.ifPresent(operateKeyColumn -> this.operateColumn = operateKeyColumn);
        Optional<MybatisColumn> firstAlert = alertKeyColumns.stream().findFirst();
        firstAlert.ifPresent(alertKeyColumn -> this.alertColumn = alertKeyColumn);
        Optional<MybatisColumn> firstLink = linkKeyColumns.stream().findFirst();
        firstLink.ifPresent(linkKeyColumn -> this.linkColumn = linkKeyColumn);
        /*
         * identityColumns
         * 优先级别: RestIdentity > RestPrimaryKey > RestIdentityKey > RestUnionKey
         */
        if (!isIdentity()) {
            /* RestIdentityKey  */
            Optional<MybatisColumn> firstIdentity = identityKeyColumns.stream().findFirst();
            firstIdentity.ifPresent(mybatisColumn -> this.identityColumns = Collections.singletonList(mybatisColumn));
            /* RestPrimaryKey  */
            Optional<MybatisColumn> firstPrimaryKey = primaryKeyColumns.stream().findFirst();
            firstPrimaryKey.ifPresent(mybatisColumn -> this.identityColumns = Collections.singletonList(mybatisColumn));
        } else if (GeneralUtils.isEmpty(this.identityColumns)) {
            if (GeneralUtils.isNotEmpty(identityColumns)) {
                /* RestIdentity  */
                this.identityColumns = new ArrayList<>(identityColumns);
            } else {
                throw new MybatisIdentityLackError("the custom identity columns must be not empty, identity type: " + this.identity.getName());
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    protected void addColumn(MybatisColumn column) {
        /* 不重复添加同名的列 */
        if (!this.tableColumns.contains(column)) {
            /* 父类 字段处理 */
            if (column.getField().declaringClass() != this.entity) {
                if (column.isForceInsert() || column.isForceUpdate() || column.isLogicKey() || column.isOperateKey()) {
                    refreshColumn(this.tableColumns, column);
                } else if (column.isUnionKey() || column.isLinkKey()) {
                    /*  RestUnionKey 或者 RestLinkKey 放在 identity后面 */
                    refreshColumn(this.tableColumns, column, this.identityIndex);
                } else if (column.isIdentityKey() || column.isIdentity()) {
                    refreshColumn(this.tableColumns, column, 0);
                    /* 更新 identityIndex */
                    this.identityIndex++;
                } else {
                    refreshColumn(this.tableColumns, column, 0);
                }
            } else {
                /* 当前实体类 字段处理 */
                refreshColumn(this.tableColumns, column);
            }
            column.setTable(this);
        } else if (isIdentity()) {
            /* 同名列在存在, 为自定义主键类型覆盖实体类或父类，进行字段覆盖 */
            int columnIndex = this.tableColumns.indexOf(column);
            MybatisColumn existsColumn = this.tableColumns.remove(columnIndex);
            refreshColumn(this.tableColumns, column, columnIndex);
        } else {
            /* 同名列在存在，为子类覆盖父类，不做处理 */
        }
    }

    public boolean isIdentity() {
        return Optional.ofNullable(this.identity).isPresent();
    }

    public List<MybatisField> fields() {
        return this.tableColumns.stream().map(MybatisColumn::getField).collect(Collectors.toList());
    }

    public List<String> columnNames() {
        return this.tableColumns.stream().map(MybatisColumn::columnName).collect(Collectors.toList());
    }

    public List<String> columnAliasNames() {
        return this.tableColumns.stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.toList());
    }

    public List<String> fieldNames() {
        return this.tableColumns.stream().map(MybatisColumn::property).collect(Collectors.toList());
    }

    protected void refreshColumn(List<MybatisColumn> columns, MybatisColumn column) {
        refreshColumn(columns, column, null);
    }

    protected void refreshColumn(List<MybatisColumn> columns, MybatisColumn column, Integer index) {
        if (GeneralUtils.isNotEmpty(column.getOrder())) {
            columns.add(column.getOrder(), column);
        } else if (GeneralUtils.isValid(index)) {
            columns.add(index, column);
        } else {
            columns.add(column);
        }
    }

    protected boolean canUseResultMaps(ProviderContext providerContext, String cacheKey) {
        if (this.autoResultMaps != null && !this.autoResultMaps.isEmpty()
                && providerContext.getMapperMethod().isAnnotationPresent(SelectProvider.class)) {
            Class<?> resultType = this.autoResultMaps.get(0).getType();
            /* 类型相同时直接返回 */
            if (resultType == providerContext.getMapperMethod().getReturnType()) {
                return true;
            }
            /* 可能存在泛型的情况，如 List<T>, Optional<T>, 还有 MyBatis 包含的一些注解 */
            Class<?> returnType = MybatisGenericTypes.resolveMapperReturnType(
                    providerContext.getMapperMethod(), providerContext.getMapperType());
            return resultType == returnType;
        }
        return false;
    }

    public boolean useResultMap() {
        return this.autoResultMaps != null || this.autoResultMap || GeneralUtils.isNotEmpty(this.resultMap);
    }

    protected boolean hasBeenReplaced(Configuration configuration, String cacheKey) {
        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
        if (mappedStatement.getResultMaps() != null && !mappedStatement.getResultMaps().isEmpty()) {
            return mappedStatement.getResultMaps().get(0) == this.autoResultMaps.get(0);
        }
        return false;
    }

    @SuppressWarnings("all")
    public void initContext(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        /* 初始化一次，后续不会重复初始化 */
        if (!this.initiates.contains(configuration)) {
            initResultMap(configuration, providerContext, cacheKey);
            this.initiates.add(configuration);
        }
        if (canUseResultMaps(providerContext, cacheKey)) {
            synchronized (cacheKey) {
                if (!hasBeenReplaced(configuration, cacheKey)) {
                    MetaObject metaObject = configuration.newMetaObject(configuration.getMappedStatement(cacheKey));
                    metaObject.setValue("resultMaps", Collections.unmodifiableList(this.autoResultMaps));
                }
            }
        }
    }

    protected void initResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        /* 使用指定的 resultMap */
        if (GeneralUtils.isNotEmpty(this.resultMap)) {
            synchronized (this) {
                if (this.autoResultMaps == null) {
                    this.autoResultMaps = new ArrayList<>();
                    String resultMapId = resultMapId(providerContext, this.resultMap);
                    if (configuration.hasResultMap(resultMapId)) {
                        this.autoResultMaps.add(configuration.getResultMap(resultMapId));
                    } else if (configuration.hasResultMap(this.resultMap)) {
                        this.autoResultMaps.add(configuration.getResultMap(this.resultMap));
                    } else {
                        throw new ConfigureLackError(this.entity.getName() + " configured result map: " + this.resultMap + " not found");
                    }
                }
            }
        } else if (this.autoResultMap) {
            /* 自动生成 resultMap */
            synchronized (this) {
                if (this.autoResultMaps == null) {
                    this.autoResultMaps = new ArrayList<>();
                    ResultMap resultMap = autoResultMap(configuration, providerContext, cacheKey);
                    this.autoResultMaps.add(resultMap);
                    configuration.addResultMap(resultMap);
                }
            }
        }
    }

    protected String resultMapId(ProviderContext providerContext, String resultMapId) {
        if (resultMapId.indexOf(SQLConstants.PERIOD) > 0) {
            return resultMapId;
        }
        return providerContext.getMapperType().getName() + SQLConstants.PERIOD + resultMapId;
    }

    protected ResultMap autoResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (MybatisColumn column : selectColumns()) {
            String columnName = column.columnName();
            /* 去掉可能存在的分隔符，例如：`order` */
            Matcher matcher = MybatisTable.DELIMITER.matcher(columnName);
            if (matcher.find()) {
                columnName = matcher.group(1);
            }
            String property = column.property();
            if (column.isIdentity()) {
                property = column.property(EntityConstants.IDENTITY + SQLConstants.PERIOD);
            }
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, property, columnName, column.javaType());
            if (column.getJdbcType() != null && column.getJdbcType() != JdbcType.UNDEFINED) {
                builder.jdbcType(column.getJdbcType());
            }
            if (column.getTypeHandler() != null && column.getTypeHandler() != UnknownTypeHandler.class) {
                try {
                    builder.typeHandler(getTypeHandlerInstance(column.javaType(), column.getTypeHandler()));
                } catch (TypeException exception) {
                    throw new ConfigureLackError(exception);
                }
            }
            List<ResultFlag> flags = new ArrayList<>();
            if (column.isPrimaryKey() || column.isUnionKey() || column.isIdentityKey()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
        String resultMapId = resultMapId(providerContext, DEFAULT_RESULT_MAP_NAME);
        ResultMap.Builder builder = new ResultMap.Builder(configuration, resultMapId, this.entity, resultMappings, true);
        return builder.build();
    }


    public TypeHandler<?> getTypeHandlerInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
                return (TypeHandler<?>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                throw new TypeException("failed invoking constructor for handler " + typeHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerClass.getConstructor();
            return (TypeHandler<?>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("unable to find a usable constructor for " + typeHandlerClass, e);
        }
    }

    public List<MybatisColumn> tableColumns() {
        return this.tableColumns;
    }

    public MybatisColumn fieldColumn(String fieldName) {
        if (GeneralUtils.isEmpty(this.fieldColumns)) {
            this.fieldColumns = this.tableColumns.stream().collect(Collectors.toMap(MybatisColumn::fieldName, Function.identity()));
        }
        return this.fieldColumns.get(fieldName);
    }

    public List<MybatisColumn> identityColumns() {
        return this.identityColumns;
    }

    public List<MybatisColumn> normalColumns() {
        return this.tableColumns.stream().filter(column -> !column.isAnyKey()).collect(Collectors.toList());
    }

    public List<MybatisColumn> selectColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
    }

    public List<MybatisColumn> whereColumns() {
        return this.tableColumns;
    }

    public List<MybatisColumn> uniqueColumns() {
        return this.unionColumns;
    }

    public List<MybatisColumn> insertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isInsert).collect(Collectors.toList());
    }

    public List<MybatisColumn> updateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isUpdate).collect(Collectors.toList());
    }

    public List<MybatisColumn> forceInsertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceInsert).collect(Collectors.toList());
    }

    public List<MybatisColumn> forceUpdateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceUpdate).collect(Collectors.toList());
    }

    public Optional<List<MybatisColumn>> groupByColumns() {
        return Optional.empty();
    }

    public Optional<List<MybatisColumn>> havingColumns() {
        return Optional.empty();
    }

    public Optional<List<MybatisColumn>> orderByColumns() {
        List<MybatisColumn> orderByColumns = this.tableColumns.stream()
                .filter(column -> GeneralUtils.isNotEmpty(column.getSortType()) && SortType.NONE != column.getSortType())
                .sorted(Comparator.comparing(MybatisColumn::getPriority))
                .collect(Collectors.toList());
        if (!orderByColumns.isEmpty()) {
            return Optional.of(orderByColumns);
        }
        return Optional.empty();
    }

    public String sqlOfIdentityColumn() {
        Optional<MybatisColumn> columnOptional = this.identityColumns.stream().findFirst();
        if (columnOptional.isPresent()) {
            return columnOptional.get().columnEqualsProperty();
        }
        return SQLConstants.Empty;
    }

    public String sqlOfIdentityAliasColumn() {
        Optional<MybatisColumn> columnOptional = this.identityColumns.stream().findFirst();
        if (columnOptional.isPresent()) {
            return columnOptional.get().aliasColumnEqualsProperty(this.alias);
        }
        return SQLConstants.Empty;
    }

    public String sqlOfBaseColumns() {
        return selectColumns().stream().map(MybatisColumn::columnName).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfBaseAliasColumns() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String asSqlOfBaseColumns() {
        if (useResultMap()) {
            return sqlOfBaseColumns();
        }
        return selectColumns().stream().map(MybatisColumn::columnAsProperty).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String asSqlOfBaseAliasColumns() {
        if (useResultMap()) {
            return sqlOfBaseColumns();
        }
        return selectColumns().stream().map(column -> column.aliasColumnAsProperty(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfSelectColumns() {
        return selectColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfSelectAliasColumns() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfInsertColumns() {
        return insertColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfInsertAliasColumns() {
        return insertColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfIdentityColumns() {
        return identityColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public String sqlOfIdentityAliasColumns() {
        return identityColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    public Optional<String> sqlOfGroupByColumns() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfGroupByAliasColumns() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfGroupBy() {
        Optional<String> groupByColumnList = sqlOfGroupByColumns();
        return groupByColumnList.map(s -> SQLConstants.BLANK + SQLConstants.GROUP_BY + SQLConstants.BLANK + s);
    }

    public Optional<String> sqlOfGroupByAlias() {
        Optional<String> groupByColumnList = sqlOfGroupByAliasColumns();
        return groupByColumnList.map(s -> SQLConstants.BLANK + SQLConstants.GROUP_BY + SQLConstants.BLANK + s);
    }

    public Optional<String> sqlOfHavingColumns() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfHavingAliasColumns() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfHaving() {
        Optional<String> havingColumnList = sqlOfHavingColumns();
        return havingColumnList.map(s -> SQLConstants.BLANK + SQLConstants.HAVING + SQLConstants.BLANK + s);
    }

    public Optional<String> sqlOfHavingAlias() {
        Optional<String> havingColumnList = sqlOfHavingAliasColumns();
        return havingColumnList.map(s -> SQLConstants.BLANK + SQLConstants.HAVING + SQLConstants.BLANK + s);
    }

    public Optional<String> sqlOfOrderByColumns() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.columnName() + SQLConstants.BLANK + column.getSortType().getKey())
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfOrderByAliasColumns() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.aliasColumn(this.alias) + SQLConstants.BLANK + column.getSortType().getKey())
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    public Optional<String> sqlOfOrderBy() {
        Optional<String> orderColumnList = sqlOfOrderByColumns();
        return orderColumnList.map(s -> SQLConstants.BLANK + SQLConstants.ORDER_BY + SQLConstants.BLANK + s);
    }

    public Optional<String> sqlOfOrderByAlias() {
        Optional<String> orderColumnList = sqlOfOrderByAliasColumns();
        return orderColumnList.map(s -> SQLConstants.BLANK + SQLConstants.ORDER_BY + SQLConstants.BLANK + s);
    }

    public boolean isExcludeSuperClass(Class<?> superClass) {
        if (this.excludeSuperClasses != null) {
            for (Class<?> clazz : this.excludeSuperClasses) {
                if (clazz == superClass) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isExcludeField(MybatisField field) {
        if (this.excludeFieldTypes != null) {
            Class<?> fieldType = field.fieldType();
            for (Class<?> clazz : this.excludeFieldTypes) {
                if (clazz == fieldType) {
                    return true;
                }
            }
        }
        if (this.excludeFields != null) {
            String fieldName = field.fieldName();
            for (String excludeField : this.excludeFields) {
                if (excludeField.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isIgnoreSuperClass(Class<?> superClass) {
        if (this.ignoreSuperClasses != null) {
            for (Class<?> clazz : this.ignoreSuperClasses) {
                if (clazz == superClass) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isIgnoreField(MybatisField field) {
        if (this.ignoreFieldTypes != null) {
            Class<?> fieldType = field.fieldType();
            for (Class<?> clazz : this.ignoreFieldTypes) {
                if (clazz == fieldType) {
                    return true;
                }
            }
        }
        if (this.ignoreFields != null) {
            String fieldName = field.fieldName();
            for (String excludeField : this.ignoreFields) {
                if (excludeField.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisTable)) return false;
        MybatisTable entity = (MybatisTable) o;
        return tablename().equals(entity.tablename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tablename());
    }

    @Override
    public String toString() {
        return tablename();
    }
}
