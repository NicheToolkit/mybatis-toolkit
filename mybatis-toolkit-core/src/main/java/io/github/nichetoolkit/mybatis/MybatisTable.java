package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.consts.SQLConstants;
import io.github.nichetoolkit.rice.consts.ScriptConstants;
import io.github.nichetoolkit.rice.enums.SortType;
import io.github.nichetoolkit.rice.enums.StyleType;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.error.MybatisLinkageLackError;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
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
 * <p>The mybatis table class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisProperty
 * @see  lombok.Getter
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Getter
public class MybatisTable extends MybatisProperty<MybatisTable> {
    /**
     * <code>DELIMITER</code>
     * {@link java.util.regex.Pattern} <p>The constant <code>DELIMITER</code> field.</p>
     * @see  java.util.regex.Pattern
     */
    public static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
    /**
     * <code>DEFAULT_RESULT_MAP_NAME</code>
     * {@link java.lang.String} <p>The constant <code>DEFAULT_RESULT_MAP_NAME</code> field.</p>
     * @see  java.lang.String
     */
    public static final String DEFAULT_RESULT_MAP_NAME = ScriptConstants.DEFAULT_RESULT_MAP;
    /**
     * <code>entityType</code>
     * {@link java.lang.Class} <p>The <code>entityType</code> field.</p>
     * @see  java.lang.Class
     */
    private final Class<?> entityType;
    /**
     * <code>identityType</code>
     * {@link java.lang.Class} <p>The <code>identityType</code> field.</p>
     * @see  java.lang.Class
     */
    private final Class<?> identityType;
    /**
     * <code>linkageType</code>
     * {@link java.lang.Class} <p>The <code>linkageType</code> field.</p>
     * @see  java.lang.Class
     */
    private final Class<?> linkageType;
    /**
     * <code>alertnessType</code>
     * {@link java.lang.Class} <p>The <code>alertnessType</code> field.</p>
     * @see  java.lang.Class
     */
    private final Class<?> alertnessType;
    /**
     * <code>isSpecialIdentity</code>
     * <p>The <code>isSpecialIdentity</code> field.</p>
     */
    private final boolean isSpecialIdentity;
    /**
     * <code>isSpecialLinkage</code>
     * <p>The <code>isSpecialLinkage</code> field.</p>
     */
    private final boolean isSpecialLinkage;
    /**
     * <code>isSpecialAlertness</code>
     * <p>The <code>isSpecialAlertness</code> field.</p>
     */
    private final boolean isSpecialAlertness;
    /**
     * <code>initiates</code>
     * {@link java.util.Set} <p>The <code>initiates</code> field.</p>
     * @see  java.util.Set
     */
    private final Set<Configuration> initiates = new HashSet<>();
    /**
     * <code>tableColumns</code>
     * {@link java.util.List} <p>The <code>tableColumns</code> field.</p>
     * @see  java.util.List
     */
    private final List<MybatisColumn> tableColumns = new ArrayList<>();
    /**
     * <code>uniqueColumns</code>
     * {@link java.util.List} <p>The <code>uniqueColumns</code> field.</p>
     * @see  java.util.List
     */
    private final List<MybatisColumn> uniqueColumns = new ArrayList<>();
    /**
     * <code>unionColumns</code>
     * {@link java.util.List} <p>The <code>unionColumns</code> field.</p>
     * @see  java.util.List
     */
    private final List<MybatisColumn> unionColumns = new ArrayList<>();
    /**
     * <code>forceUpdateColumns</code>
     * {@link java.util.List} <p>The <code>forceUpdateColumns</code> field.</p>
     * @see  java.util.List
     */
    private final List<MybatisColumn> forceUpdateColumns = new ArrayList<>();
    /**
     * <code>fieldColumns</code>
     * {@link java.util.Map} <p>The <code>fieldColumns</code> field.</p>
     * @see  java.util.Map
     */
    private Map<String, MybatisColumn> fieldColumns = new HashMap<>();
    /**
     * <code>identityColumns</code>
     * {@link java.util.List} <p>The <code>identityColumns</code> field.</p>
     * @see  java.util.List
     */
    private List<MybatisColumn> identityColumns = new ArrayList<>();
    /**
     * <code>linkageColumns</code>
     * {@link java.util.List} <p>The <code>linkageColumns</code> field.</p>
     * @see  java.util.List
     */
    private List<MybatisColumn> linkageColumns = new ArrayList<>();
    /**
     * <code>alertnessColumns</code>
     * {@link java.util.List} <p>The <code>alertnessColumns</code> field.</p>
     * @see  java.util.List
     */
    private List<MybatisColumn> alertnessColumns = new ArrayList<>();
    /**
     * <code>identityIndex</code>
     * <p>The <code>identityIndex</code> field.</p>
     */
    private int identityIndex;
    /**
     * <code>ready</code>
     * <p>The <code>ready</code> field.</p>
     * @see  lombok.Setter
     */
    @Setter
    private boolean ready;
    /**
     * <code>table</code>
     * {@link java.lang.String} <p>The <code>table</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String table;
    /**
     * <code>alias</code>
     * {@link java.lang.String} <p>The <code>alias</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String alias;
    /**
     * <code>comment</code>
     * {@link java.lang.String} <p>The <code>comment</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String comment;
    /**
     * <code>catalog</code>
     * {@link java.lang.String} <p>The <code>catalog</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String catalog;
    /**
     * <code>schema</code>
     * {@link java.lang.String} <p>The <code>schema</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String schema;
    /**
     * <code>styleName</code>
     * {@link java.lang.String} <p>The <code>styleName</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String styleName;
    /**
     * <code>styleType</code>
     * {@link io.github.nichetoolkit.rice.enums.StyleType} <p>The <code>styleType</code> field.</p>
     * @see  io.github.nichetoolkit.rice.enums.StyleType
     * @see  lombok.Setter
     */
    @Setter
    private StyleType styleType;
    /**
     * <code>resultMap</code>
     * {@link java.lang.String} <p>The <code>resultMap</code> field.</p>
     * @see  java.lang.String
     * @see  lombok.Setter
     */
    @Setter
    private String resultMap;
    /**
     * <code>autoResultMap</code>
     * <p>The <code>autoResultMap</code> field.</p>
     * @see  lombok.Setter
     */
    @Setter
    private boolean autoResultMap = true;
    /**
     * <code>unionKeys</code>
     * {@link java.util.List} <p>The <code>unionKeys</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> unionKeys;
    /**
     * <code>linkKeys</code>
     * {@link java.util.List} <p>The <code>linkKeys</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> linkKeys;
    /**
     * <code>uniqueKeys</code>
     * {@link java.util.List} <p>The <code>uniqueKeys</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> uniqueKeys;
    /**
     * <code>alertKeys</code>
     * {@link java.util.List} <p>The <code>alertKeys</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> alertKeys;
    /**
     * <code>autoResultMaps</code>
     * {@link java.util.List} <p>The <code>autoResultMaps</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<ResultMap> autoResultMaps;
    /**
     * <code>excludeFields</code>
     * {@link java.util.List} <p>The <code>excludeFields</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> excludeFields;
    /**
     * <code>excludeFieldTypes</code>
     * {@link java.util.List} <p>The <code>excludeFieldTypes</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<Class<?>> excludeFieldTypes;
    /**
     * <code>excludeSuperClasses</code>
     * {@link java.util.List} <p>The <code>excludeSuperClasses</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<Class<?>> excludeSuperClasses;
    /**
     * <code>ignoreFields</code>
     * {@link java.util.List} <p>The <code>ignoreFields</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<String> ignoreFields;
    /**
     * <code>ignoreFieldTypes</code>
     * {@link java.util.List} <p>The <code>ignoreFieldTypes</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<Class<?>> ignoreFieldTypes;
    /**
     * <code>ignoreSuperClasses</code>
     * {@link java.util.List} <p>The <code>ignoreSuperClasses</code> field.</p>
     * @see  java.util.List
     * @see  lombok.Setter
     */
    @Setter
    private List<Class<?>> ignoreSuperClasses;

