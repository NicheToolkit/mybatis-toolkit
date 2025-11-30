package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.common.CommonFilter;
import io.github.nichetoolkit.mybatis.test.common.CommonModel;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>CommonService</code>
 * <p>The common service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @since Jdk17
 */
public interface CommonService extends FilterService<CommonModel, CommonFilter, String, String>, SingleService<CommonModel, String, String> {
}
