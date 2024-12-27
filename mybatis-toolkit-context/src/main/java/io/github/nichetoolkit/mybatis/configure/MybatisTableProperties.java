package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.enums.ExcludedType;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <code>MybatisTableProperties</code>
 * <p>The mybatis table properties class.</p>
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  org.springframework.stereotype.Component
 * @see  org.springframework.boot.context.properties.ConfigurationProperties
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.table")
public class MybatisTableProperties {
    /**
     * <code>catalog</code>
     * {@link java.lang.String} <p>The <code>catalog</code> field.</p>
     * @see  java.lang.String
     */
    private String catalog;
    /**
     * <code>schema</code>
     * {@link java.lang.String} <p>The <code>schema</code> field.</p>
     * @see  java.lang.String
     */
    private String schema;
    /**
     * <code>updateLogic</code>
     * {@link java.lang.Boolean} <p>The <code>updateLogic</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean updateLogic = false;

    /**
     * <code>useLogic</code>
     * {@link java.lang.Boolean} <p>The <code>useLogic</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean useLogic = true;

    /**
     * <code>useOperate</code>
     * {@link java.lang.Boolean} <p>The <code>useOperate</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean useOperate = false;

    /**
     * <code>excludeUnused</code>
     * {@link java.lang.Boolean} <p>The <code>excludeUnused</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean excludeUnused = false;

    /**
     * <code>sqlScriptShow</code>
     * {@link java.lang.Boolean} <p>The <code>sqlScriptShow</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean sqlScriptShow = false;

    /**
     * <code>mysqlIgnoreInsert</code>
     * {@link java.lang.Boolean} <p>The <code>mysqlIgnoreInsert</code> field.</p>
     * @see  java.lang.Boolean
     */
    private Boolean mysqlIgnoreInsert = false;
    /**
     * <code>datetimeNow</code>
     * {@link java.lang.String} <p>The <code>datetimeNow</code> field.</p>
     * @see  java.lang.String
     */
    private String datetimeNow = ScriptConstants.NOW;
    /**
     * <code>databaseType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>The <code>databaseType</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.enums.DatabaseType
     */
    private DatabaseType databaseType = DatabaseType.POSTGRESQL;
    /**
     * <code>styleType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>The <code>styleType</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.enums.StyleType
     */
    private StyleType styleType = StyleType.LOWER_UNDERLINE;

    /**
     * <code>excludedType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.ExcludedType} <p>The <code>excludedType</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.enums.ExcludedType
     */
    private ExcludedType excludedType = ExcludedType.EXCLUDED;
    /**
     * <code>properties</code>
     * {@link java.util.Map} <p>The <code>properties</code> field.</p>
     * @see  java.util.Map
     */
    private Map<String, String> properties = new HashMap<>();
    /**
     * <code>excludes</code>
     * {@link java.lang.String} <p>The <code>excludes</code> field.</p>
     * @see  java.lang.String
     */
    private String[] excludes;
    /**
     * <code>ignores</code>
     * {@link java.lang.String} <p>The <code>ignores</code> field.</p>
     * @see  java.lang.String
     */
    private String[] ignores;

    /**
     * <code>selectIgnores</code>
     * {@link java.lang.String} <p>The <code>selectIgnores</code> field.</p>
     * @see  java.lang.String
     */
    private String[] selectIgnores;

    /**
     * <code>insertIgnores</code>
     * {@link java.lang.String} <p>The <code>insertIgnores</code> field.</p>
     * @see  java.lang.String
     */
    private String[] insertIgnores;

    /**
     * <code>updateIgnores</code>
     * {@link java.lang.String} <p>The <code>updateIgnores</code> field.</p>
     * @see  java.lang.String
     */
    private String[] updateIgnores;

    /**
     * <code>getExcludes</code>
     * <p>The get excludes getter method.</p>
     * @return  {@link java.util.List} <p>The get excludes return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getExcludes() {
        if (GeneralUtils.isNotEmpty(this.excludes)) {
            return new ArrayList<>(Arrays.asList(this.excludes));
        }
        return null;
    }

    /**
     * <code>getUnuseds</code>
     * <p>The get unuseds getter method.</p>
     * @return  {@link java.util.List} <p>The get unuseds return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getUnuseds() {
        List<String> unuseds = new ArrayList<>();
        if (!useLogic) {
            unuseds.add(EntityConstants.LOGIC);
        }
        if (!useOperate) {
            unuseds.add(EntityConstants.OPERATE);
        }
        return unuseds;
    }

    /**
     * <code>setExcludes</code>
     * <p>The set excludes setter method.</p>
     * @param excludes {@link java.lang.String} <p>The excludes parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setExcludes(String... excludes) {
        this.excludes = excludes;
    }

    /**
     * <code>getIgnores</code>
     * <p>The get ignores getter method.</p>
     * @return  {@link java.util.List} <p>The get ignores return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getIgnores() {
        if (GeneralUtils.isNotEmpty(this.ignores)) {
            return new ArrayList<>(Arrays.asList(this.ignores));
        }
        return null;
    }

    /**
     * <code>setIgnores</code>
     * <p>The set ignores setter method.</p>
     * @param ignores {@link java.lang.String} <p>The ignores parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setIgnores(String... ignores) {
        this.ignores = ignores;
    }

    /**
     * <code>getSelectIgnores</code>
     * <p>The get select ignores getter method.</p>
     * @return  {@link java.util.List} <p>The get select ignores return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getSelectIgnores() {
        if (GeneralUtils.isNotEmpty(this.selectIgnores)) {
            return new ArrayList<>(Arrays.asList(this.selectIgnores));
        }
        return null;
    }

    /**
     * <code>setSelectIgnores</code>
     * <p>The set select ignores setter method.</p>
     * @param selectIgnores {@link java.lang.String} <p>The select ignores parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setSelectIgnores(String... selectIgnores) {
        this.selectIgnores = selectIgnores;
    }

    /**
     * <code>getInsertIgnores</code>
     * <p>The get insert ignores getter method.</p>
     * @return  {@link java.util.List} <p>The get insert ignores return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getInsertIgnores() {
        if (GeneralUtils.isNotEmpty(this.insertIgnores)) {
            return new ArrayList<>(Arrays.asList(this.insertIgnores));
        }
        return null;
    }

    /**
     * <code>setInsertIgnores</code>
     * <p>The set insert ignores setter method.</p>
     * @param insertIgnores {@link java.lang.String} <p>The insert ignores parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setInsertIgnores(String... insertIgnores) {
        this.insertIgnores = insertIgnores;
    }

    /**
     * <code>getUpdateIgnores</code>
     * <p>The get update ignores getter method.</p>
     * @return  {@link java.util.List} <p>The get update ignores return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    public List<String> getUpdateIgnores() {
        if (GeneralUtils.isNotEmpty(this.updateIgnores)) {
            return new ArrayList<>(Arrays.asList(this.updateIgnores));
        }
        return null;
    }

    /**
     * <code>setUpdateIgnores</code>
     * <p>The set update ignores setter method.</p>
     * @param updateIgnores {@link java.lang.String} <p>The update ignores parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public void setUpdateIgnores(String... updateIgnores) {
        this.updateIgnores = updateIgnores;
    }
}
