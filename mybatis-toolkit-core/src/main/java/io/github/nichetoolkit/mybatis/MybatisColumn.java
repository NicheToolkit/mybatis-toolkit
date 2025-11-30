package io.github.nichetoolkit.mybatis;

import tools.jackson.databind.JavaType;
import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.ExcludedType;
import io.github.nichetoolkit.mybatis.enums.SortType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.regex.Matcher;

/**
 * <code>MybatisColumn</code>
 * <p>The mybatis column class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProperty
 * @see lombok.Setter
 * @see lombok.Getter
 * @since Jdk17
 */
@Setter
@Getter
public class MybatisColumn extends MybatisProperty<MybatisColumn> {
    /**
     * <code>field</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The <code>field</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected final MybatisField field;
    /**
     * <code>table</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The <code>table</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    protected MybatisTable table;
    /**
     * <code>column</code>
     * {@link java.lang.String} <p>The <code>column</code> field.</p>
     * @see java.lang.String
     */
    protected String column;
    /**
     * <code>comment</code>
     * {@link java.lang.String} <p>The <code>comment</code> field.</p>
     * @see java.lang.String
     */
    protected String comment;

    /**
     * <code>linkName</code>
     * {@link java.lang.String} <p>The <code>linkName</code> field.</p>
     * @see java.lang.String
     */
    protected String linkName;

    /**
     * <code>alertName</code>
     * {@link java.lang.String} <p>The <code>alertName</code> field.</p>
     * @see java.lang.String
     */
    protected String alertName;

    /**
     * <code>paramName</code>
     * {@link java.lang.String} <p>The <code>paramName</code> field.</p>
     * @see java.lang.String
     */
    protected String paramName;
    /**
     * <code>order</code>
     * {@link java.lang.Integer} <p>The <code>order</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer order;
    /**
     * <code>sortType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>The <code>sortType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     */
    protected SortType sortType;
    /**
     * <code>priority</code>
     * {@link java.lang.Integer} <p>The <code>priority</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer priority;

    /**
     * <code>forceInsert</code>
     * <p>The <code>forceInsert</code> field.</p>
     */
    protected boolean forceInsert;
    /**
     * <code>forceInsertValue</code>
     * {@link java.lang.String} <p>The <code>forceInsertValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceInsertValue;
    /**
     * <code>forceUpdate</code>
     * <p>The <code>forceUpdate</code> field.</p>
     */
    protected boolean forceUpdate;
    /**
     * <code>forceUpdateValue</code>
     * {@link java.lang.String} <p>The <code>forceUpdateValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceUpdateValue;

    /**
     * <code>jdbcType</code>
     * {@link org.apache.ibatis.type.JdbcType} <p>The <code>jdbcType</code> field.</p>
     * @see org.apache.ibatis.type.JdbcType
     */
    protected JdbcType jdbcType;
    /**
     * <code>typeHandler</code>
     * {@link java.lang.Class} <p>The <code>typeHandler</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<? extends TypeHandler<?>> typeHandler;
    /**
     * <code>numericScale</code>
     * {@link java.lang.String} <p>The <code>numericScale</code> field.</p>
     * @see java.lang.String
     */
    protected String numericScale;

    /**
     * <code>select</code>
     * <p>The <code>select</code> field.</p>
     */
    protected boolean select = true;
    /**
     * <code>insert</code>
     * <p>The <code>insert</code> field.</p>
     */
    protected boolean insert = true;
    /**
     * <code>update</code>
     * <p>The <code>update</code> field.</p>
     */
    protected boolean update = true;

    /**
     * <code>exclude</code>
     * <p>The <code>exclude</code> field.</p>
     */
    protected boolean exclude;

    /**
     * <code>identityKey</code>
     * <p>The <code>identityKey</code> field.</p>
     */
    protected boolean identityKey;
    /**
     * <code>primaryKey</code>
     * <p>The <code>primaryKey</code> field.</p>
     */
    protected boolean primaryKey;
    /**
     * <code>linkKey</code>
     * <p>The <code>linkKey</code> field.</p>
     */
    protected boolean linkKey;

