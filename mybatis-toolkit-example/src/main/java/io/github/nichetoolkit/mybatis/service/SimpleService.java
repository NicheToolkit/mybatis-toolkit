package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.OperateService;
import io.github.nichetoolkit.rice.service.RemoveLinkService;
import io.github.nichetoolkit.rice.service.SingleService;

public interface SimpleService extends FilterService<SimpleModel, SimpleFilter, String, String>, SingleService<SimpleModel, String, String>, RemoveLinkService<String, String>, OperateService<String, String> {
}
