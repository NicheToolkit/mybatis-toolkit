package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.fickle.FickleEntryFilter;
import io.github.nichetoolkit.mybatis.test.fickle.FickleEntryModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.QueryFickleService;
import io.github.nichetoolkit.rice.service.SingleService;

/**
 * <code>FickleEntryService</code>
 * <p>The fickle entry service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @see io.github.nichetoolkit.rice.service.QueryFickleService
 * @since Jdk1.8
 */
public interface FickleEntryService extends FilterService<FickleEntryModel, FickleEntryFilter, String, String>,
        SingleService<FickleEntryModel, String, String>,
        QueryFickleService<FickleEntryModel, String, String> {
}
