package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
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
 * <code>MybatisTable</code>
 * <p>The type mybatis table class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProperty
 * @see lombok.Data
 * @since Jdk1.8
 */
@Data
public class MybatisTable extends MybatisProperty<MybatisTable> {
    /**
     * <code>DELIMITER</code>
     * {@link java.util.regex.Pattern} <p>the constant <code>DELIMITER</code> field.</p>
     * @see java.util.regex.Pattern
     */
    public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
    /**
     * <code>DEFAULT_RESULT_MAP_NAME</code>
     * {@link java.lang.String} <p>the constant <code>DEFAULT_RESULT_MAP_NAME</code> field.</p>
     * @see java.lang.String
     */
    public static final String DEFAULT_RESULT_MAP_NAME = "defaultResultMap";
    /**
     * <code>table</code>
     * {@link java.lang.String} <p>the <code>table</code> field.</p>
     * @see java.lang.String
     */
    protected String table;
    /**
     * <code>alias</code>
     * {@link java.lang.String} <p>the <code>alias</code> field.</p>
     * @see java.lang.String
     */
    protected String alias;
    /**
     * <code>comment</code>
     * {@link java.lang.String} <p>the <code>comment</code> field.</p>
     * @see java.lang.String
     */
    protected String comment;
    /**
     * <code>entity</code>
     * {@link java.lang.Class} <p>the <code>entity</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<?> entity;
    /**
     * <code>identity</code>
     * {@link java.lang.Class} <p>the <code>identity</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<?> identity;
    /**
     * <code>unionKeys</code>
     * {@link java.util.List} <p>the <code>unionKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> unionKeys;
    /**
     * <code>unionIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>unionIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> unionIgnoreKeys;
    /**
     * <code>linkKeys</code>
     * {@link java.util.List} <p>the <code>linkKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> linkKeys;
    /**
     * <code>linkIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>linkIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> linkIgnoreKeys;
    /**
     * <code>uniqueKeys</code>
     * {@link java.util.List} <p>the <code>uniqueKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> uniqueKeys;
    /**
     * <code>uniqueIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>uniqueIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> uniqueIgnoreKeys;
    /**
     * <code>alertKeys</code>
     * {@link java.util.List} <p>the <code>alertKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> alertKeys;
    /**
     * <code>alertIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>alertIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> alertIgnoreKeys;
    /**
     * <code>catalog</code>
     * {@link java.lang.String} <p>the <code>catalog</code> field.</p>
     * @see java.lang.String
     */
    protected String catalog;
    /**
     * <code>schema</code>
     * {@link java.lang.String} <p>the <code>schema</code> field.</p>
     * @see java.lang.String
     */
    protected String schema;
    /**
     * <code>styleName</code>
     * {@link java.lang.String} <p>the <code>styleName</code> field.</p>
     * @see java.lang.String
     */
    protected String styleName;
    /**
     * <code>styleType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the <code>styleType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     */
    protected StyleType styleType;
    /**
     * <code>resultMap</code>
     * {@link java.lang.String} <p>the <code>resultMap</code> field.</p>
     * @see java.lang.String
     */
    protected String resultMap;
    /**
     * <code>autoResultMap</code>
     * <p>the <code>autoResultMap</code> field.</p>
     */
    protected boolean autoResultMap;
    /**
     * <code>autoResultMaps</code>
     * {@link java.util.List} <p>the <code>autoResultMaps</code> field.</p>
     * @see java.util.List
     */
    protected List<ResultMap> autoResultMaps;
    /**
     * <code>excludeFields</code>
     * {@link java.util.List} <p>the <code>excludeFields</code> field.</p>
     * @see java.util.List
     */
    protected List<String> excludeFields;
    /**
     * <code>excludeFieldTypes</code>
     * {@link java.util.List} <p>the <code>excludeFieldTypes</code> field.</p>
     * @see java.util.List
     */
    protected List<Class<?>> excludeFieldTypes;
    /**
     * <code>excludeSuperClasses</code>
     * {@link java.util.List} <p>the <code>excludeSuperClasses</code> field.</p>
     * @see java.util.List
     */
    protected List<Class<?>> excludeSuperClasses;
    /**
     * <code>excludeIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>excludeIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> excludeIgnoreKeys;
    /**
     * <code>tableColumns</code>
     * {@link java.util.List} <p>the <code>tableColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> tableColumns;
    /**
     * <code>identityColumns</code>
     * {@link java.util.List} <p>the <code>identityColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> identityColumns;
    /**
     * <code>logicColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>logicColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn logicColumn;
    /**
     * <code>operateColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>operateColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn operateColumn;
    /**
     * <code>alertColumns</code>
     * {@link java.util.List} <p>the <code>alertColumns</code> field.</p>
     * @see java.util.List
     */
    protected MybatisColumn alertColumn;
    /**
     * <code>linkColumns</code>
     * {@link java.util.List} <p>the <code>linkColumns</code> field.</p>
     * @see java.util.List
     */
    protected MybatisColumn linkColumn;
    /**
     * <code>uniqueColumns</code>
     * {@link java.util.List} <p>the <code>uniqueColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> uniqueColumns;
    /**
     * <code>unionColumns</code>
     * {@link java.util.List} <p>the <code>unionColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> unionColumns;
    /**
     * <code>ready</code>
     * <p>the <code>ready</code> field.</p>
     */
    protected boolean ready;
    /**
     * <code>identityIndex</code>
     * <p>the <code>identityIndex</code> field.</p>
     */
    protected int identityIndex;
    /**
     * <code>initiates</code>
     * {@link java.util.Set} <p>the <code>initiates</code> field.</p>
     * @see java.util.Set
     */
    protected Set<Configuration> initiates = new HashSet<>();
    /**
     * <code>fieldColumns</code>
     * {@link java.util.Map} <p>the <code>fieldColumns</code> field.</p>
     * @see java.util.Map
     */
    protected Map<String, MybatisColumn> fieldColumns = new HashMap<>();

