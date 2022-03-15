package ${package.Controller};

import ${package.Entity}.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.*;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
        <#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import com.lk.cf.util.*;
import com.lk.cf.framework.common.consts.*;
import com.alibaba.fastjson.JSONObject;
import javax.validation.Valid;
import ${package.Service}.${table.serviceName};

/**
 * <p>
 * ${table.comment!} controller
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Api(tags = "${table.comment!}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${table.serviceName?uncap_first};


    @ApiOperation("分页列表")
    @GetMapping("/listPage")
    public PageVo listPage(PageSel sel) {
        return ${table.serviceName?uncap_first}.listPageObjs(sel);
    }

    @ApiOperation("单条数据")
    @GetMapping("/get/{id}")
    public Rest getOne(@PathVariable String id) {
        return RestUtil.ok(${table.serviceName?uncap_first}.getById(id));
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public Rest add(@Valid ${entity}VoAdd objAdd) {
        ${entity} obj = JSONObject.parseObject(JSONObject.toJSONString(objAdd), ${entity}.class);
        obj.setId(null);
        return ${table.serviceName?uncap_first}.addObj(obj);
    }

    @ApiOperation("删除")
    @GetMapping("/delete")
    public Rest delete(String[] ids) {
        if (null == ids)
            return RestUtil.error("至少选择一条记录");

        for (String id : ids) {
            ${table.serviceName?uncap_first}.removeById(id);
        }
        return RestUtil.ok("成功删除 [" + ids.length + "] 个记录");
    }

    @ApiOperation("编辑")
    @PostMapping("/edit")
    public Rest edit(@Valid ${entity}VoAdd objAdd) {
        ${entity} obj = JSONObject.parseObject(JSONObject.toJSONString(objAdd), ${entity}.class);
        return ${table.serviceName?uncap_first}.updObj(obj);
    }

}
</#if>
