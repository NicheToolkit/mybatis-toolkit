package io.github.nichetoolkit.mybatis.test.simple;

import io.github.nichetoolkit.rice.service.*;

/**
 * <code>SimpleService</code>
 * <p>The simple service interface.</p>
 * @see  io.github.nichetoolkit.rice.service.FilterService
 * @see  io.github.nichetoolkit.rice.service.SingleService
 * @see  io.github.nichetoolkit.rice.service.AlertLinkService
 * @see  io.github.nichetoolkit.rice.service.DeleteLinkService
 * @see  io.github.nichetoolkit.rice.service.RemoveLinkService
 * @see  io.github.nichetoolkit.rice.service.OperateLinkService
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface SimpleService extends FilterService<SimpleModel, SimpleFilter, String, String>, SingleService<SimpleModel, String, String>, QueryLinkService<SimpleModel,String,String>,
        AlertLinkService<String, String>, DeleteLinkService<String, String>, RemoveLinkService<String, String>, OperateLinkService<String, String> {
}