    /**
     * <code>alertKey</code>
     * <p>The <code>alertKey</code> field.</p>
     */
    protected boolean alertKey;
    /**
     * <code>operateKey</code>
     * <p>The <code>operateKey</code> field.</p>
     */
    protected boolean operateKey;
    /**
     * <code>uniqueKey</code>
     * <p>The <code>uniqueKey</code> field.</p>
     */
    protected boolean uniqueKey;
    /**
     * <code>unionKey</code>
     * <p>The <code>unionKey</code> field.</p>
     */
    protected boolean unionKey;
    /**
     * <code>logicKey</code>
     * <p>The <code>logicKey</code> field.</p>
     */
    protected boolean logicKey;

    /**
     * <code>fickleType</code>
     * {@link tools.jackson.databind.JavaType} <p>The <code>fickleType</code> field.</p>
     * @see tools.jackson.databind.JavaType
     */
    protected JavaType fickleType;

    /**
     * <code>fickleKey</code>
     * <p>The <code>fickleKey</code> field.</p>
     */
    protected boolean fickleKey;

    /**
     * <code>fickleValue</code>
     * <p>The <code>fickleValue</code> field.</p>
     */
    protected boolean fickleValue;

    /**
     * <code>loadKey</code>
     * <p>The <code>loadKey</code> field.</p>
     */
    protected boolean loadKey;

    /**
     * <code>loadParam</code>
     * <p>The <code>loadParam</code> field.</p>
     */
    protected boolean loadParam;

    /**
     * <code>loadKeys</code>
     * {@link java.util.Set} <p>The <code>loadKeys</code> field.</p>
     * @see java.util.Set
     */
    protected final Set<String> loadKeys = new HashSet<>();

    /**
     * <code>loadTypes</code>
     * {@link java.util.List} <p>The <code>loadTypes</code> field.</p>
     * @see java.util.List
     */
    protected final List<Class<?>> loadTypes = new ArrayList<>();

    /**
     * <code>loadEntity</code>
     * <p>The <code>loadEntity</code> field.</p>
     */
    protected boolean loadEntity;

    /**
     * <code>loadRecursive</code>
     * <p>The <code>loadRecursive</code> field.</p>
     */
    protected boolean loadRecursive;

    /**
     * <code>loadTable</code>
     * {@link java.lang.String} <p>The <code>loadTable</code> field.</p>
     * @see java.lang.String
     */
    protected String loadTable;

    /**
     * <code>ignored</code>
     * <p>The <code>ignored</code> field.</p>
     */
    protected boolean ignored;

