package io.github.nichetoolkit.mybatis.controller;

import io.github.nichetoolkit.mybatis.service.TemplateService;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <code>TemplateController</code>
 * <p>The template controller class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see org.springframework.web.bind.annotation.CrossOrigin
 * @see io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @since Jdk1.8
 */
@RestSkip
@CrossOrigin
@RestNotelog
@RestController
@RequestMapping("/template")
public class TemplateController {

    /**
     * <code>templateService</code>
     * {@link io.github.nichetoolkit.mybatis.service.TemplateService} <p>The <code>templateService</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.service.TemplateService
     */
    private final TemplateService templateService;

    /**
     * <code>TemplateController</code>
     * <p>Instantiates a new template controller.</p>
     * @param templateService {@link io.github.nichetoolkit.mybatis.service.TemplateService} <p>The template service parameter is <code>TemplateService</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.service.TemplateService
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * <code>create</code>
     * <p>The create method.</p>
     * @param templateModel {@link io.github.nichetoolkit.mybatis.simple.TemplateModel} <p>The template model parameter is <code>TemplateModel</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The create return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateModel
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/create")
    public RestResult<TemplateModel> create(@RequestBody TemplateModel templateModel) throws RestException {
        return RestResult.success(templateService.create(templateModel));
    }

    /**
     * <code>update</code>
     * <p>The update method.</p>
     * @param templateModel {@link io.github.nichetoolkit.mybatis.simple.TemplateModel} <p>The template model parameter is <code>TemplateModel</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The update return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateModel
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/update")
    public RestResult<TemplateModel> update(@RequestBody TemplateModel templateModel) throws RestException {
        return RestResult.success(templateService.update(templateModel));
    }

    /**
     * <code>queryById</code>
     * <p>The query by id method.</p>
     * @param id       {@link io.github.nichetoolkit.mybatis.simple.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @param tablekey {@link java.lang.String} <p>The tablekey parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The query by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateIdentity
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @GetMapping("/query/{tablekey}")
    public RestResult<TemplateModel> queryById(TemplateIdentity id, @PathVariable("tablekey") String tablekey) throws RestException {
        TemplateModel templateModel = templateService.queryById(tablekey, id);
        return RestResult.success(templateModel);
    }

    /**
     * <code>queryByFilter</code>
     * <p>The query by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.TemplateFilter} <p>The filter parameter is <code>TemplateFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The query by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/query/filter")
    public RestResult<RestPage<TemplateModel>> queryByFilter(@RequestBody TemplateFilter filter) throws RestException {
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @param id       {@link io.github.nichetoolkit.mybatis.simple.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @param tablekey {@link java.lang.String} <p>The tablekey parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The delete by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateIdentity
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.DeleteMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @DeleteMapping("/delete/{tablekey}")
    public RestResult<?> deleteById(TemplateIdentity id, @PathVariable("tablekey") String tablekey) throws RestException {
        templateService.deleteById(tablekey, id);
        return RestResult.success();
    }

    /**
     * <code>deleteByFilter</code>
     * <p>The delete by filter method.</p>
     * @param filter {@link io.github.nichetoolkit.mybatis.simple.TemplateFilter} <p>The filter parameter is <code>TemplateFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The delete by filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/delete/filter")
    public RestResult<?> deleteByFilter(@RequestBody TemplateFilter filter) throws RestException {
        templateService.deleteAllWithFilter(filter);
        return RestResult.success();
    }
}
