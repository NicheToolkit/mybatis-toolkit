package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultIdentityHandler;

import java.io.Serializable;
import java.util.Collection;

/**
 * <code>MybatisIdentityHandler</code>
 * <p>The mybatis identity handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultIdentityHandler
 * @since Jdk17
 */
public class MybatisIdentityHandler extends DefaultIdentityHandler<Serializable> {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisIdentityHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     */
    public static final MybatisIdentityHandler DEFAULT_HANDLER = new MybatisIdentityHandler();

    @Override
    public String handle(String prefix, Collection<Serializable> idList, Class<Serializable> identityType) throws RestException {
        StyleType defaultStyleType = MybatisSqlProviderHolder.defaultStyleType();
        return MybatisSqlUtils.whereSqlOfTypes(prefix,idList,identityType,defaultStyleType);
    }
}
