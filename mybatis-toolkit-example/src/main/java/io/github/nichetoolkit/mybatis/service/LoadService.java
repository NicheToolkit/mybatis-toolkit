package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.load.LoadFilter;
import io.github.nichetoolkit.mybatis.test.load.LoadIdentity;
import io.github.nichetoolkit.mybatis.test.load.LoadModel;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>LoadService</code>
 * <p>The load service interface.</p>
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
public interface LoadService extends FilterService<LoadModel, LoadFilter, LoadIdentity, String>, SingleService<LoadModel, LoadIdentity, String>, QueryLinkService<LoadModel,LoadIdentity,String>,
        AlertLinkService<LoadIdentity, String>, DeleteLinkService<LoadIdentity, String>, RemoveLinkService<LoadIdentity, String>, OperateLinkService<LoadIdentity, String> {
}
