package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultAlertnessHandler;

import java.io.Serializable;
import java.util.Collection;

/**
 * <code>MybatisAlertnessHandler</code>
 * <p>The mybatis alertness handler class.</p>
 * @see  io.github.nichetoolkit.rice.DefaultAlertnessHandler
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisAlertnessHandler extends DefaultAlertnessHandler<Serializable> {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisAlertnessHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     */
    public static final MybatisAlertnessHandler DEFAULT_HANDLER = new MybatisAlertnessHandler();

    @Override
    public String handle(String prefix, Collection<Serializable> statusList, Class<Serializable> alertnessType) throws RestException {
        StyleType defaultStyleType = MybatisSqlProviderHolder.defaultStyleType();
        return MybatisSqlUtils.whereSqlOfTypes(prefix, statusList, alertnessType,defaultStyleType);
    }
}
