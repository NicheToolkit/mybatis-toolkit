package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypeResolver;
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

/**
 * <p>MybatisTable</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Data
public class MybatisTable extends MybatisProperty<MybatisTable> {

    public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");

    public static final String DEFAULT_RESULT_MAP_NAME = "defaultResultMap";

    /** 原始表名 */
    protected String table;
    /** 实体类 */
    protected Class<?> entity;
    /** 联合主键 */
    protected List<String> unionKeys;
    /** 联合键忽略 */
    protected List<String> unionIgnoreKeys;
    /** 是否将主键添加到联合主键 */
    protected boolean unionIdentity;
    /** 是否将主键添加到联合主键 */
    protected boolean useUnionKey;
    /** 连接键 */
    protected List<String> linkKeys;
    /** 连接键忽略 */
    protected List<String> linkIgnoreKeys;
    /** 唯一键 */
    protected List<String> uniqueKeys;
    /** 唯一键忽略 */
    protected List<String> uniqueIgnoreKeys;
    /** 联合主键 */
    protected List<String> alertKeys;
    /** 联合键忽略 */
    protected List<String> alertIgnoreKeys;
    /** catalog 名称 */
    protected String catalog;
    /** schema 名称 */
    protected String schema;
    /** 名称规则样式名称 */
    protected String styleName;
    /** 名称规则样式类型 */
    protected StyleType styleType;
    /** 使用指定的 resultMap */
    protected String resultMap;
    /** 根据字段生成 resultMap */
    protected boolean autoResultMap;
    /** 初始化自动ResultMap */
    protected List<ResultMap> autoResultMaps;
    /** 排除指定字段名的字段 */
    protected List<String> excludeFields;
    /** 排除指定类型的字段 */
    protected List<Class<?>> excludeFieldTypes;
    /** 排除指定父类的所有字段 */
    protected List<Class<?>> excludeSuperClasses;
    /**排除指定键忽略 */
    protected List<String> excludeIgnoreKeys;
    /** 字段集合 */
    protected List<MybatisColumn> allColumns;
    /** 主键字段 */
    protected MybatisColumn identityColumn;
    /** 逻辑删除字段 */
    protected MybatisColumn logicColumn;
    /** 数据操作字段 */
    protected MybatisColumn operateColumn;
    /** 修改字段 */
    protected MybatisColumn alertColumn;
    /** 关联连接字段 */
    protected MybatisColumn linkColumn;
    /** 联合主键字段 */
    protected List<MybatisColumn> uniqueColumns;
    /** 联合主键字段 */
    protected List<MybatisColumn> unionColumns;
    /** 初始化完成，可以使用 */
    protected boolean ready;
    /** 已经初始化的配置 */
    protected Set<Configuration> initiates = new HashSet<>();
    /** 字段名称 */
    protected Map<String, MybatisColumn> fieldColumns = new HashMap<>();

    public MybatisTable(Class<?> entity) {
        this.entity = entity;
        this.allColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    public MybatisTable(Map<String, String> properties, Class<?> entity) {
        super(properties);
        this.entity = entity;
        this.allColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    public static MybatisTable of(Class<?> clazz) {
        return new MybatisTable(clazz);
    }

    public static MybatisTable of(Class<?> clazz, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisTable(properties, clazz);
        } else {
            return new MybatisTable(clazz);
        }
    }

    public String tableName() {
        return Stream.of(this.catalog, this.schema, this.table)
                .filter(GeneralUtils::isNotEmpty)
                .collect(Collectors.joining("."));
    }

    public List<MybatisField> fields() {
        return this.allColumns.stream().map(MybatisColumn::getField).collect(Collectors.toList());
    }

    public List<String> columnNames() {
        return this.allColumns.stream().map(MybatisColumn::getColumnName).collect(Collectors.toList());
    }

    public List<String> fieldNames() {
        return this.allColumns.stream().map(MybatisColumn::property).collect(Collectors.toList());
    }

    public void readyColumns() {
        List<MybatisColumn> identityColumns = new ArrayList<>();
        List<MybatisColumn> primaryKeyColumns = new ArrayList<>();
        List<MybatisColumn> logicColumns = new ArrayList<>();
        List<MybatisColumn> operateColumns = new ArrayList<>();
        List<MybatisColumn> linkKeyColumns = new ArrayList<>();
        List<MybatisColumn> alertKeyColumns = new ArrayList<>();
        this.allColumns.forEach(column -> {
            if (column.isIdentity()) {
                if (!identityColumns.contains(column)) {
                    identityColumns.add(0,column);
                } else {
                    identityColumns.remove(column);
                    identityColumns.add(0,column);
                }
            }
            if (column.isPrimaryKey()) {
                if (!primaryKeyColumns.contains(column)) {
                    primaryKeyColumns.add(0,column);
                } else {
                    primaryKeyColumns.remove(column);
                    primaryKeyColumns.add(0,column);
                }
            }
            if (column.isLogic()) {
                if (!logicColumns.contains(column)) {
                    logicColumns.add(0,column);
                } else {
                    logicColumns.remove(column);
                    logicColumns.add(0,column);
                }
            }
            if (column.isOperate()) {
                if (!operateColumns.contains(column)) {
                    operateColumns.add(0,column);
                } else {
                    linkKeyColumns.remove(column);
                    operateColumns.add(0,column);
                }
            }
            /** 如果是联合主键 */
            String fieldName = column.getField().fieldName();
            if (column.isUnionKey() || (GeneralUtils.isNotEmpty(this.unionKeys) && this.unionKeys.contains(fieldName))) {
                column.setUnionKey(true);
                if (!this.unionColumns.contains(column)) {
                    refreshColumn(this.unionColumns,column);
                } else {
                    this.unionColumns.remove(column);
                    refreshColumn(this.unionColumns,column);
                }
            }
            if (column.isUnionKey() && GeneralUtils.isNotEmpty(this.unionIgnoreKeys) && this.unionIgnoreKeys.contains(fieldName)) {
                column.setUnionKey(false);
                this.unionColumns.remove(column);
            }
            /** 如果是链接外键 */
            if (column.isLinkKey() || (GeneralUtils.isNotEmpty(this.linkKeys) && this.linkKeys.contains(fieldName))) {
                column.setLinkKey(true);
                if (!linkKeyColumns.contains(column)) {
                    linkKeyColumns.add(0,column);
                } else {
                    linkKeyColumns.remove(column);
                    linkKeyColumns.add(0,column);
                }
            }
            if (column.isLinkKey() && GeneralUtils.isNotEmpty(this.linkIgnoreKeys) && this.linkIgnoreKeys.contains(fieldName)) {
                column.setLinkKey(false);
                linkKeyColumns.remove(column);
            }
            /** 如果是链接外键 */
            if (column.isAlertKey() || (GeneralUtils.isNotEmpty(this.alertKeys) && this.alertKeys.contains(fieldName))) {
                column.setAlertKey(true);
                if (!alertKeyColumns.contains(column)) {
                    refreshColumn(alertKeyColumns,column);
                } else {
                    alertKeyColumns.remove(column);
                    refreshColumn(alertKeyColumns,column);
                }
            }
            if (column.isAlertKey() && GeneralUtils.isNotEmpty(this.alertIgnoreKeys) && this.alertIgnoreKeys.contains(fieldName)) {
                column.setAlertKey(false);
                alertKeyColumns.remove(column);
            }
            /** 如果是链接外键 */
            if (column.isUnique() || (GeneralUtils.isNotEmpty(this.uniqueKeys) && this.uniqueKeys.contains(fieldName))) {
                column.setUnique(true);
                if (!this.uniqueColumns.contains(column)) {
                    refreshColumn(this.uniqueColumns,column);
                } else {
                    this.uniqueColumns.remove(column);
                    refreshColumn(this.uniqueColumns,column);
                }
            }
            if (column.isUnique() && GeneralUtils.isNotEmpty(this.uniqueIgnoreKeys) && this.uniqueIgnoreKeys.contains(fieldName)) {
                column.setUnique(false);
                this.uniqueColumns.remove(column);
            }
        });
        Optional<MybatisColumn> firstIdentity = identityColumns.stream().findFirst();
        if (firstIdentity.isPresent() && GeneralUtils.isEmpty(this.identityColumn)) {
            this.identityColumn = firstIdentity.get();
        }
        Optional<MybatisColumn> firstPrimaryKey = primaryKeyColumns.stream().findFirst();
        if (firstPrimaryKey.isPresent() && GeneralUtils.isNotEmpty(this.identityColumn)) {
            this.identityColumn = firstPrimaryKey.get();
        }
        Optional<MybatisColumn> firstLinkKey = linkKeyColumns.stream().findFirst();
        if (firstLinkKey.isPresent() && GeneralUtils.isNotEmpty(this.linkColumn)) {
            this.linkColumn = firstLinkKey.get();
        }
        Optional<MybatisColumn> firstAlertKey = alertKeyColumns.stream().findFirst();
        if (firstAlertKey.isPresent() && GeneralUtils.isNotEmpty(this.alertColumn)) {
            this.alertColumn = firstAlertKey.get();
        }
        Optional<MybatisColumn> firstLogic = logicColumns.stream().findFirst();
        firstLogic.ifPresent(logicColumn -> this.logicColumn = logicColumn);
        Optional<MybatisColumn> firstOperate = operateColumns.stream().findFirst();
        firstOperate.ifPresent(operateColumn -> this.operateColumn = operateColumn);
        if (GeneralUtils.isNotEmpty(this.identityColumn) && this.unionIdentity && !this.unionColumns.contains(this.identityColumn)) {
            this.unionColumns.add(0, this.identityColumn);
        }
        if (GeneralUtils.isNotEmpty(this.unionColumns)) {
            this.useUnionKey = true;
        }
    }

    public void addColumn(MybatisColumn column) {
        /** 不重复添加同名的列 */
        if (!this.allColumns.contains(column)) {
            if (column.getField().declaringClass() != this.entity) {
                if (column.isForceInsert() || column.isForceUpdate() || column.isLogic() || column.isOperate()) {
                    this.allColumns.add(column);
                } else if (column.isUnionKey() || column.isLinkKey()) {
                    this.allColumns.add(1, column);
                } else {
                    this.allColumns.add(0, column);
                }
            } else {
                if (GeneralUtils.isValid(column.getOrder())) {
                    this.allColumns.add(column.getOrder(), column);
                } else {
                    this.allColumns.add(column);
                }
            }
            column.setTable(this);
        } else {
            /** 同名列在父类存在时，说明是子类覆盖的，字段的顺序应该更靠前 */
            MybatisColumn existsColumn = this.allColumns.remove(this.allColumns.indexOf(column));
            if (GeneralUtils.isValid(column.getOrder())) {
                this.allColumns.add(column.getOrder(), existsColumn);
            } else {
                this.allColumns.add(0, existsColumn);
            }
        }
    }

    protected void refreshColumn(List<MybatisColumn> columns, MybatisColumn column) {
        if (GeneralUtils.isValid(column.getUnionIndex())) {
            columns.add(column.getUnionIndex(),column);
        } else if (GeneralUtils.isValid(column.getOrder())) {
            columns.add(column.getOrder(),column);
        } else {
            columns.add(column);
        }
    }

    protected boolean canUseResultMaps(ProviderContext providerContext, String cacheKey) {
        if (this.autoResultMaps != null && !this.autoResultMaps.isEmpty()
                && providerContext.getMapperMethod().isAnnotationPresent(SelectProvider.class)) {
            Class<?> resultType = this.autoResultMaps.get(0).getType();
            /** 类型相同时直接返回 */
            if (resultType == providerContext.getMapperMethod().getReturnType()) {
                return true;
            }
            /** 可能存在泛型的情况，如 List<T>, Optional<T>, 还有 MyBatis 包含的一些注解 */
            Class<?> returnType = MybatisGenericTypeResolver.resolveMapperReturnType(
                    providerContext.getMapperMethod(), providerContext.getMapperType());
            return resultType == returnType;
        }
        return false;
    }

    /**
     * 当前实体类是否使用 resultMap
     * @return boolean
     */
    public boolean useResultMaps() {
        return this.autoResultMaps != null || this.autoResultMap || GeneralUtils.isNotEmpty(this.resultMap);
    }

    /**
     * 是否已经替换 resultMap
     * @param configuration MyBatis 配置类，慎重操作
     * @param cacheKey      缓存 key，每个方法唯一，默认和 msId 一样
     * @return boolean
     */
    protected boolean hasBeenReplaced(Configuration configuration, String cacheKey) {
        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
        if (mappedStatement.getResultMaps() != null && !mappedStatement.getResultMaps().isEmpty()) {
            return mappedStatement.getResultMaps().get(0) == this.autoResultMaps.get(0);
        }
        return false;
    }

    /**
     * 设置运行时信息，不同方法分别执行一次，需要保证幂等
     * @param configuration   MyBatis 配置类，慎重操作，多数据源或多个配置时，需要区分 Configuration 执行
     * @param providerContext 当前方法信息
     * @param cacheKey        缓存 key，每个方法唯一，默认和 msId 一样
     */
    public void initContext(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        /** 初始化一次，后续不会重复初始化 */
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
        /** 使用指定的 resultMap */
        if (GeneralUtils.isNotEmpty(this.resultMap)) {
            synchronized (this) {
                if (this.autoResultMaps == null) {
                    this.autoResultMaps = new ArrayList<>();
                    String resultMapId = generateResultMapId(providerContext, this.resultMap);
                    if (configuration.hasResultMap(resultMapId)) {
                        this.autoResultMaps.add(configuration.getResultMap(resultMapId));
                    } else if (configuration.hasResultMap(this.resultMap)) {
                        this.autoResultMaps.add(configuration.getResultMap(this.resultMap));
                    } else {
                        throw new ConfigureLackError(this.entity.getName() + " configured resultMap: " + this.resultMap + " not found");
                    }
                }
            }
        } else if (this.autoResultMap) {
            /** 自动生成 resultMap */
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

    protected String generateResultMapId(ProviderContext providerContext, String resultMapId) {
        if (resultMapId.indexOf(".") > 0) {
            return resultMapId;
        }
        return providerContext.getMapperType().getName() + "." + resultMapId;
    }

    protected ResultMap autoResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (MybatisColumn column : selectColumns()) {
            String columnName = column.getColumnName();
            /** 去掉可能存在的分隔符，例如：`order` */
            Matcher matcher = MybatisTable.DELIMITER.matcher(columnName);
            if (matcher.find()) {
                columnName = matcher.group(1);
            }
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, column.property(), columnName, column.javaType());
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
            if (column.isPrimaryKey() || column.isUnionKey() || column.isIdentity()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
        String resultMapId = generateResultMapId(providerContext, DEFAULT_RESULT_MAP_NAME);
        ResultMap.Builder builder = new ResultMap.Builder(configuration, resultMapId, this.entity, resultMappings, true);
        return builder.build();
    }


    /**
     * 实例化TypeHandler
     */
    public TypeHandler<?> getTypeHandlerInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
                return (TypeHandler<?>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
                // ignored
            } catch (Exception e) {
                throw new TypeException("Failed invoking constructor for handler " + typeHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerClass.getConstructor();
            return (TypeHandler<?>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("Unable to find a usable constructor for " + typeHandlerClass, e);
        }
    }

    /**
     * 返回查询列，默认所有列，当使用查询条件列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> allColumns() {
        return this.allColumns;
    }
    
    /**
     * 返回查询列，默认所有列，当使用查询条件列时，必须使用当前方法返回的列
     */
    public MybatisColumn fieldColumn(String fieldName) {
        if (GeneralUtils.isEmpty(this.fieldColumns)) {
            this.fieldColumns = this.allColumns.stream().collect(Collectors.toMap(MybatisColumn::fieldName, Function.identity()));
        }
        return this.fieldColumns.get(fieldName);
    }

    /**
     * 返回主键列，不会为空，当根据主键作为条件时，必须使用当前方法返回的列，没有设置主键时，当前方法返回所有列
     */
    
    public List<MybatisColumn> identityColumns() {
        if (GeneralUtils.isNotEmpty(this.unionColumns) && this.unionColumns.size() > 1) {
            return this.unionColumns;
        }
        return Collections.singletonList(this.identityColumn);
    }

    /**
     * 返回普通列，排除主键字段，当根据非主键作为条件时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> normalColumns() {
        return this.allColumns.stream().filter(column -> !column.isIdentity() && !column.isPrimaryKey()
                && !column.isUnionKey() && !column.isLogic() && !column.isOperate()).collect(Collectors.toList());
    }

    /**
     * 返回查询列，当获取查询列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> selectColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
    }

    /**
     * 返回查询列，默认所有列，当使用查询条件列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> whereColumns() {
        return this.allColumns;
    }

    public List<MybatisColumn> uniqueColumns() {
        return this.unionColumns;
    }

    /**
     * 所有 insert 用到的字段，当插入列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> insertColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isInsert).collect(Collectors.toList());
    }

    /**
     * 所有 update 用到的字段，当更新列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> updateColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isUpdate).collect(Collectors.toList());
    }

    /**
     * 所有 insert 用到的字段，当更新列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> forceInsertColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isForceInsert).collect(Collectors.toList());
    }

    /**
     * 所有 update 用到的字段，当更新列时，必须使用当前方法返回的列
     */
    public List<MybatisColumn> forceUpdateColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isForceUpdate).collect(Collectors.toList());
    }

    /**
     * 所有 GROUP BY 到的字段，默认为空，当使用 GROUP BY 列时，必须使用当前方法返回的列
     */
    public Optional<List<MybatisColumn>> groupByColumns() {
        return Optional.empty();
    }

    /**
     * 所有 HAVING 到的字段，默认为空，当使用 HAVING 列时，必须使用当前方法返回的列
     */
    public Optional<List<MybatisColumn>> havingColumns() {
        return Optional.empty();
    }

    /**
     * 所有排序用到的字段
     */
    public Optional<List<MybatisColumn>> orderByColumns() {
        List<MybatisColumn> orderByColumns = this.allColumns.stream()
                .filter(column -> GeneralUtils.isNotEmpty(column.getSortType()) && SortType.NONE != column.getSortType())
                .sorted(Comparator.comparing(MybatisColumn::getPriority))
                .collect(Collectors.toList());
        if (!orderByColumns.isEmpty()) {
            return Optional.of(orderByColumns);
        }
        return Optional.empty();
    }

    /**
     * 所有查询列，形如 column1, column2, ...
     */
    public String baseColumnList() {
        return selectColumns().stream().map(MybatisColumn::getColumnName).collect(Collectors.joining(", "));
    }

    /**
     * 所有查询列，形如 column1 AS property1, column2 AS property2, ...
     */
    public String baseColumnAsPropertyList() {
        //当存在 resultMaps 时，查询列不能用别名
        if (useResultMaps()) {
            return baseColumnList();
        }
        return selectColumns().stream().map(MybatisColumn::columnAsProperty).collect(Collectors.joining(", "));
    }

    /**
     * 所有 select 列，形如 column1, column2, ...，字段来源 {@link #selectColumns()}
     */
    public String selectColumnList() {
        return selectColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * 所有 insert 列，形如 column1, column2, ...，字段来源 {@link #insertColumns()}
     */
    public String insertColumnList() {
        return insertColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * 所有 主键或联合主键 用到的字段，当插入列时，必须使用当前方法返回的列
     */
    public String identityColumnList() {
        return identityColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * 所有 order by 字段，默认空，字段来源 {@link #groupByColumns()} 参考值: column1, column2, ...
     * <p>
     * 默认重写 {@link #groupByColumns()} 方法即可，当前方法不需要重写
     */
    public Optional<String> groupByColumnList() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::getColumnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * 带上 GROUP BY 前缀的方法，默认空，默认查询列来自 {@link #groupByColumnList()}
     * <p>
     * 默认重写 {@link #groupByColumns()} 方法即可，当前方法不需要重写
     */
    public Optional<String> groupByColumn() {
        Optional<String> groupByColumnList = groupByColumnList();
        return groupByColumnList.map(s -> " GROUP BY " + s);
    }

    /**
     * 所有 having 字段，默认空，字段来源 {@link #havingColumns()} 参考值: column1, column2, ...
     */
    public Optional<String> havingColumnList() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::getColumnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * 带上 HAVING 前缀的方法，默认空，默认查询列来自 {@link #havingColumnList()}
     */
    public Optional<String> havingColumn() {
        Optional<String> havingColumnList = havingColumnList();
        return havingColumnList.map(s -> " HAVING " + s);
    }

    /**
     * 所有 order by 字段，默认空，字段来源 {@link #orderByColumns()} 参考值: column1, column2, ...
     * <p>
     * 默认重写 {@link #orderByColumns()} 方法即可，当前方法不需要重写
     */
    public Optional<String> orderByColumnList() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.getColumnName() + " " + column.getSortType().getKey())
                .collect(Collectors.joining(", ")));
    }

    /**
     * 带上 ORDER BY 前缀的方法，默认空，默认查询列来自 {@link #orderByColumnList()}
     * <p>
     * 默认重写 {@link #orderByColumns()} 方法即可，当前方法不需要重写
     */
    public Optional<String> orderByColumn() {
        Optional<String> orderColumnList = orderByColumnList();
        return orderColumnList.map(s -> " ORDER BY " + s);
    }

    /**
     * 是否需要排除父类
     * @param superClass 父类
     * @return true - 需要排除，false - 不需要排除
     */
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

    /**
     * 是否需要排除指定的字段
     * @param field 字段
     * @return true - 需要排除，false - 不需要排除
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisTable)) return false;
        MybatisTable entity = (MybatisTable) o;
        return tableName().equals(entity.tableName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName());
    }

    @Override
    public String toString() {
        return tableName();
    }
}
