package io.github.nichetoolkit.mybatis.controller;

import io.github.nichetoolkit.mybatis.service.TemplateService;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestSkip
@CrossOrigin
@RestNotelog
@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/create")
    public RestResult<TemplateModel> create(@RequestBody TemplateModel templateModel) throws RestException {
        return RestResult.success(templateService.create(templateModel));
    }

    @PostMapping("/update")
    public RestResult<TemplateModel> update(@RequestBody TemplateModel templateModel) throws RestException {
        return RestResult.success(templateService.update(templateModel));
    }

    @GetMapping("/query/{tablekey}")
    public RestResult<TemplateModel> queryById(TemplateKey id, @PathVariable("tablekey") String tablekey) throws RestException {
        TemplateModel templateModel = templateService.queryById(tablekey, id);
        return RestResult.success(templateModel);
    }

    @PostMapping("/query/filter")
    public RestResult<RestPage<TemplateModel>> queryByFilter(@RequestBody TemplateFilter filter) throws RestException {
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }

    @DeleteMapping("/delete/{tablekey}")
    public RestResult<?> deleteById(TemplateKey id, @PathVariable("tablekey") String tablekey) throws RestException {
        templateService.deleteById(tablekey, id);
        return RestResult.success();
    }

    @PostMapping("/delete/filter")
    public RestResult<?> deleteByFilter(@RequestBody TemplateFilter filter) throws RestException {
        templateService.deleteAllWithFilter(filter);
        return RestResult.success();
    }
}
