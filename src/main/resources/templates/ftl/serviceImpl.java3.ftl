package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.util.StrUtil;
import java.util.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.util.StrUtil;
import com.myq.core.framework.common.entity.*;
import com.myq.core.utils.RestUtil;
import com.myq.core.utils.IsExistUtil;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public PageRest listPageObjs(PageListSel PageListSel) {
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>()
        ;
 <#list table.fields as field>
  <#if field.propertyName == "name">
        if (!StrUtil.isEmptyOrUndefined(PageListSel.getKeywords())){
            wrapper.like("name", PageListSel.getKeywords());
        }
  </#if>
  <#if field.propertyName == "update_time">
        wrapper.orderByDesc("update_time");
  </#if>
 </#list>

        IPage<${entity}> iPage = new Page<>(PageListSel.getCurrent(), PageListSel.getSize());
        IPage<${entity}> page = this.page(iPage, wrapper);
        return RestUtil.pageRest(page);
    }

    @Override
    public Rest addObj(${entity} obj) {
 <#list table.fields as field>
  <#if field.propertyName == "name">
        Rest rest = IsExistUtil.checkNameValueIsExist(this, "name", obj.get${field.capitalName}(), null);
        if (rest.getErrcode() != 0){
            return rest;
        }
  </#if>
 </#list>

        obj.setId(null);
        this.save(obj);
        return RestUtil.ok("新增成功", obj.getId());
    }

    @Override
    public Rest updObj(${entity} obj) {

 <#list table.fields as field>
  <#if field.propertyName == "name">
        Rest rest = IsExistUtil.checkNameValueIsExist(this, "name", obj.get${field.capitalName}(), obj.getId());
        if (rest.getErrcode() != 0){
            return rest;
        }
  </#if>
 </#list>

        this.updateById(obj);
        return RestUtil.ok("修改成功");
    }
}
</#if>