    /**
     * <code>MybatisColumn</code>
     * <p>Instantiates a new mybatis column.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(MybatisField field) {
        this.field = field;
        this.ignored = field.ignored();
    }

    /**
     * <code>MybatisColumn</code>
     * <p>Instantiates a new mybatis column.</p>
     * @param table  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The column parameter is <code>MybatisColumn</code> type.</p>
     * @param field  {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The field parameter is <code>RestFickle</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings("unchecked")
    protected MybatisColumn(MybatisTable table, MybatisColumn column, RestFickle<?> field) {
        MybatisTableStyle mybatisStyle = MybatisTableStyle.style(table.getStyleName());
        MybatisField fickleField = column.getField();
        this.field = MybatisField.ofFickleField(fickleField.entityType(), fickleField, field);
        this.table = table;
        this.column = GeneralUtils.isNotEmpty(field.getKey()) ? field.getKey() : mybatisStyle.columnName(field);
        this.comment = field.getDescription();
        this.jdbcType = field.getJdbcType() == JdbcType.JAVA_OBJECT ? null : field.getJdbcType();
        this.select = false;
        this.insert = false;
        this.update = false;
        TypeHandler<?> jdbcTypeHandler = field.getJdbcTypeHandler();
        if (GeneralUtils.isNotEmpty(jdbcTypeHandler)) {
            this.typeHandler = (Class<? extends TypeHandler<?>>) jdbcTypeHandler.getClass();
        }
    }

    /**
     * <code>MybatisColumn</code>
     * <p>Instantiates a new mybatis column.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see java.util.Map
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(Map<String, String> properties, MybatisField field) {
        super(properties);
        this.field = field;
        this.ignored = field.ignored();
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The of return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    public static MybatisColumn of(MybatisField field) {
        return new MybatisColumn(field);
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The of return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see java.util.Map
     */
    public static MybatisColumn of(MybatisField field, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisColumn(properties, field);
        } else {
            return new MybatisColumn(field);
        }
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param table  {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param column {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The column parameter is <code>MybatisColumn</code> type.</p>
     * @param field  {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The field parameter is <code>RestFickle</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The of return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     */
    public static MybatisColumn of(MybatisTable table, MybatisColumn column, RestFickle<?> field) {
        return new MybatisColumn(table, column, field);
    }

    /**
     * <code>getFickleField</code>
     * <p>The get fickle field getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The get fickle field return object is <code>RestFickle</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     */
    public RestFickle<?> getFickleField() {
        return this.field.fickleField();
    }

    /**
     * <code>isAnyKey</code>
     * <p>The is any key method.</p>
     * @return boolean <p>The is any key return object is <code>boolean</code> type.</p>
     */
    public boolean isAnyKey() {
        return this.identityKey | this.primaryKey | this.linkKey | this.unionKey
                | this.alertKey | this.logicKey | this.operateKey | this.fickleKey | this.fickleValue;
    }


    /**
     * <code>isFickleColum</code>
     * <p>The is fickle colum method.</p>
     * @return boolean <p>The is fickle colum return object is <code>boolean</code> type.</p>
     */
    public boolean isFickleColum() {
        return this.field.isFickleField;
    }

    /**
     * <code>isSpecialIdentity</code>
     * <p>The is special identity method.</p>
     * @return boolean <p>The is special identity return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialIdentity() {
        return this.field.isIdentity();
    }

    /**
     * <code>isSpecialLinkage</code>
     * <p>The is special linkage method.</p>
     * @return boolean <p>The is special linkage return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialLinkage() {
        return this.field.isLinkage();
    }

    /**
     * <code>isSpecialAlertness</code>
     * <p>The is special alertness method.</p>
     * @return boolean <p>The is special alertness return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialAlertness() {
        return this.field.isAlertness();
    }

    /**
     * <code>isSpecialFickleness</code>
     * <p>The is special fickleness method.</p>
     * @return boolean <p>The is special fickleness return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialFickleness() {
        return this.field.isFickleness();
    }

    /**
     * <code>isParentNotEmpty</code>
     * <p>The is parent not empty method.</p>
     * @return boolean <p>The is parent not empty return object is <code>boolean</code> type.</p>
     */
    public boolean isParentNotEmpty() {
        return this.field.isParentNotEmpty();
    }

    /**
     * <code>ofParentPrefix</code>
     * <p>The of parent prefix method.</p>
     * @return {@link java.lang.String} <p>The of parent prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String ofParentPrefix() {
        return this.field.ofParentPrefix(true);
    }

    /**
     * <code>ofParentPrefix</code>
     * <p>The of parent prefix method.</p>
     * @param isPeriod boolean <p>The is period parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The of parent prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String ofParentPrefix(boolean isPeriod) {
        return this.field.ofParentPrefix(isPeriod);
    }

    /**
     * <code>columnName</code>
     * <p>The column name method.</p>
     * @return {@link java.lang.String} <p>The column name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnName() {
        return this.column;
    }

    /**
     * <code>paramName</code>
     * <p>The param name method.</p>
     * @return {@link java.lang.String} <p>The param name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String paramName() {
        if (GeneralUtils.isEmpty(this.paramName)) {
            return this.column;
        }
        return this.paramName;
    }

    /**
     * <code>addLoadKey</code>
     * <p>The add load key method.</p>
     * @param loadKey {@link java.lang.String} <p>The load key parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void addLoadKey(String loadKey) {
        this.loadKeys.add(loadKey);
    }

    /**
     * <code>addLoadType</code>
     * <p>The add load type method.</p>
     * @param loadType {@link java.lang.Class} <p>The load type parameter is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public void addLoadType(Class<?> loadType) {
        this.loadTypes.add(loadType);
    }

    /**
     * <code>fieldName</code>
     * <p>The field name method.</p>
     * @return {@link java.lang.String} <p>The field name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String fieldName() {
        return this.field.fieldName();
    }

    /**
     * <code>javaType</code>
     * <p>The java type method.</p>
     * @return {@link java.lang.Class} <p>The java type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> javaType() {
        return this.field.fieldType();
    }

    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property() {
        return property(SQLConstants.EMPTY);
    }

    /**
     * <code>aliasColumn</code>
     * <p>The alias column method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumn(String alias) {
        if (GeneralUtils.isNotEmpty(alias)) {
            return alias + SQLConstants.PERIOD + this.column;
        } else {
            return this.column;
        }
    }

    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property(String prefix) {
        return prefix + this.field.fieldName();
    }

    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param index  {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     */
    public String property(String prefix, Integer index) {
        return prefix + SQLConstants.SQUARE_LT + index + SQLConstants.SQUARE_GT + SQLConstants.PERIOD + EntityConstants.VALUE;
    }

    /**
     * <code>variable</code>
     * <p>The variable method.</p>
     * @return {@link java.lang.String} <p>The variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable() {
        return variable(SQLConstants.EMPTY);
    }

    /**
     * <code>variable</code>
     * <p>The variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            return signerVariable(prefix);
        }
    }

    /**
     * <code>variable</code>
     * <p>The variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param index  {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     */
    public String variable(String prefix, Integer index) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            return signerVariable(prefix, index);
        }
    }

    /**
     * <code>prefixVariable</code>
     * <p>The prefix variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The prefix variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String prefixVariable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            if (this.field.isParentNotEmpty()) {
                prefix = prefix + this.field.ofParentPrefix(true);
            }
            return signerVariable(prefix);
        }
    }

    /**
     * <code>prefixVariable</code>
     * <p>The prefix variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param index  {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The prefix variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     */
    public String prefixVariable(String prefix, Integer index) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            if (this.field.isParentNotEmpty()) {
                prefix = prefix + this.field.ofParentPrefix(false);
            }
            return signerVariable(prefix, index);
        }
    }

    /**
     * <code>signerVariable</code>
     * <p>The signer variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The signer variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String signerVariable(String prefix) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }

    /**
     * <code>signerVariable</code>
     * <p>The signer variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param index  {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The signer variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     */
    public String signerVariable(String prefix, Integer index) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property(prefix, index)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }

    /**
     * <code>dollarVariable</code>
     * <p>The dollar variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The dollar variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String dollarVariable(String prefix) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }

    /**
     * <code>dollarVariable</code>
     * <p>The dollar variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param index  {@link java.lang.Integer} <p>The index parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The dollar variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     */
    public String dollarVariable(String prefix, Integer index) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property(prefix, index)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }


    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param property     {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.String
     */
    public static String property(Class<?> identityType, String property) {
        if (String.class == identityType || Date.class == identityType) {
            return signerProperty(property);
        } else {
            return dollarProperty(property);
        }
    }

    /**
     * <code>signerProperty</code>
     * <p>The signer property method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The signer property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String signerProperty(String property) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    /**
     * <code>dollarProperty</code>
     * <p>The dollar property method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The dollar property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String dollarProperty(String property) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    /**
     * <code>excluded</code>
     * <p>The excluded method.</p>
     * @param excludedType {@link io.github.nichetoolkit.mybatis.enums.ExcludedType} <p>The excluded type parameter is <code>ExcludedType</code> type.</p>
     * @param tableName    {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The excluded return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.ExcludedType
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public String excluded(ExcludedType excludedType, @NonNull String tableName) {
        if (this.forceUpdate) {
            return this.columnName() + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                    + SQLConstants.BLANK + this.forceUpdateValue;
        } else {
            String excludedColumn = excludedType.getKey() + SQLConstants.PERIOD + this.columnName();
            if (excludedType == ExcludedType.VALUES) {
                excludedColumn = excludedType.getKey() + SQLConstants.BRACE_LT + this.columnName() + SQLConstants.BRACE_GT;
            }
            return this.columnName() + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ + SQLConstants.BLANK + SQLConstants.CASE
                    + SQLConstants.BLANK + SQLConstants.WHEN + SQLConstants.BLANK + excludedColumn + SQLConstants.BLANK
                    + SQLConstants.IS_NOT_NULL + SQLConstants.BLANK + SQLConstants.THEN + SQLConstants.BLANK + excludedColumn + SQLConstants.BLANK
                    + SQLConstants.ELSE + SQLConstants.BLANK + tableName + SQLConstants.PERIOD + this.columnName() + SQLConstants.BLANK + SQLConstants.END;
        }
    }

    /**
     * <code>excluded</code>
     * <p>The excluded method.</p>
     * @return {@link java.lang.String} <p>The excluded return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String excluded() {
        return excluded(ExcludedType.EXCLUDED, this.table.tableName());
    }

    /**
     * <code>excluded</code>
     * <p>The excluded method.</p>
     * @param excludedType {@link io.github.nichetoolkit.mybatis.enums.ExcludedType} <p>The excluded type parameter is <code>ExcludedType</code> type.</p>
     * @return {@link java.lang.String} <p>The excluded return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.ExcludedType
     * @see java.lang.String
     */
    public String excluded(ExcludedType excludedType) {
        return excluded(excludedType, this.table.tableName());
    }

    /**
     * <code>keyword</code>
     * <p>The keyword method.</p>
     * @param keyword {@link java.lang.String} <p>The keyword parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The keyword return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String keyword(String keyword) {
        return SQLConstants.COMMA + SQLConstants.BLANK + keyword + SQLConstants.CONTRAST_EQ;
    }

    /**
     * <code>jdbcTypeVariable</code>
     * <p>The jdbc type variable method.</p>
     * @return {@link java.util.Optional} <p>The jdbc type variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> jdbcTypeVariable() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(keyword(SQLConstants.JDBC_TYPE) + this.jdbcType);
        }
        return Optional.empty();
    }

    /**
     * <code>typeHandlerVariable</code>
     * <p>The type handler variable method.</p>
     * @return {@link java.util.Optional} <p>The type handler variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> typeHandlerVariable() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(keyword(SQLConstants.TYPE_HANDLER) + this.typeHandler.getName());
        }
        return Optional.empty();
    }

    /**
     * <code>numericScaleVariable</code>
     * <p>The numeric scale variable method.</p>
     * @return {@link java.util.Optional} <p>The numeric scale variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> numericScaleVariable() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(keyword(SQLConstants.NUMERIC_SCALE) + this.numericScale);
        }
        return Optional.empty();
    }

    /**
     * <code>columnAsProperty</code>
     * <p>The column as property method.</p>
     * @return {@link java.lang.String} <p>The column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty() {
        return columnAsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>The alias column as property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias) {
        return aliasColumnAsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnAsProperty</code>
     * <p>The column as property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty(String prefix) {
        return aliasColumnAsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>The alias column as property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias, String prefix) {
        String column = this.columnName();
        Matcher matcher = MybatisTable.DELIMITER.matcher(this.columnName());
        if (matcher.find()) {
            column = matcher.group(1);
        }
        if (!Objects.equals(column, property(prefix))) {
            return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.AS
                    + SQLConstants.BLANK + property(prefix);
        }
        return aliasColumn(alias);
    }

    /**
     * <code>columnEqualsDollar</code>
     * <p>The column equals dollar method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsDollar(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, true);
    }

    /**
     * <code>aliasColumnEqualsDollar</code>
     * <p>The alias column equals dollar method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsDollar(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, true);
    }

    /**
     * <code>columnEqualsSigner</code>
     * <p>The column equals signer method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsSigner(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, false);
    }

    /**
     * <code>aliasColumnEqualsSigner</code>
     * <p>The alias column equals signer method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsSigner(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, false);
    }

    /**
     * <code>columnEqualsVariable</code>
     * <p>The column equals variable method.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsVariable(String variable, boolean isDollarOrSigner) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, isDollarOrSigner);
    }

    /**
     * <code>aliasColumnEqualsVariable</code>
     * <p>The alias column equals variable method.</p>
     * @param alias            {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsVariable(String alias, String variable, boolean isDollarOrSigner) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + (isDollarOrSigner ? dollarProperty(variable) : signerProperty(variable));
    }

    /**
     * <code>aliasColumnEqualsValue</code>
     * <p>The alias column equals value method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals value return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public String aliasColumnEqualsValue(String alias, Object value) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + SqlBuilder.sqlBuilder().value(value);
    }

    /**
     * <code>columnNotEqualsDollar</code>
     * <p>The column not equals dollar method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsDollar(String variable) {
        return aliasColumnNotEqualsVariable(SQLConstants.EMPTY, variable, true);
    }

    /**
     * <code>aliasNotColumnEqualsDollar</code>
     * <p>The alias not column equals dollar method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias not column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasNotColumnEqualsDollar(String alias, String variable) {
        return aliasColumnNotEqualsVariable(alias, variable, true);
    }

    /**
     * <code>columnNotEqualsSigner</code>
     * <p>The column not equals signer method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsSigner(String variable) {
        return aliasColumnNotEqualsVariable(SQLConstants.EMPTY, variable, false);
    }

    /**
     * <code>aliasColumnNotEqualsSigner</code>
     * <p>The alias column not equals signer method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsSigner(String alias, String variable) {
        return aliasColumnNotEqualsVariable(alias, variable, false);
    }

    /**
     * <code>columnNotEqualsVariable</code>
     * <p>The column not equals variable method.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsVariable(String variable, boolean isDollarOrSigner) {
        return aliasColumnNotEqualsVariable(SQLConstants.EMPTY, variable, isDollarOrSigner);
    }

    /**
     * <code>aliasColumnNotEqualsVariable</code>
     * <p>The alias column not equals variable method.</p>
     * @param alias            {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsVariable(String alias, String variable, boolean isDollarOrSigner) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + (isDollarOrSigner ? dollarProperty(variable) : signerProperty(variable));
    }

    /**
     * <code>aliasColumnNotEqualsValue</code>
     * <p>The alias column not equals value method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals value return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    public String aliasColumnNotEqualsValue(String alias, Object value) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + SqlBuilder.sqlBuilder().value(value);
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>The column equals property method.</p>
     * @return {@link java.lang.String} <p>The column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty() {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>columnEqualsValue</code>
     * <p>The column equals value method.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals value return object is <code>String</code> type.</p>
     * @see java.lang.Object
     * @see java.lang.String
     */
    public String columnEqualsValue(Object value) {
        return aliasColumnEqualsValue(SQLConstants.EMPTY, value);
    }

    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>The alias column equals property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias) {
        return aliasColumnEqualsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>The column not equals property method.</p>
     * @return {@link java.lang.String} <p>The column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty() {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>columnNotEqualsValue</code>
     * <p>The column not equals value method.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals value return object is <code>String</code> type.</p>
     * @see java.lang.Object
     * @see java.lang.String
     */
    public String columnNotEqualsValue(Object value) {
        return aliasColumnNotEqualsValue(SQLConstants.EMPTY, value);
    }

    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>The alias column not equals property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias) {
        return aliasColumnNotEqualsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>The column equals property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty(String prefix) {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>The alias column equals property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + variable(prefix);
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>The column not equals property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty(String prefix) {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>The alias column not equals property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + variable(prefix);
    }

    /**
     * <code>notNullTest</code>
     * <p>The not null test method.</p>
     * @return {@link java.lang.String} <p>The not null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest() {
        return notNullTest(SQLConstants.EMPTY);
    }

    /**
     * <code>notNullTest</code>
     * <p>The not null test method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The not null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest(String prefix) {
        return property(prefix) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + ScriptConstants.NULL;
    }

    /**
     * <code>notEmptyTest</code>
     * <p>The not empty test method.</p>
     * @return {@link java.lang.String} <p>The not empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notEmptyTest() {
        return notEmptyTest(SQLConstants.EMPTY);
    }

    /**
     * <code>notEmptyTest</code>
     * <p>The not empty test method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The not empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notEmptyTest(String prefix) {
        if (this.field.fieldType() == String.class || this.field.fieldType() == Date.class) {
            return notNullTest(prefix) + SQLConstants.BLANK + ScriptConstants.AND
                    + SQLConstants.BLANK + property(prefix) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                    + SQLConstants.BLANK + SQLConstants.EMPTY;
        }
        return notNullTest();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisColumn that)) return false;
        return this.columnName().equals(that.columnName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.columnName());
    }

    @Override
    public String toString() {
        return this.columnName();
    }
}
