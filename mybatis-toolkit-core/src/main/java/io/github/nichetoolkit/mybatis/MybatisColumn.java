package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.enums.SortType;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.nichetoolkit.mybatis.MybatisTable.DELIMITER;

/**
 * <code>MybatisColumn</code>
 * <p>The type mybatis column class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProperty
 * @see lombok.Data
 * @since Jdk1.8
 */
@Data
public class MybatisColumn extends MybatisProperty<MybatisColumn> {
    /**
     * <code>field</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the <code>field</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected final MybatisField field;
    /**
     * <code>table</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the <code>table</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    protected MybatisTable table;
    /**
     * <code>columnName</code>
     * {@link java.lang.String} <p>the <code>columnName</code> field.</p>
     * @see java.lang.String
     */
    protected String column;
    /**
     * <code>comment</code>
     * {@link java.lang.String} <p>the <code>comment</code> field.</p>
     * @see java.lang.String
     */
    protected String comment;
    /**
     * <code>order</code>
     * {@link java.lang.Integer} <p>the <code>order</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer order;
    /**
     * <code>identity</code>
     * <p>the <code>identity</code> field.</p>
     */
    protected boolean identity;
    /**
     * <code>primaryKey</code>
     * <p>the <code>primaryKey</code> field.</p>
     */
    protected boolean primaryKey;
    /**
     * <code>linkKey</code>
     * <p>the <code>linkKey</code> field.</p>
     */
    protected boolean linkKey;
    /**
     * <code>unionKey</code>
     * <p>the <code>unionKey</code> field.</p>
     */
    protected boolean unionKey;
    /**
     * <code>unionIndex</code>
     * {@link java.lang.Integer} <p>the <code>unionIndex</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer unionIndex;
    /**
     * <code>alertKey</code>
     * <p>the <code>alertKey</code> field.</p>
     */
    protected boolean alertKey;
    /**
     * <code>sortType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the <code>sortType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     */
    protected SortType sortType;
    /**
     * <code>priority</code>
     * <p>the <code>priority</code> field.</p>
     */
    protected int priority;
    /**
     * <code>exclude</code>
     * <p>the <code>exclude</code> field.</p>
     */
    protected boolean exclude;
    /**
     * <code>forceInsert</code>
     * <p>the <code>forceInsert</code> field.</p>
     */
    protected boolean forceInsert;
    /**
     * <code>forceInsertValue</code>
     * {@link java.lang.String} <p>the <code>forceInsertValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceInsertValue;
    /**
     * <code>forceUpdate</code>
     * <p>the <code>forceUpdate</code> field.</p>
     */
    protected boolean forceUpdate;
    /**
     * <code>forceUpdateValue</code>
     * {@link java.lang.String} <p>the <code>forceUpdateValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceUpdateValue;
    /**
     * <code>unique</code>
     * <p>the <code>unique</code> field.</p>
     */
    protected boolean unique;
    /**
     * <code>logic</code>
     * <p>the <code>logic</code> field.</p>
     */
    protected boolean logic;
    /**
     * <code>operate</code>
     * <p>the <code>operate</code> field.</p>
     */
    protected boolean operate;
    /**
     * <code>select</code>
     * <p>the <code>select</code> field.</p>
     */
    protected boolean select = true;
    /**
     * <code>insert</code>
     * <p>the <code>insert</code> field.</p>
     */
    protected boolean insert = true;
    /**
     * <code>update</code>
     * <p>the <code>update</code> field.</p>
     */
    protected boolean update = true;
    /**
     * <code>jdbcType</code>
     * {@link org.apache.ibatis.type.JdbcType} <p>the <code>jdbcType</code> field.</p>
     * @see org.apache.ibatis.type.JdbcType
     */
    protected JdbcType jdbcType;
    /**
     * <code>typeHandler</code>
     * {@link java.lang.Class} <p>the <code>typeHandler</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<? extends TypeHandler<?>> typeHandler;
    /**
     * <code>numericScale</code>
     * {@link java.lang.String} <p>the <code>numericScale</code> field.</p>
     * @see java.lang.String
     */
    protected String numericScale;

