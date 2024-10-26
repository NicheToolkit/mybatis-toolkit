package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.OperateLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import java.util.Collection;

/**
 * <code>MybatisOperateLinkMapper</code>
 * <p>The mybatis operate link mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.OperateLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisOperateMapper
 * @since Jdk1.8
 */
public interface MybatisOperateLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, OperateLinkMapper<L, I>, MybatisOperateMapper<E, I> {

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateByLinkId(@Param("linkId") L linkId, @Param("operate") Integer operate);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("operate") Integer operate);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate);
}
