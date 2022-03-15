package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lk.cms.common.entity.Message;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
        <#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
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
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
@RequiresPermissions("<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${table.serviceName?uncap_first};

    /**
     * 新增接口
     */
    @PostMapping("/add")
    public Message add(${entity} ${entity?uncap_first}) {

        return ${table.serviceName?uncap_first}.add${entity}(${entity?uncap_first});
    }

    /**
     * 删除接口
     */
    @DeleteMapping("/delete/{id}")
    public Message delete(@PathVariable String id) {

        return ${table.serviceName?uncap_first}.delete${entity}(id);
    }

    /**
     * 修改接口
     */
    @PutMapping("/update")
    public Message update(${entity} ${entity?uncap_first}) {

        return ${table.serviceName?uncap_first}.update${entity}(${entity?uncap_first});
    }

    /**
     * 查询单数据接口
     */
    @GetMapping("/get/{id}")
    public Message get(@PathVariable String id) {

        return ${table.serviceName?uncap_first}.getById(id);
    }

    /**
     * 查询列表接口
     */
    @GetMapping("/list")
    public Message list(@RequestParam(required = false, defaultValue = "") String keywords) {

        return ${table.serviceName?uncap_first}.get${entity}List(null, keywords);
    }

    /**
     * 分页查询列表接口
     */
    @GetMapping("/listPage")
    public Message list(@RequestParam(required = false, defaultValue = "") String keywords,
                        Page page) {

        return ${table.serviceName?uncap_first}.get${entity}List(page, keywords);
    }



}
</#if>