    /**
     * <code>MybatisColumn</code>
     * Instantiates a new mybatis column.
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(MybatisField field) {
        this.field = field;
    }

    /**
     * <code>MybatisColumn</code>
     * Instantiates a new mybatis column.
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @see java.util.Map
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(Map<String, String> properties, MybatisField field) {
        super(properties);
        this.field = field;
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    public static MybatisColumn of(MybatisField field) {
        return new MybatisColumn(field);
    }

    /**
     * <code>of</code>
     * <p>the method.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>the field parameter is <code>MybatisField</code> type.</p>
     * @param properties {@link java.util.Map} <p>the properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>the return object is <code>MybatisColumn</code> type.</p>
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

    public String columnName() {
        return this.column;
    }

    /**
     * <code>fieldName</code>
     * <p>the name method.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String fieldName() {
        return this.field.fieldName();
    }

    /**
     * <code>javaType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> javaType() {
        return this.field.fieldType();
    }

    /**
     * <code>property</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property() {
        return property("");
    }


    /**
     * <code>aliasColumn</code>
     * <p>the column method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumn(String alias) {
        if (GeneralUtils.isNotEmpty(alias)) {
            return alias + "." + this.column;
        } else {
            return this.column;
        }
    }

    /**
     * <code>property</code>
     * <p>the method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property(String prefix) {
        return prefix + this.field.fieldName();
    }

    /**
     * <code>variable</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable() {
        return variable("");
    }

    /**
     * <code>variable</code>
     * <p>the method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            return "#{" + property(prefix)
                    + jdbcTypeVariable().orElse("")
                    + typeHandlerVariable().orElse("")
                    + numericScaleVariable().orElse("") + "}";
        }
    }

    /**
     * <code>excluded</code>
     * <p>the method.</p>
     * @param tablename {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     */
    public String excluded(@NonNull String tablename) {
        if (this.forceUpdate) {
            return this.columnName() + " = " + this.forceUpdateValue;
        } else {
            return this.columnName() + " = CASE WHEN EXCLUDED." + this.columnName() + " IS NOT NULL THEN EXCLUDED." +
                    this.columnName() + " ELSE " + tablename + "." + this.columnName() + " END";
        }
    }