    /**
     * <code>identityColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The <code>identityColumn</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn identityColumn;
    /**
     * <code>logicColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The <code>logicColumn</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn logicColumn;
    /**
     * <code>operateColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The <code>operateColumn</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn operateColumn;
    /**
     * <code>alertColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The <code>alertColumn</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn alertColumn;
    /**
     * <code>linkColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The <code>linkColumn</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn linkColumn;


    /**
     * <code>MybatisTable</code>
     * <p>Instantiates a new mybatis table.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     */
    public MybatisTable(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType) {
        this.entityType = entityType;
        this.identityType = identityType;
        this.linkageType = linkageType;
        this.alertnessType = alertnessType;
        this.isSpecialIdentity = identityType != null;
        this.isSpecialLinkage = linkageType != null;
        this.isSpecialAlertness = alertnessType != null;
    }

    /**
     * <code>MybatisTable</code>
     * <p>Instantiates a new mybatis table.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @see  java.lang.Class
     * @see  java.util.Map
     */
    public MybatisTable(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType, Map<String, String> properties) {
        super(properties);
        this.entityType = entityType;
        this.identityType = identityType;
        this.linkageType = linkageType;
        this.alertnessType = alertnessType;
        this.isSpecialIdentity = identityType != null;
        this.isSpecialLinkage = linkageType != null;
        this.isSpecialAlertness = alertnessType != null;
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The of return object is <code>MybatisTable</code> type.</p>
     */
    public static MybatisTable of(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType) {
        return new MybatisTable(entityType, identityType, linkageType, alertnessType);
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param linkageType {@link java.lang.Class} <p>The linkage type parameter is <code>Class</code> type.</p>
     * @param alertnessType {@link java.lang.Class} <p>The alertness type parameter is <code>Class</code> type.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @see  java.lang.Class
     * @see  java.util.Map
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The of return object is <code>MybatisTable</code> type.</p>
     */
    public static MybatisTable of(Class<?> entityType, Class<?> identityType, Class<?> linkageType, Class<?> alertnessType, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisTable(entityType, identityType, linkageType, alertnessType, properties);
        } else {
            return new MybatisTable(entityType, identityType, linkageType, alertnessType);
        }
    }

    /**
     * <code>tablename</code>
     * <p>The tablename method.</p>
     * @return  {@link java.lang.String} <p>The tablename return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String tablename() {
        return Stream.of(this.catalog, this.schema, this.table)
                .filter(GeneralUtils::isNotEmpty)
                .collect(Collectors.joining(SQLConstants.PERIOD));
    }

    /**
     * <code>tablename</code>
     * <p>The tablename method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The tablename return object is <code>String</code> type.</p>
     */
    public String tablename(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename());
    }