    /**
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param entityType   {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity type parameter is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public MybatisTable(Class<?> entityType, Class<?> identityType) {
        this.entity = entityType;
        this.identity = identityType;
        this.identityColumns = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    /**
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param entityType   {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity type parameter is <code>Class</code> type.</p>
     * @param properties   {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Map
     */
    public MybatisTable(Class<?> entityType, Class<?> identityType, Map<String, String> properties) {
        super(properties);
        this.entity = entityType;
        this.identity = identityType;
        this.identityColumns = new ArrayList<>();
        this.tableColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param entityType   {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity type parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     */
    public static MybatisTable of(Class<?> entityType, Class<?> identityType) {
        return new MybatisTable(entityType, identityType);
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param entityType   {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>the identity type parameter is <code>Class</code> type.</p>
     * @param properties   {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Map
     */
    public static MybatisTable of(Class<?> entityType, Class<?> identityType, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisTable(entityType, identityType, properties);
        } else {
            return new MybatisTable(entityType, identityType);
        }
    }

    /**
     * <code>tablename</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String tablename() {
        return Stream.of(this.catalog, this.schema, this.table)
                .filter(GeneralUtils::isNotEmpty)
                .collect(Collectors.joining("."));
    }

    /**
     * <code>tablename</code>
     * <p>the method.</p>
     * @param tablename {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String tablename(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename());
    }

    /**
     * <code>tablenameAsAlias</code>
     * <p>the as alias method.</p>
     * @param tablename {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the as alias return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String tablenameAsAlias(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename()) + " AS " + this.alias;
    }

    /**
     * <code>readyColumns</code>
     * <p>the columns method.</p>
     */
    protected void readyColumns() {
        List<MybatisColumn> identityColumns = new ArrayList<>();
        List<MybatisColumn> identityKeyColumns = new ArrayList<>();
        List<MybatisColumn> primaryKeyColumns = new ArrayList<>();
        List<MybatisColumn> alertKeyColumns = new ArrayList<>();
        List<MybatisColumn> linkKeyColumns = new ArrayList<>();
        List<MybatisColumn> logicKeyColumns = new ArrayList<>();
        List<MybatisColumn> operateKeyColumns = new ArrayList<>();
        this.tableColumns.forEach(column -> {
            if (column.isIdentityColumn()) {
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
                refreshUnionColumn(this.unionColumns, column);
            }
            if (column.isUnionKey() && GeneralUtils.isNotEmpty(this.unionIgnoreKeys) && this.unionIgnoreKeys.contains(fieldName)) {
                column.setUnionKey(false);
                this.unionColumns.remove(column);
            }
            /* 如果是链接外键 */
            if (column.isLinkKey() || (GeneralUtils.isNotEmpty(this.linkKeys) && this.linkKeys.contains(fieldName))) {
                column.setLinkKey(true);
                linkKeyColumns.remove(column);
                linkKeyColumns.add(0, column);
            }
            if (column.isLinkKey() && GeneralUtils.isNotEmpty(this.linkIgnoreKeys) && this.linkIgnoreKeys.contains(fieldName)) {
                column.setLinkKey(false);
                linkKeyColumns.remove(column);
            }
            /* 如果是修改外键 */
            if (column.isAlertKey() || (GeneralUtils.isNotEmpty(this.alertKeys) && this.alertKeys.contains(fieldName))) {
                column.setAlertKey(true);
                alertKeyColumns.remove(column);
                alertKeyColumns.add(0, column);
            }
            if (column.isAlertKey() && GeneralUtils.isNotEmpty(this.alertIgnoreKeys) && this.alertIgnoreKeys.contains(fieldName)) {
                column.setAlertKey(false);
                alertKeyColumns.remove(column);
            }
            /* 如果是链接外键 */
            if (column.isUnique() || (GeneralUtils.isNotEmpty(this.uniqueKeys) && this.uniqueKeys.contains(fieldName))) {
                column.setUnique(true);
                this.uniqueColumns.remove(column);
                this.uniqueColumns.add(0, column);
            }
            if (column.isUnique() && GeneralUtils.isNotEmpty(this.uniqueIgnoreKeys) && this.uniqueIgnoreKeys.contains(fieldName)) {
                column.setUnique(false);
                this.uniqueColumns.remove(column);
            }
            if (column.isIdentityColumn() && (column.isPrimaryKey() || column.isIdentityKey() || column.isUnionKey())) {
                this.identityColumns.remove(column);
                refreshUnionColumn(this.identityColumns, column);
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
        if (!isCustomIdentity()) {
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

    /**
     * <code>addColumn</code>
     * <p>the column method.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected void addColumn(MybatisColumn column) {
        /* 不重复添加同名的列 */
        if (!this.tableColumns.contains(column)) {
            /* 父类 字段处理 */
            if (column.getField().declaringClass() != this.entity) {
                if (column.isForceInsert() || column.isForceUpdate() || column.isLogicKey() || column.isOperateKey()) {
                    refreshOrderColumn(this.tableColumns, column);
                } else if (column.isUnionKey() || column.isLinkKey()) {
                    /*  RestUnionKey 或者 RestLinkKey 放在 identity后面 */
                    refreshOrderColumn(this.tableColumns, column, this.identityIndex);
                } else if (column.isIdentityKey() || column.isIdentityColumn()) {
                    refreshOrderColumn(this.tableColumns, column, 0);
                    /* 更新 identityIndex */
                    this.identityIndex++;
                } else {
                    refreshOrderColumn(this.tableColumns, column, 0);
                }
            } else {
                /* 当前实体类 字段处理 */
                refreshOrderColumn(this.tableColumns, column);
            }
            column.setTable(this);
        } else if (isCustomIdentity()) {
            /* 同名列在存在, 为自定义主键类型覆盖实体类或父类，进行字段覆盖 */
            int columnIndex = this.tableColumns.indexOf(column);
            MybatisColumn existsColumn = this.tableColumns.remove(columnIndex);
            refreshOrderColumn(this.tableColumns, column, columnIndex);
        } else {
            /* 同名列在存在，为子类覆盖父类，不做处理 */
        }
    }

    public boolean isCustomIdentity() {
        return Optional.ofNullable(this.identity).isPresent();
    }


    /**
     * <code>fields</code>
     * <p>the method.</p>
     * @return {@link java.util.List} <p>the return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisField> fields() {
        return this.tableColumns.stream().map(MybatisColumn::getField).collect(Collectors.toList());
    }

    /**
     * <code>columnNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> columnNames() {
        return this.tableColumns.stream().map(MybatisColumn::columnName).collect(Collectors.toList());
    }

    /**
     * <code>aliasColumnNames</code>
     * <p>the column names method.</p>
     * @return {@link java.util.List} <p>the column names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> aliasColumnNames() {
        return this.tableColumns.stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.toList());
    }

    /**
     * <code>fieldNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> fieldNames() {
        return this.tableColumns.stream().map(MybatisColumn::property).collect(Collectors.toList());
    }

    /**
     * <code>refreshOrderColumn</code>
     * <p>the order column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected void refreshOrderColumn(List<MybatisColumn> columns, MybatisColumn column) {
        refreshOrderColumn(columns, column, null);
    }

    /**
     * <code>refreshOrderColumn</code>
     * <p>the order column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @param index   {@link java.lang.Integer} <p>the index parameter is <code>Integer</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     * @see java.lang.Integer
     */
    protected void refreshOrderColumn(List<MybatisColumn> columns, MybatisColumn column, Integer index) {
        if (GeneralUtils.isNotEmpty(column.getOrder())) {
            columns.add(column.getOrder(), column);
        } else if (GeneralUtils.isValid(index)) {
            columns.add(index, column);
        } else {
            columns.add(column);
        }
    }


    /**
     * <code>refreshUnionColumn</code>
     * <p>the union column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected void refreshUnionColumn(List<MybatisColumn> columns, MybatisColumn column) {
        refreshUnionColumn(columns, column, null);
    }

    /**
     * <code>refreshUnionColumn</code>
     * <p>the union column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @param index   {@link java.lang.Integer} <p>the index parameter is <code>Integer</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     * @see java.lang.Integer
     */
    protected void refreshUnionColumn(List<MybatisColumn> columns, MybatisColumn column, Integer index) {
        if (GeneralUtils.isNotEmpty(column.getUnionIndex())) {
            columns.add(column.getUnionIndex(), column);
        } else if (GeneralUtils.isNotEmpty(column.getOrder())) {
            columns.add(column.getOrder(), column);
        } else if (GeneralUtils.isNotEmpty(index)) {
            columns.add(index, column);
        } else {
            columns.add(column);
        }
    }

    /**
     * <code>canUseResultMaps</code>
     * <p>the use result maps method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey        {@link java.lang.String} <p>the cache key parameter is <code>String</code> type.</p>
     * @return boolean <p>the use result maps return object is <code>boolean</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     */
    protected boolean canUseResultMaps(ProviderContext providerContext, String cacheKey) {
        if (this.autoResultMaps != null && !this.autoResultMaps.isEmpty()
                && providerContext.getMapperMethod().isAnnotationPresent(SelectProvider.class)) {
            Class<?> resultType = this.autoResultMaps.get(0).getType();
            /* 类型相同时直接返回 */
            if (resultType == providerContext.getMapperMethod().getReturnType()) {
                return true;
            }
            /* 可能存在泛型的情况，如 List<T>, Optional<T>, 还有 MyBatis 包含的一些注解 */
            Class<?> returnType = MybatisGenericTypeResolver.resolveMapperReturnType(
                    providerContext.getMapperMethod(), providerContext.getMapperType());
            return resultType == returnType;
        }
        return false;
    }

    /**
     * <code>useResultMaps</code>
     * <p>the result maps method.</p>
     * @return boolean <p>the result maps return object is <code>boolean</code> type.</p>
     */
    public boolean useResultMaps() {
        return this.autoResultMaps != null || this.autoResultMap || GeneralUtils.isNotEmpty(this.resultMap);
    }

    /**
     * <code>hasBeenReplaced</code>
     * <p>the been replaced method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>the configuration parameter is <code>Configuration</code> type.</p>
     * @param cacheKey      {@link java.lang.String} <p>the cache key parameter is <code>String</code> type.</p>
     * @return boolean <p>the been replaced return object is <code>boolean</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see java.lang.String
     */
    protected boolean hasBeenReplaced(Configuration configuration, String cacheKey) {
        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
        if (mappedStatement.getResultMaps() != null && !mappedStatement.getResultMaps().isEmpty()) {
            return mappedStatement.getResultMaps().get(0) == this.autoResultMaps.get(0);
        }
        return false;
    }

    /**
     * <code>initContext</code>
     * <p>the context method.</p>
     * @param configuration   {@link org.apache.ibatis.session.Configuration} <p>the configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey        {@link java.lang.String} <p>the cache key parameter is <code>String</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     */
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

    /**
     * <code>initResultMap</code>
     * <p>the result map method.</p>
     * @param configuration   {@link org.apache.ibatis.session.Configuration} <p>the configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey        {@link java.lang.String} <p>the cache key parameter is <code>String</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     */
    protected void initResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        /* 使用指定的 resultMap */
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

    /**
     * <code>generateResultMapId</code>
     * <p>the result map id method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param resultMapId     {@link java.lang.String} <p>the result map id parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the result map id return object is <code>String</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     */
    protected String generateResultMapId(ProviderContext providerContext, String resultMapId) {
        if (resultMapId.indexOf(".") > 0) {
            return resultMapId;
        }
        return providerContext.getMapperType().getName() + "." + resultMapId;
    }

    /**
     * <code>autoResultMap</code>
     * <p>the result map method.</p>
     * @param configuration   {@link org.apache.ibatis.session.Configuration} <p>the configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey        {@link java.lang.String} <p>the cache key parameter is <code>String</code> type.</p>
     * @return {@link org.apache.ibatis.mapping.ResultMap} <p>the result map return object is <code>ResultMap</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.mapping.ResultMap
     */
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
            if (column.isIdentityColumn()) {
                property = column.property("id.");
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
        String resultMapId = generateResultMapId(providerContext, DEFAULT_RESULT_MAP_NAME);
        ResultMap.Builder builder = new ResultMap.Builder(configuration, resultMapId, this.entity, resultMappings, true);
        return builder.build();
    }


    /**
     * <code>getTypeHandlerInstance</code>
     * <p>the type handler instance getter method.</p>
     * @param javaTypeClass    {@link java.lang.Class} <p>the java type class parameter is <code>Class</code> type.</p>
     * @param typeHandlerClass {@link java.lang.Class} <p>the type handler class parameter is <code>Class</code> type.</p>
     * @return {@link org.apache.ibatis.type.TypeHandler} <p>the type handler instance return object is <code>TypeHandler</code> type.</p>
     * @see java.lang.Class
     * @see org.apache.ibatis.type.TypeHandler
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
     * <code>tableColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> tableColumns() {
        return this.tableColumns;
    }

    /**
     * <code>fieldColumn</code>
     * <p>the column method.</p>
     * @param fieldName {@link java.lang.String} <p>the field name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column return object is <code>MybatisColumn</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    public MybatisColumn fieldColumn(String fieldName) {
        if (GeneralUtils.isEmpty(this.fieldColumns)) {
            this.fieldColumns = this.tableColumns.stream().collect(Collectors.toMap(MybatisColumn::fieldName, Function.identity()));
        }
        return this.fieldColumns.get(fieldName);
    }

    /**
     * <code>identityColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> identityColumns() {
        return this.identityColumns;
    }

    /**
     * <code>normalColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> normalColumns() {
        return this.tableColumns.stream().filter(column -> !column.isIdentityKey() && !column.isPrimaryKey()
                && !column.isUnionKey() && !column.isLogicKey() && !column.isOperateKey()).collect(Collectors.toList());
    }

    /**
     * <code>selectColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> selectColumns() {
        if (isCustomIdentity()) {
            return this.tableColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
        } else {
            return this.tableColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
        }

    }

    /**
     * <code>whereColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> whereColumns() {
        return this.tableColumns;
    }

    /**
     * <code>uniqueColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> uniqueColumns() {
        return this.unionColumns;
    }

    /**
     * <code>insertColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> insertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isInsert).collect(Collectors.toList());
    }

    /**
     * <code>updateColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> updateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isUpdate).collect(Collectors.toList());
    }

    /**
     * <code>forceInsertColumns</code>
     * <p>the insert columns method.</p>
     * @return {@link java.util.List} <p>the insert columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> forceInsertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceInsert).collect(Collectors.toList());
    }

    /**
     * <code>forceUpdateColumns</code>
     * <p>the update columns method.</p>
     * @return {@link java.util.List} <p>the update columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> forceUpdateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceUpdate).collect(Collectors.toList());
    }

    /**
     * <code>groupByColumns</code>
     * <p>the by columns method.</p>
     * @return {@link java.util.Optional} <p>the by columns return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<List<MybatisColumn>> groupByColumns() {
        return Optional.empty();
    }

    /**
     * <code>havingColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.Optional} <p>the columns return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<List<MybatisColumn>> havingColumns() {
        return Optional.empty();
    }

    /**
     * <code>orderByColumns</code>
     * <p>the by columns method.</p>
     * @return {@link java.util.Optional} <p>the by columns return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
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

    /**
     * <code>identityColumnEqualsProperty</code>
     * <p>the column equals property method.</p>
     * @return {@link java.lang.String} <p>the column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String identityColumnEqualsProperty() {
        Optional<MybatisColumn> columnOptional = this.identityColumns.stream().findFirst();
        if (columnOptional.isPresent()) {
            return columnOptional.get().columnEqualsProperty();
        }
        return "";
    }

    /**
     * <code>identityAliasColumnEqualsProperty</code>
     * <p>the alias column equals property method.</p>
     * @return {@link java.lang.String} <p>the alias column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String identityAliasColumnEqualsProperty() {
        Optional<MybatisColumn> columnOptional = this.identityColumns.stream().findFirst();
        if (columnOptional.isPresent()) {
            return columnOptional.get().aliasColumnEqualsProperty(this.alias);
        }
        return "";
    }

    /**
     * <code>baseColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String baseColumnList() {
        return selectColumns().stream().map(MybatisColumn::columnName).collect(Collectors.joining(", "));
    }

    /**
     * <code>baseAliasColumnList</code>
     * <p>the alias column list method.</p>
     * @return {@link java.lang.String} <p>the alias column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String baseAliasColumnList() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(", "));
    }

    /**
     * <code>baseColumnAsPropertyList</code>
     * <p>the column as property list method.</p>
     * @return {@link java.lang.String} <p>the column as property list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String baseColumnAsPropertyList() {
        //当存在 resultMaps 时，查询列不能用别名
        if (useResultMaps()) {
            return baseColumnList();
        }
        return selectColumns().stream().map(MybatisColumn::columnAsProperty).collect(Collectors.joining(", "));
    }

    /**
     * <code>baseAliasColumnAsPropertyList</code>
     * <p>the alias column as property list method.</p>
     * @return {@link java.lang.String} <p>the alias column as property list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String baseAliasColumnAsPropertyList() {
        //当存在 resultMaps 时，查询列不能用别名
        if (useResultMaps()) {
            return baseColumnList();
        }
        return selectColumns().stream().map(column -> column.aliasColumnAsProperty(this.alias)).collect(Collectors.joining(", "));
    }

    /**
     * <code>selectColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String selectColumnList() {
        return selectColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>selectAliasColumnList</code>
     * <p>the alias column list method.</p>
     * @return {@link java.lang.String} <p>the alias column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String selectAliasColumnList() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(", "));
    }

    /**
     * <code>insertColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String insertColumnList() {
        return insertColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>insertAliasColumnList</code>
     * <p>the alias column list method.</p>
     * @return {@link java.lang.String} <p>the alias column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String insertAliasColumnList() {
        return insertColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>identityColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String identityColumnList() {
        return identityColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>identityAliasColumnList</code>
     * <p>the alias column list method.</p>
     * @return {@link java.lang.String} <p>the alias column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String identityAliasColumnList() {
        return identityColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>groupByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> groupByColumnList() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>groupByAliasColumnList</code>
     * <p>the by alias column list method.</p>
     * @return {@link java.util.Optional} <p>the by alias column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> groupByAliasColumnList() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>groupByColumn</code>
     * <p>the by column method.</p>
     * @return {@link java.util.Optional} <p>the by column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> groupByColumn() {
        Optional<String> groupByColumnList = groupByColumnList();
        return groupByColumnList.map(s -> " GROUP BY " + s);
    }

    /**
     * <code>groupByAliasColumn</code>
     * <p>the by alias column method.</p>
     * @return {@link java.util.Optional} <p>the by alias column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> groupByAliasColumn() {
        Optional<String> groupByColumnList = groupByAliasColumnList();
        return groupByColumnList.map(s -> " GROUP BY " + s);
    }

    /**
     * <code>havingColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.util.Optional} <p>the column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> havingColumnList() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>havingAliasColumnList</code>
     * <p>the alias column list method.</p>
     * @return {@link java.util.Optional} <p>the alias column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> havingAliasColumnList() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>havingColumn</code>
     * <p>the column method.</p>
     * @return {@link java.util.Optional} <p>the column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> havingColumn() {
        Optional<String> havingColumnList = havingColumnList();
        return havingColumnList.map(s -> " HAVING " + s);
    }

    /**
     * <code>havingAliasColumn</code>
     * <p>the alias column method.</p>
     * @return {@link java.util.Optional} <p>the alias column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> havingAliasColumn() {
        Optional<String> havingColumnList = havingAliasColumnList();
        return havingColumnList.map(s -> " HAVING " + s);
    }

    /**
     * <code>orderByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> orderByColumnList() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.columnName() + " " + column.getSortType().getKey())
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>orderByAliasColumnList</code>
     * <p>the by alias column list method.</p>
     * @return {@link java.util.Optional} <p>the by alias column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> orderByAliasColumnList() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.aliasColumn(this.alias) + " " + column.getSortType().getKey())
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>orderByColumn</code>
     * <p>the by column method.</p>
     * @return {@link java.util.Optional} <p>the by column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> orderByColumn() {
        Optional<String> orderColumnList = orderByColumnList();
        return orderColumnList.map(s -> " ORDER BY " + s);
    }

    /**
     * <code>orderByAliasColumn</code>
     * <p>the by alias column method.</p>
     * @return {@link java.util.Optional} <p>the by alias column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> orderByAliasColumn() {
        Optional<String> orderColumnList = orderByAliasColumnList();
        return orderColumnList.map(s -> " ORDER BY " + s);
    }

    /**
     * <code>isExcludeSuperClass</code>
     * <p>the exclude super class method.</p>
     * @param superClass {@link java.lang.Class} <p>the super class parameter is <code>Class</code> type.</p>
     * @return boolean <p>the exclude super class return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
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
     * <code>isExcludeField</code>
     * <p>the exclude field method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>the exclude field return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
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