    /**
     * <code>excluded</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String excluded() {
        return excluded(this.table.tablename());
    }

    /**
     * <code>jdbcTypeVariable</code>
     * <p>the type variable method.</p>
     * @return {@link java.util.Optional} <p>the type variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> jdbcTypeVariable() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(", jdbcType=" + this.jdbcType);
        }
        return Optional.empty();
    }

    /**
     * <code>typeHandlerVariable</code>
     * <p>the handler variable method.</p>
     * @return {@link java.util.Optional} <p>the handler variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> typeHandlerVariable() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(", typeHandler=" + this.typeHandler.getName());
        }
        return Optional.empty();
    }

    /**
     * <code>numericScaleVariable</code>
     * <p>the scale variable method.</p>
     * @return {@link java.util.Optional} <p>the scale variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> numericScaleVariable() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(", numericScale=" + this.numericScale);
        }
        return Optional.empty();
    }

    /**
     * <code>columnAsProperty</code>
     * <p>the as property method.</p>
     * @return {@link java.lang.String} <p>the as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty() {
        return columnAsProperty("");
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>the column as property method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias) {
        return aliasColumnAsProperty(alias, "");
    }

    /**
     * <code>columnAsProperty</code>
     * <p>the as property method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty(String prefix) {
        return aliasColumnAsProperty("", prefix);
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>the column as property method.</p>
     * @param alias  {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias, String prefix) {
        /* 这里的column 和 property 的比较 应该是需要忽略界定符之后再比较 */
        /* mysql 中 【`order`】 应该认为是 和 field 的 【order】 相同 */
        String column = this.columnName();
        Matcher matcher = DELIMITER.matcher(this.columnName());
        if (matcher.find()) {
            column = matcher.group(1);
        }
        if (!Objects.equals(column, property(prefix))) {
            return aliasColumn(alias) + " AS " + property(prefix);
        }
        return aliasColumn(alias);
    }


    /**
     * <code>columnEqualsKey</code>
     * <p>the equals key method.</p>
     * @return {@link java.lang.String} <p>the equals key return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsKey() {
        return aliasColumnEqualsKey("");
    }

    /**
     * <code>aliasColumnEqualsKey</code>
     * <p>the column equals key method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals key return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsKey(String alias) {
        return aliasColumn(alias) + " = " + "${key}";
    }

    /**
     * <code>columnEqualsSign</code>
     * <p>the equals sign method.</p>
     * @return {@link java.lang.String} <p>the equals sign return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsSign() {
        return aliasColumnEqualsSign("");
    }

    /**
     * <code>aliasColumnEqualsSign</code>
     * <p>the column equals sign method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals sign return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsSign(String alias) {
        return aliasColumn(alias) + " = " + "${logicSign}";
    }

    /**
     * <code>columnEqualsOperate</code>
     * <p>the equals operate method.</p>
     * @return {@link java.lang.String} <p>the equals operate return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsOperate() {
        return aliasColumnEqualsOperate("");
    }

    /**
     * <code>aliasColumnEqualsOperate</code>
     * <p>the column equals operate method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals operate return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsOperate(String alias) {
        return aliasColumn(alias) + " = " + "${operate}";
    }

    /**
     * <code>columnEqualsLink</code>
     * <p>the equals link method.</p>
     * @return {@link java.lang.String} <p>the equals link return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsLink() {
        return aliasColumnEqualsLink("");
    }

    /**
     * <code>aliasColumnEqualsLink</code>
     * <p>the column equals link method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals link return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsLink(String alias) {
        return aliasColumn(alias) + " = " + "#{linkId}";
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>the equals property method.</p>
     * @return {@link java.lang.String} <p>the equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty() {
        return aliasColumnEqualsProperty("");
    }

    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>the column equals property method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias) {
        return aliasColumnEqualsProperty(alias, "");
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>the not equals property method.</p>
     * @return {@link java.lang.String} <p>the not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty() {
        return aliasColumnNotEqualsProperty("");
    }


    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>the column not equals property method.</p>
     * @param alias {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias) {
        return aliasColumnNotEqualsProperty(alias, "");
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>the equals property method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty(String prefix) {
        return aliasColumnEqualsProperty("", prefix);
    }


    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>the column equals property method.</p>
     * @param alias  {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + " = " + variable(prefix);
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>the not equals property method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty(String prefix) {
        return aliasColumnNotEqualsProperty("", prefix);
    }


    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>the column not equals property method.</p>
     * @param alias  {@link java.lang.String} <p>the alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + " != " + variable(prefix);
    }

    /**
     * <code>notNullTest</code>
     * <p>the null test method.</p>
     * @return {@link java.lang.String} <p>the null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest() {
        return notNullTest("");
    }

    /**
     * <code>notNullTest</code>
     * <p>the null test method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest(String prefix) {
        return property(prefix) + " != null";
    }

    /**
     * <code>notEmptyTest</code>
     * <p>the empty test method.</p>
     * @return {@link java.lang.String} <p>the empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notEmptyTest() {
        return notEmptyTest("");
    }

    /**
     * <code>notEmptyTest</code>
     * <p>the empty test method.</p>
     * @param prefix {@link java.lang.String} <p>the prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notEmptyTest(String prefix) {
        if (this.field.fieldType() == String.class) {
            return notNullTest(prefix) + " and " + property(prefix) + " != '' ";
        }
        return notNullTest();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisColumn)) return false;
        MybatisColumn that = (MybatisColumn) o;
        return this.columnName().equals(that.columnName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.columnName());
    }

    @Override
    public String toString() {
        return columnEqualsProperty();
    }
}