    /**
     * <code>tablenameAsAlias</code>
     * <p>The tablename as alias method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The tablename as alias return object is <code>String</code> type.</p>
     */
    public String tablenameAsAlias(String tablename) {
        return Optional.ofNullable(tablename).orElse(tablename()) + SQLConstants.BLANK + SQLConstants.AS + SQLConstants.BLANK + this.alias;
    }

    /**
     * <code>readyColumns</code>
     * <p>The ready columns method.</p>
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
            if (column.isForceUpdate()) {
                this.forceUpdateColumns.remove(column);
                refreshColumn(this.forceUpdateColumns, column);
            }
            if (column.isSpecialIdentity()) {
                /* special Identity */
                if (column.isPrimaryKey() || column.isIdentityKey() || column.isUnionKey()) {
                    this.identityColumns.remove(column);
                    refreshColumn(this.identityColumns, column);
                } else {
                    identityColumns.remove(column);
                    identityColumns.add(0, column);
                }
            } else {
                /* common Identity */
                if (column.isIdentityKey()) {
                    this.identityColumn = column;
                }
            }
            String fieldName = column.getField().fieldName();
            if (column.isSpecialLinkage()) {
                if (column.isLinkKey() || (GeneralUtils.isNotEmpty(this.linkKeys) && this.linkKeys.contains(fieldName))) {
                    this.linkageColumns.remove(column);
                    refreshColumn(this.linkageColumns, column);
                } else {
                    linkKeyColumns.remove(column);
                    linkKeyColumns.add(0, column);
                }
            } else {
                /* 如果是链接外键 */
                if (column.isLinkKey() || (GeneralUtils.isNotEmpty(this.linkKeys) && this.linkKeys.contains(fieldName))) {
                    column.setLinkKey(true);
                    linkKeyColumns.remove(column);
                    linkKeyColumns.add(0, column);
                }
            }
            if (column.isSpecialAlertness()) {
                if (column.isAlertKey() || (GeneralUtils.isNotEmpty(this.alertKeys) && this.alertKeys.contains(fieldName))) {
                    this.alertnessColumns.remove(column);
                    refreshColumn(this.alertnessColumns, column);
                } else {
                    alertKeyColumns.remove(column);
                    alertKeyColumns.add(0, column);
                }
            } else {
                /* 如果是链接外键 */
                if (column.isAlertKey() || (GeneralUtils.isNotEmpty(this.alertKeys) && this.alertKeys.contains(fieldName))) {
                    column.setAlertKey(true);
                    alertKeyColumns.remove(column);
                    alertKeyColumns.add(0, column);
                }
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
            if (column.isUnionKey() || (GeneralUtils.isNotEmpty(this.unionKeys) && this.unionKeys.contains(fieldName))) {
                column.setUnionKey(true);
                this.unionColumns.remove(column);
                refreshColumn(this.unionColumns, column);
            }
            /* 如果是唯一键 */
            if (column.isUniqueKey() || (GeneralUtils.isNotEmpty(this.uniqueKeys) && this.uniqueKeys.contains(fieldName))) {
                column.setUniqueKey(true);
                this.uniqueColumns.remove(column);
                this.uniqueColumns.add(0, column);
            }
        });
        Optional<MybatisColumn> firstLogic = logicKeyColumns.stream().findFirst();
        firstLogic.ifPresent(logicKeyColumn -> this.logicColumn = logicKeyColumn);
        Optional<MybatisColumn> firstOperate = operateKeyColumns.stream().findFirst();
        firstOperate.ifPresent(operateKeyColumn -> this.operateColumn = operateKeyColumn);
        /*
         * 优先级别: RestIdentity > RestPrimaryKey > RestIdentityKey > RestUnionKey > RestUniqueKey
         */
        if (isSpecialIdentity()) {
            if (GeneralUtils.isEmpty(this.identityColumns)) {
                if (GeneralUtils.isNotEmpty(identityColumns)) {
                    this.identityColumns = new ArrayList<>(identityColumns);
                } else {
                    throw new MybatisIdentityLackError("The special identity columns must be not empty, identity type: " + this.identityType.getName());
                }
            }
        } else {
            /* RestPrimaryKey override RestIdentityKey */
            Optional<MybatisColumn> firstPrimaryKey = primaryKeyColumns.stream().findFirst();
            firstPrimaryKey.ifPresent(mybatisColumn -> this.identityColumn = mybatisColumn);
        }
        if (isSpecialLinkage()) {
            if (GeneralUtils.isEmpty(this.linkageColumns)) {
                if (GeneralUtils.isNotEmpty(linkKeyColumns)) {
                    this.linkageColumns = new ArrayList<>(linkKeyColumns);
                } else {
                    throw new MybatisLinkageLackError("The special linkage columns must be not empty, linkage type: " + this.linkageType.getName());
                }
            }
        } else {
            Optional<MybatisColumn> firstLink = linkKeyColumns.stream().findFirst();
            firstLink.ifPresent(linkKeyColumn -> this.linkColumn = linkKeyColumn);
        }
        if (isSpecialAlertness()) {
            if (GeneralUtils.isEmpty(this.alertnessColumns)) {
                if (GeneralUtils.isNotEmpty(alertKeyColumns)) {
                    this.alertnessColumns = new ArrayList<>(alertKeyColumns);
                } else {
                    throw new MybatisLinkageLackError("The special alertness columns must be not empty, alertness type: " + this.alertnessType.getName());
                }
            }
        } else {
            Optional<MybatisColumn> firstAlert = alertKeyColumns.stream().findFirst();
            firstAlert.ifPresent(alertKeyColumn -> this.alertColumn = alertKeyColumn);
        }
    }

    /**
     * <code>addColumn</code>
     * <p>The add column method.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The column parameter is <code>MybatisColumn</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     * @see  java.lang.SuppressWarnings
     */
    @SuppressWarnings("StatementWithEmptyBody")
    protected void addColumn(MybatisColumn column) {
        /* 不重复添加同名的列 */
        if (!this.tableColumns.contains(column)) {
            /* 父类 字段处理 */
            if (column.getField().declaringClass() != this.entityType) {
                if (column.isForceInsert() || column.isForceUpdate() || column.isLogicKey() || column.isOperateKey()) {
                    refreshColumn(this.tableColumns, column);
                } else if (column.isUnionKey() || column.isLinkKey()) {
                    /*  RestUnionKey 或者 RestLinkKey 放在 identity后面 */
                    refreshColumn(this.tableColumns, column, this.identityIndex);
                } else if (column.isIdentityKey() || column.isSpecialIdentity()) {
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
        } else if (isSpecialIdentity()) {
            /* 同名列在存在, 为自定义主键类型覆盖实体类或父类，进行字段覆盖 */
            int columnIndex = this.tableColumns.indexOf(column);
            MybatisColumn existsColumn = this.tableColumns.remove(columnIndex);
            refreshColumn(this.tableColumns, column, columnIndex);
        } else {
            /* 同名列在存在，为子类覆盖父类，不做处理 */
        }
    }

    /**
     * <code>fields</code>
     * <p>The fields method.</p>
     * @return  {@link java.util.List} <p>The fields return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisField> fields() {
        return this.tableColumns.stream().map(MybatisColumn::getField).collect(Collectors.toList());
    }

    /**
     * <code>columnNames</code>
     * <p>The column names method.</p>
     * @return  {@link java.util.List} <p>The column names return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> columnNames() {
        return this.tableColumns.stream().map(MybatisColumn::columnName).collect(Collectors.toList());
    }

    /**
     * <code>columnAliasNames</code>
     * <p>The column alias names method.</p>
     * @return  {@link java.util.List} <p>The column alias names return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> columnAliasNames() {
        return this.tableColumns.stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.toList());
    }

    /**
     * <code>fieldNames</code>
     * <p>The field names method.</p>
     * @return  {@link java.util.List} <p>The field names return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> fieldNames() {
        return this.tableColumns.stream().map(MybatisColumn::property).collect(Collectors.toList());
    }

    /**
     * <code>refreshColumn</code>
     * <p>The refresh column method.</p>
     * @param columns {@link java.util.List} <p>The columns parameter is <code>List</code> type.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The column parameter is <code>MybatisColumn</code> type.</p>
     * @see  java.util.List
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private void refreshColumn(List<MybatisColumn> columns, MybatisColumn column) {
        refreshColumn(columns, column, null);
    }

    /**
     * <code>refreshColumn</code>
     * <p>The refresh column method.</p>
     * @param columns {@link java.util.List} <p>The columns parameter is <code>List</code> type.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The column parameter is <code>MybatisColumn</code> type.</p>
     * @param index {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @see  java.util.List
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     * @see  java.lang.Integer
     */
    private void refreshColumn(List<MybatisColumn> columns, MybatisColumn column, Integer index) {
        if (GeneralUtils.isNotNull(column.getOrder())) {
            columns.add(column.getOrder(), column);
        } else if (GeneralUtils.isNotNull(index)) {
            columns.add(index, column);
        } else {
            columns.add(column);
        }
    }

    /**
     * <code>canUseResultMaps</code>
     * <p>The can use result maps method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @return boolean <p>The can use result maps return object is <code>boolean</code> type.</p>
     */
    private boolean canUseResultMaps(ProviderContext providerContext, String cacheKey) {
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

    /**
     * <code>useResultMap</code>
     * <p>The use result map method.</p>
     * @return boolean <p>The use result map return object is <code>boolean</code> type.</p>
     */
    public boolean useResultMap() {
        return this.autoResultMaps != null || this.autoResultMap || GeneralUtils.isNotEmpty(this.resultMap);
    }

    /**
     * <code>hasBeenReplaced</code>
     * <p>The has been replaced method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.session.Configuration
     * @see  java.lang.String
     * @return boolean <p>The has been replaced return object is <code>boolean</code> type.</p>
     */
    private boolean hasBeenReplaced(Configuration configuration, String cacheKey) {
        MappedStatement mappedStatement = configuration.getMappedStatement(cacheKey);
        if (mappedStatement.getResultMaps() != null && !mappedStatement.getResultMaps().isEmpty()) {
            return mappedStatement.getResultMaps().get(0) == this.autoResultMaps.get(0);
        }
        return false;
    }

    /**
     * <code>initContext</code>
     * <p>The init context method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.session.Configuration
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.lang.SuppressWarnings
     */
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
                    metaObject.setValue(ScriptConstants.RESULT_MAPS, Collections.unmodifiableList(this.autoResultMaps));
                }
            }
        }
    }

    /**
     * <code>initResultMap</code>
     * <p>The init result map method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.session.Configuration
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     */
    private void initResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
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
                        throw new ConfigureLackError("The " + this.entityType.getName() + " configured result map: " + this.resultMap + " not found");
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
     * <code>resultMapId</code>
     * <p>The result map id method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param resultMapId {@link java.lang.String} <p>The result map id parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @return  {@link java.lang.String} <p>The result map id return object is <code>String</code> type.</p>
     */
    private String resultMapId(ProviderContext providerContext, String resultMapId) {
        if (resultMapId.indexOf(SQLConstants.PERIOD) > 0) {
            return resultMapId;
        }
        return providerContext.getMapperType().getName() + SQLConstants.PERIOD + resultMapId;
    }

    /**
     * <code>autoResultMap</code>
     * <p>The auto result map method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.session.Configuration
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.apache.ibatis.mapping.ResultMap
     * @return  {@link org.apache.ibatis.mapping.ResultMap} <p>The auto result map return object is <code>ResultMap</code> type.</p>
     */
    private ResultMap autoResultMap(Configuration configuration, ProviderContext providerContext, String cacheKey) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (MybatisColumn column : selectColumns()) {
            String columnName = column.columnName();
            /* 去掉可能存在的分隔符，例如：`order` */
            Matcher matcher = MybatisTable.DELIMITER.matcher(columnName);
            if (matcher.find()) {
                columnName = matcher.group(1);
            }
            String property = column.property();
            if ((column.isSpecialIdentity() || column.isSpecialLinkage() || column.isSpecialAlertness()) && column.isParentNotEmpty()) {
                property = column.property(column.prefixOfParent() + SQLConstants.PERIOD);
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
        ResultMap.Builder builder = new ResultMap.Builder(configuration, resultMapId, this.entityType, resultMappings, true);
        return builder.build();
    }


    /**
     * <code>getTypeHandlerInstance</code>
     * <p>The get type handler instance getter method.</p>
     * @param javaTypeClass {@link java.lang.Class} <p>The java type class parameter is <code>Class</code> type.</p>
     * @param typeHandlerClass {@link java.lang.Class} <p>The type handler class parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     * @see  org.apache.ibatis.type.TypeHandler
     * @return  {@link org.apache.ibatis.type.TypeHandler} <p>The get type handler instance return object is <code>TypeHandler</code> type.</p>
     */
    public TypeHandler<?> getTypeHandlerInstance(Class<?> javaTypeClass, Class<?> typeHandlerClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerClass.getConstructor(Class.class);
                return (TypeHandler<?>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                throw new TypeException("It is failed invoking constructor for handler " + typeHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerClass.getConstructor();
            return (TypeHandler<?>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("It is unable to find a usable constructor for " + typeHandlerClass, e);
        }
    }

    /**
     * <code>tableColumns</code>
     * <p>The table columns method.</p>
     * @return  {@link java.util.List} <p>The table columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> tableColumns() {
        return this.tableColumns;
    }

    /**
     * <code>fieldColumn</code>
     * <p>The field column method.</p>
     * @param fieldName {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.mybatis.MybatisColumn
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The field column return object is <code>MybatisColumn</code> type.</p>
     */
    public MybatisColumn fieldColumn(String fieldName) {
        if (GeneralUtils.isEmpty(this.fieldColumns)) {
            this.fieldColumns = this.tableColumns.stream().collect(Collectors.toMap(MybatisColumn::fieldName, Function.identity()));
        }
        return this.fieldColumns.get(fieldName);
    }

    /**
     * <code>identityColumns</code>
     * <p>The identity columns method.</p>
     * @return  {@link java.util.List} <p>The identity columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> identityColumns() {
        return this.identityColumns;
    }

    /**
     * <code>linkageColumns</code>
     * <p>The linkage columns method.</p>
     * @return  {@link java.util.List} <p>The linkage columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> linkageColumns() {
        return this.linkageColumns;
    }

    /**
     * <code>alertnessColumns</code>
     * <p>The alertness columns method.</p>
     * @return  {@link java.util.List} <p>The alertness columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> alertnessColumns() {
        return this.alertnessColumns;
    }

    /**
     * <code>normalColumns</code>
     * <p>The normal columns method.</p>
     * @return  {@link java.util.List} <p>The normal columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> normalColumns() {
        return this.tableColumns.stream().filter(column -> !column.isAnyKey()).collect(Collectors.toList());
    }

    /**
     * <code>selectColumns</code>
     * <p>The select columns method.</p>
     * @return  {@link java.util.List} <p>The select columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> selectColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
    }

    /**
     * <code>whereColumns</code>
     * <p>The where columns method.</p>
     * @return  {@link java.util.List} <p>The where columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> whereColumns() {
        return this.tableColumns;
    }

    /**
     * <code>uniqueColumns</code>
     * <p>The unique columns method.</p>
     * @return  {@link java.util.List} <p>The unique columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> uniqueColumns() {
        return this.unionColumns;
    }

    /**
     * <code>insertColumns</code>
     * <p>The insert columns method.</p>
     * @return  {@link java.util.List} <p>The insert columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> insertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isInsert).collect(Collectors.toList());
    }

    /**
     * <code>updateColumns</code>
     * <p>The update columns method.</p>
     * @return  {@link java.util.List} <p>The update columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> updateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isUpdate).collect(Collectors.toList());
    }

    /**
     * <code>forceInsertColumns</code>
     * <p>The force insert columns method.</p>
     * @return  {@link java.util.List} <p>The force insert columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> forceInsertColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceInsert).collect(Collectors.toList());
    }

    /**
     * <code>forceUpdateColumns</code>
     * <p>The force update columns method.</p>
     * @return  {@link java.util.List} <p>The force update columns return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<MybatisColumn> forceUpdateColumns() {
        return this.tableColumns.stream().filter(MybatisColumn::isForceUpdate).collect(Collectors.toList());
    }

    /**
     * <code>groupByColumns</code>
     * <p>The group by columns method.</p>
     * @return  {@link java.util.Optional} <p>The group by columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<List<MybatisColumn>> groupByColumns() {
        return Optional.empty();
    }

    /**
     * <code>havingColumns</code>
     * <p>The having columns method.</p>
     * @return  {@link java.util.Optional} <p>The having columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<List<MybatisColumn>> havingColumns() {
        return Optional.empty();
    }

    /**
     * <code>orderByColumns</code>
     * <p>The order by columns method.</p>
     * @return  {@link java.util.Optional} <p>The order by columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
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
     * <code>sqlOfIdentityColumn</code>
     * <p>The sql of identity column method.</p>
     * @return  {@link java.lang.String} <p>The sql of identity column return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfIdentityColumn() {
        return this.identityColumn.columnEqualsProperty();
    }

    /**
     * <code>sqlOfIdentityAliasColumn</code>
     * <p>The sql of identity alias column method.</p>
     * @return  {@link java.lang.String} <p>The sql of identity alias column return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfIdentityAliasColumn() {
        return this.identityColumn.aliasColumnEqualsProperty(this.alias);
    }

    /**
     * <code>sqlOfBaseColumns</code>
     * <p>The sql of base columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of base columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfBaseColumns() {
        return selectColumns().stream().map(MybatisColumn::columnName).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfBaseAliasColumns</code>
     * <p>The sql of base alias columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of base alias columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfBaseAliasColumns() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>asSqlOfBaseColumns</code>
     * <p>The as sql of base columns method.</p>
     * @return  {@link java.lang.String} <p>The as sql of base columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String asSqlOfBaseColumns() {
        if (useResultMap()) {
            return sqlOfBaseColumns();
        }
        return selectColumns().stream().map(MybatisColumn::columnAsProperty).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>asSqlOfBaseAliasColumns</code>
     * <p>The as sql of base alias columns method.</p>
     * @return  {@link java.lang.String} <p>The as sql of base alias columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String asSqlOfBaseAliasColumns() {
        if (useResultMap()) {
            return sqlOfBaseColumns();
        }
        return selectColumns().stream().map(column -> column.aliasColumnAsProperty(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfSelectColumns</code>
     * <p>The sql of select columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of select columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfSelectColumns() {
        return selectColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfSelectAliasColumns</code>
     * <p>The sql of select alias columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of select alias columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfSelectAliasColumns() {
        return selectColumns().stream().map(column -> column.aliasColumn(this.alias)).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfForceUpdateColumns</code>
     * <p>The sql of force update columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of force update columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfForceUpdateColumns() {
        return forceUpdateColumns().stream().map(MybatisColumn::columnEqualsProperty).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfInsertColumns</code>
     * <p>The sql of insert columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of insert columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfInsertColumns() {
        return insertColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfInsertAliasColumns</code>
     * <p>The sql of insert alias columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of insert alias columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfInsertAliasColumns() {
        return insertColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfIdentityColumns</code>
     * <p>The sql of identity columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of identity columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfIdentityColumns() {
        return identityColumns().stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfIdentityAliasColumns</code>
     * <p>The sql of identity alias columns method.</p>
     * @return  {@link java.lang.String} <p>The sql of identity alias columns return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String sqlOfIdentityAliasColumns() {
        return identityColumns().stream().map(column -> column.aliasColumn(this.alias)).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
    }

    /**
     * <code>sqlOfGroupByColumns</code>
     * <p>The sql of group by columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of group by columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfGroupByColumns() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfGroupByAliasColumns</code>
     * <p>The sql of group by alias columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of group by alias columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfGroupByAliasColumns() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfGroupBy</code>
     * <p>The sql of group by method.</p>
     * @return  {@link java.util.Optional} <p>The sql of group by return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfGroupBy() {
        Optional<String> groupByColumnList = sqlOfGroupByColumns();
        return groupByColumnList.map(s -> SQLConstants.BLANK + SQLConstants.GROUP_BY + SQLConstants.BLANK + s);
    }

    /**
     * <code>sqlOfGroupByAlias</code>
     * <p>The sql of group by alias method.</p>
     * @return  {@link java.util.Optional} <p>The sql of group by alias return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfGroupByAlias() {
        Optional<String> groupByColumnList = sqlOfGroupByAliasColumns();
        return groupByColumnList.map(s -> SQLConstants.BLANK + SQLConstants.GROUP_BY + SQLConstants.BLANK + s);
    }

    /**
     * <code>sqlOfHavingColumns</code>
     * <p>The sql of having columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of having columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfHavingColumns() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::columnName)
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfHavingAliasColumns</code>
     * <p>The sql of having alias columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of having alias columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfHavingAliasColumns() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(column -> column.aliasColumn(this.alias))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfHaving</code>
     * <p>The sql of having method.</p>
     * @return  {@link java.util.Optional} <p>The sql of having return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfHaving() {
        Optional<String> havingColumnList = sqlOfHavingColumns();
        return havingColumnList.map(sql -> SQLConstants.BLANK + SQLConstants.HAVING + SQLConstants.BLANK + sql);
    }

    /**
     * <code>sqlOfHavingAlias</code>
     * <p>The sql of having alias method.</p>
     * @return  {@link java.util.Optional} <p>The sql of having alias return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfHavingAlias() {
        Optional<String> havingColumnList = sqlOfHavingAliasColumns();
        return havingColumnList.map(sql -> SQLConstants.BLANK + SQLConstants.HAVING + SQLConstants.BLANK + sql);
    }

    /**
     * <code>sqlOfOrderByColumns</code>
     * <p>The sql of order by columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of order by columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfOrderByColumns() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.columnName() + SQLConstants.BLANK + column.getSortType().getKey())
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfOrderByAliasColumns</code>
     * <p>The sql of order by alias columns method.</p>
     * @return  {@link java.util.Optional} <p>The sql of order by alias columns return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfOrderByAliasColumns() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.aliasColumn(this.alias) + SQLConstants.BLANK + column.getSortType().getKey())
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)));
    }

    /**
     * <code>sqlOfOrderBy</code>
     * <p>The sql of order by method.</p>
     * @return  {@link java.util.Optional} <p>The sql of order by return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfOrderBy() {
        Optional<String> orderColumnList = sqlOfOrderByColumns();
        return orderColumnList.map(sql -> SQLConstants.BLANK + SQLConstants.ORDER_BY + SQLConstants.BLANK + sql);
    }

    /**
     * <code>sqlOfOrderByAlias</code>
     * <p>The sql of order by alias method.</p>
     * @return  {@link java.util.Optional} <p>The sql of order by alias return object is <code>Optional</code> type.</p>
     * @see  java.util.Optional
     */
    public Optional<String> sqlOfOrderByAlias() {
        Optional<String> orderColumnList = sqlOfOrderByAliasColumns();
        return orderColumnList.map(sql -> SQLConstants.BLANK + SQLConstants.ORDER_BY + SQLConstants.BLANK + sql);
    }

    /**
     * <code>isExcludeSuperClass</code>
     * <p>The is exclude super class method.</p>
     * @param superClass {@link java.lang.Class} <p>The super class parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     * @return boolean <p>The is exclude super class return object is <code>boolean</code> type.</p>
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
     * <p>The is exclude field method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisField
     * @return boolean <p>The is exclude field return object is <code>boolean</code> type.</p>
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

    /**
     * <code>isIgnoreSuperClass</code>
     * <p>The is ignore super class method.</p>
     * @param superClass {@link java.lang.Class} <p>The super class parameter is <code>Class</code> type.</p>
     * @see  java.lang.Class
     * @return boolean <p>The is ignore super class return object is <code>boolean</code> type.</p>
     */
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

    /**
     * <code>isIgnoreField</code>
     * <p>The is ignore field method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisField
     * @return boolean <p>The is ignore field return object is <code>boolean</code> type.</p>
     */
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
