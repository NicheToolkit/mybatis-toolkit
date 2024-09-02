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
 * <code>MybatisTable</code>
 * <p>The type mybatis table class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProperty
 * @see lombok.Data
 * @since Jdk1.8
 */
/*
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
    /*
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
    /*
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
    /*
     * <code>table</code>
     * {@link java.lang.String} <p>the <code>table</code> field.</p>
     * @see java.lang.String
     */
    protected String table;
    /**
     * <code>entity</code>
     * {@link java.lang.Class} <p>the <code>entity</code> field.</p>
     * @see java.lang.Class
     */
    /*
     * <code>entity</code>
     * {@link java.lang.Class} <p>the <code>entity</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<?> entity;
    /**
     * <code>unionKeys</code>
     * {@link java.util.List} <p>the <code>unionKeys</code> field.</p>
     * @see java.util.List
     */
    /*
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
    /*
     * <code>unionIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>unionIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> unionIgnoreKeys;
    /**
     * <code>unionIdentity</code>
     * <p>the <code>unionIdentity</code> field.</p>
     */
    /*
     * <code>unionIdentity</code>
     * <p>the <code>unionIdentity</code> field.</p>
     */
    protected boolean unionIdentity;
    /**
     * <code>useUnionKey</code>
     * <p>the <code>useUnionKey</code> field.</p>
     */
    /*
     * <code>useUnionKey</code>
     * <p>the <code>useUnionKey</code> field.</p>
     */
    protected boolean useUnionKey;
    /**
     * <code>linkKeys</code>
     * {@link java.util.List} <p>the <code>linkKeys</code> field.</p>
     * @see java.util.List
     */
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
     * <code>resultMap</code>
     * {@link java.lang.String} <p>the <code>resultMap</code> field.</p>
     * @see java.lang.String
     */
    protected String resultMap;
    /**
     * <code>autoResultMap</code>
     * <p>the <code>autoResultMap</code> field.</p>
     */
    /*
     * <code>autoResultMap</code>
     * <p>the <code>autoResultMap</code> field.</p>
     */
    protected boolean autoResultMap;
    /**
     * <code>autoResultMaps</code>
     * {@link java.util.List} <p>the <code>autoResultMaps</code> field.</p>
     * @see java.util.List
     */
    /*
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
    /*
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
    /*
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
    /*
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
    /*
     * <code>excludeIgnoreKeys</code>
     * {@link java.util.List} <p>the <code>excludeIgnoreKeys</code> field.</p>
     * @see java.util.List
     */
    protected List<String> excludeIgnoreKeys;
    /**
     * <code>allColumns</code>
     * {@link java.util.List} <p>the <code>allColumns</code> field.</p>
     * @see java.util.List
     */
    /*
     * <code>allColumns</code>
     * {@link java.util.List} <p>the <code>allColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> allColumns;
    /**
     * <code>identityColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>identityColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>identityColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>identityColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn identityColumn;
    /**
     * <code>logicColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>logicColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
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
    /*
     * <code>operateColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>operateColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn operateColumn;
    /**
     * <code>alertColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>alertColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>alertColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>alertColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn alertColumn;
    /**
     * <code>linkColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>linkColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>linkColumn</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the <code>linkColumn</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected MybatisColumn linkColumn;
    /**
     * <code>uniqueColumns</code>
     * {@link java.util.List} <p>the <code>uniqueColumns</code> field.</p>
     * @see java.util.List
     */
    /*
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
    /*
     * <code>unionColumns</code>
     * {@link java.util.List} <p>the <code>unionColumns</code> field.</p>
     * @see java.util.List
     */
    protected List<MybatisColumn> unionColumns;
    /**
     * <code>ready</code>
     * <p>the <code>ready</code> field.</p>
     */
    /*
     * <code>ready</code>
     * <p>the <code>ready</code> field.</p>
     */
    protected boolean ready;
    /**
     * <code>initiates</code>
     * {@link java.util.Set} <p>the <code>initiates</code> field.</p>
     * @see java.util.Set
     */
    /*
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
    /*
     * <code>fieldColumns</code>
     * {@link java.util.Map} <p>the <code>fieldColumns</code> field.</p>
     * @see java.util.Map
     */
    protected Map<String, MybatisColumn> fieldColumns = new HashMap<>();

    /**
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param entity {@link java.lang.Class} <p>the entity parameter is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    /*
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param entity {@link java.lang.Class} <p>the entity parameter is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public MybatisTable(Class<?> entity) {
        this.entity = entity;
        this.allColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    /**
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @param entity     {@link java.lang.Class} <p>the entity parameter is <code>Class</code> type.</p>
     * @see java.util.Map
     * @see java.lang.Class
     */
    /*
     * <code>MybatisTable</code>
     * Instantiates a new mybatis table.
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @param entity     {@link java.lang.Class} <p>the entity parameter is <code>Class</code> type.</p>
     * @see java.util.Map
     * @see java.lang.Class
     */
    public MybatisTable(Map<String, String> properties, Class<?> entity) {
        super(properties);
        this.entity = entity;
        this.allColumns = new ArrayList<>();
        this.uniqueColumns = new ArrayList<>();
        this.unionColumns = new ArrayList<>();
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     */
    /*
     * <code>of</code>
     * <p>the method.</p>
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     */
    public static MybatisTable of(Class<?> clazz) {
        return new MybatisTable(clazz);
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param clazz      {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Map
     */
    /*
     * <code>of</code>
     * <p>the method.</p>
     * @param clazz      {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Map
     */
    public static MybatisTable of(Class<?> clazz, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisTable(properties, clazz);
        } else {
            return new MybatisTable(clazz);
        }
    }

    /**
     * <code>tableName</code>
     * <p>the name method.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
     * <code>tableName</code>
     * <p>the name method.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String tableName() {
        return Stream.of(this.catalog, this.schema, this.table)
                .filter(GeneralUtils::isNotEmpty)
                .collect(Collectors.joining("."));
    }

    /**
     * <code>fields</code>
     * <p>the method.</p>
     * @return {@link java.util.List} <p>the return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>fields</code>
     * <p>the method.</p>
     * @return {@link java.util.List} <p>the return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisField> fields() {
        return this.allColumns.stream().map(MybatisColumn::getField).collect(Collectors.toList());
    }

    /**
     * <code>columnNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>columnNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> columnNames() {
        return this.allColumns.stream().map(MybatisColumn::getColumnName).collect(Collectors.toList());
    }

    /**
     * <code>fieldNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>fieldNames</code>
     * <p>the names method.</p>
     * @return {@link java.util.List} <p>the names return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<String> fieldNames() {
        return this.allColumns.stream().map(MybatisColumn::property).collect(Collectors.toList());
    }

    /**
     * <code>readyColumns</code>
     * <p>the columns method.</p>
     */
    /*
     * <code>readyColumns</code>
     * <p>the columns method.</p>
     */
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
            /* 如果是联合主键 */
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
            /* 如果是链接外键 */
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
            /* 如果是链接外键 */
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
            /* 如果是链接外键 */
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

    /**
     * <code>addColumn</code>
     * <p>the column method.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>addColumn</code>
     * <p>the column method.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    public void addColumn(MybatisColumn column) {
        /* 不重复添加同名的列 */
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
            /* 同名列在父类存在时，说明是子类覆盖的，字段的顺序应该更靠前 */
            MybatisColumn existsColumn = this.allColumns.remove(this.allColumns.indexOf(column));
            if (GeneralUtils.isValid(column.getOrder())) {
                this.allColumns.add(column.getOrder(), existsColumn);
            } else {
                this.allColumns.add(0, existsColumn);
            }
        }
    }

    /**
     * <code>refreshColumn</code>
     * <p>the column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>refreshColumn</code>
     * <p>the column method.</p>
     * @param columns {@link java.util.List} <p>the columns parameter is <code>List</code> type.</p>
     * @param column  {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column parameter is <code>MybatisColumn</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    protected void refreshColumn(List<MybatisColumn> columns, MybatisColumn column) {
        if (GeneralUtils.isValid(column.getUnionIndex())) {
            columns.add(column.getUnionIndex(),column);
        } else if (GeneralUtils.isValid(column.getOrder())) {
            columns.add(column.getOrder(),column);
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
    /*
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
            String columnName = column.getColumnName();
            /* 去掉可能存在的分隔符，例如：`order` */
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
     * <code>getTypeHandlerInstance</code>
     * <p>the type handler instance getter method.</p>
     * @param javaTypeClass    {@link java.lang.Class} <p>the java type class parameter is <code>Class</code> type.</p>
     * @param typeHandlerClass {@link java.lang.Class} <p>the type handler class parameter is <code>Class</code> type.</p>
     * @return {@link org.apache.ibatis.type.TypeHandler} <p>the type handler instance return object is <code>TypeHandler</code> type.</p>
     * @see java.lang.Class
     * @see org.apache.ibatis.type.TypeHandler
     */
    /*
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
     * <code>allColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>allColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> allColumns() {
        return this.allColumns;
    }

    /**
     * <code>fieldColumn</code>
     * <p>the column method.</p>
     * @param fieldName {@link java.lang.String} <p>the field name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column return object is <code>MybatisColumn</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    /*
     * <code>fieldColumn</code>
     * <p>the column method.</p>
     * @param fieldName {@link java.lang.String} <p>the field name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the column return object is <code>MybatisColumn</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    public MybatisColumn fieldColumn(String fieldName) {
        if (GeneralUtils.isEmpty(this.fieldColumns)) {
            this.fieldColumns = this.allColumns.stream().collect(Collectors.toMap(MybatisColumn::fieldName, Function.identity()));
        }
        return this.fieldColumns.get(fieldName);
    }

    /**
     * <code>identityColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>identityColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> identityColumns() {
        if (GeneralUtils.isNotEmpty(this.unionColumns) && this.unionColumns.size() > 1) {
            return this.unionColumns;
        }
        return Collections.singletonList(this.identityColumn);
    }

    /**
     * <code>normalColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>normalColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> normalColumns() {
        return this.allColumns.stream().filter(column -> !column.isIdentity() && !column.isPrimaryKey()
                && !column.isUnionKey() && !column.isLogic() && !column.isOperate()).collect(Collectors.toList());
    }

    /**
     * <code>selectColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>selectColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> selectColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isSelect).collect(Collectors.toList());
    }

    /**
     * <code>whereColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>whereColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> whereColumns() {
        return this.allColumns;
    }

    /**
     * <code>uniqueColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
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
    /*
     * <code>insertColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> insertColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isInsert).collect(Collectors.toList());
    }

    /**
     * <code>updateColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>updateColumns</code>
     * <p>the columns method.</p>
     * @return {@link java.util.List} <p>the columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> updateColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isUpdate).collect(Collectors.toList());
    }

    /**
     * <code>forceInsertColumns</code>
     * <p>the insert columns method.</p>
     * @return {@link java.util.List} <p>the insert columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>forceInsertColumns</code>
     * <p>the insert columns method.</p>
     * @return {@link java.util.List} <p>the insert columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> forceInsertColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isForceInsert).collect(Collectors.toList());
    }

    /**
     * <code>forceUpdateColumns</code>
     * <p>the update columns method.</p>
     * @return {@link java.util.List} <p>the update columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    /*
     * <code>forceUpdateColumns</code>
     * <p>the update columns method.</p>
     * @return {@link java.util.List} <p>the update columns return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public List<MybatisColumn> forceUpdateColumns() {
        return this.allColumns.stream().filter(MybatisColumn::isForceUpdate).collect(Collectors.toList());
    }

    /**
     * <code>groupByColumns</code>
     * <p>the by columns method.</p>
     * @return {@link java.util.Optional} <p>the by columns return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
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
    /*
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
    /*
     * <code>orderByColumns</code>
     * <p>the by columns method.</p>
     * @return {@link java.util.Optional} <p>the by columns return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
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
     * <code>baseColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
     * <code>baseColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String baseColumnList() {
        return selectColumns().stream().map(MybatisColumn::getColumnName).collect(Collectors.joining(", "));
    }

    /**
     * <code>baseColumnAsPropertyList</code>
     * <p>the column as property list method.</p>
     * @return {@link java.lang.String} <p>the column as property list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
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
     * <code>selectColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
     * <code>selectColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String selectColumnList() {
        return selectColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>insertColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
     * <code>insertColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String insertColumnList() {
        return insertColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>identityColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    /*
     * <code>identityColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.lang.String} <p>the column list return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String identityColumnList() {
        return identityColumns().stream().map(MybatisColumn::getColumnName).distinct().collect(Collectors.joining(", "));
    }

    /**
     * <code>groupByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
     * <code>groupByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> groupByColumnList() {
        Optional<List<MybatisColumn>> groupByColumns = groupByColumns();
        return groupByColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::getColumnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>groupByColumn</code>
     * <p>the by column method.</p>
     * @return {@link java.util.Optional} <p>the by column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
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
     * <code>havingColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.util.Optional} <p>the column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
     * <code>havingColumnList</code>
     * <p>the column list method.</p>
     * @return {@link java.util.Optional} <p>the column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> havingColumnList() {
        Optional<List<MybatisColumn>> havingColumns = havingColumns();
        return havingColumns.map(mybatisColumns -> mybatisColumns.stream().map(MybatisColumn::getColumnName)
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>havingColumn</code>
     * <p>the column method.</p>
     * @return {@link java.util.Optional} <p>the column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
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
     * <code>orderByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
     * <code>orderByColumnList</code>
     * <p>the by column list method.</p>
     * @return {@link java.util.Optional} <p>the by column list return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> orderByColumnList() {
        Optional<List<MybatisColumn>> orderByColumns = orderByColumns();
        return orderByColumns.map(mybatisColumns -> mybatisColumns.stream()
                .map(column -> column.getColumnName() + " " + column.getSortType().getKey())
                .collect(Collectors.joining(", ")));
    }

    /**
     * <code>orderByColumn</code>
     * <p>the by column method.</p>
     * @return {@link java.util.Optional} <p>the by column return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    /*
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
     * <code>isExcludeSuperClass</code>
     * <p>the exclude super class method.</p>
     * @param superClass {@link java.lang.Class} <p>the super class parameter is <code>Class</code> type.</p>
     * @return boolean <p>the exclude super class return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    /*
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
    /*
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
