package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.CommonFilter;
import io.github.nichetoolkit.mybatis.simple.CommonModel;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>CommonService</code>
 * <p>The common service interface.</p>
 * @see  io.github.nichetoolkit.rice.service.FilterService
 * @see  io.github.nichetoolkit.rice.service.SingleService
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface CommonService extends FilterService<CommonModel, CommonFilter, String, String>, SingleService<CommonModel, String, String> {
}
