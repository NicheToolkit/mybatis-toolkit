package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.load.LoadLink1Model;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>LoadLink1Service</code>
 * <p>The load link 1 service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @see io.github.nichetoolkit.rice.service.QueryLinkService
 * @see io.github.nichetoolkit.rice.service.AlertLinkService
 * @see io.github.nichetoolkit.rice.service.DeleteLinkService
 * @see io.github.nichetoolkit.rice.service.RemoveLinkService
 * @see io.github.nichetoolkit.rice.service.OperateLinkService
 * @since Jdk17
 */
public interface LoadLink1Service extends FilterService<LoadLink1Model, RestFilter, String, String>, SingleService<LoadLink1Model, String, String>, QueryLinkService<LoadLink1Model,String,String>,
        AlertLinkService<String, String>, DeleteLinkService<String, String>, RemoveLinkService<String, String>, OperateLinkService<String, String> {
}
