package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.lk.cms.common.entity.Message;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.util.StrUtil;
import java.util.*;
import java.io.*;
import com.baomidou.mybatisplus.core.metadata.IPage;


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
    public Message add${entity}(${entity} ${entity?uncap_first}) {

        boolean b = this.save(${entity?uncap_first});
        return b ? Message.ok() : Message.addError();
    }


    @Override
    public Message delete${entity}(String id) {

        boolean b = this.removeById(id);
        return b ? Message.ok() : Message.delError();
    }


    @Override
    public Message update${entity}(${entity} ${entity?uncap_first}) {

        boolean b = this.updateById(${entity?uncap_first});
        return b ? Message.ok() : Message.updError();
    }

    @Override
    public Message getById(String id) {
        QueryWrapper<${entity}> wrapper = new QueryWrapper<${entity}>()
                .eq("id", id)
                .last("limit 1");

        ${entity} ${entity?uncap_first} = this.getOne(wrapper);

        return ${entity?uncap_first} != null ? Message.ok().addData("${entity?uncap_first}", ${entity?uncap_first}) : Message.selError();
    }


    @Override
    public Message get${entity}List(Page page, String keywords) {

        QueryWrapper<${entity}> wrapper = new QueryWrapper<${entity}>()
                .like("name", keywords);

        List<${entity}> ${entity?uncap_first}List;
        if (page == null) {
            ${entity?uncap_first}List = this.getBaseMapper().selectList(wrapper);
        }else{
            IPage iPage = this.getBaseMapper().selectPage(page, query());
            ${entity?uncap_first}List = iPage.getRecords();
        }

        return Message.ok().addData("${entity?uncap_first}List", ${entity?uncap_first}List);
    }

    @Override
    public Message checkNameIsExist(String name, Serializable id) {

        ${entity} old = null;
        if (id != null) {
            old = this.getById(id);
            if (old == null)
                return Message.selError();
        }

        QueryWrapper<${entity}> wrapper = new QueryWrapper<${entity}>()
            .eq("name", name);

        List<${entity}> ${entity?uncap_first}List = this.getBaseMapper().selectList(wrapper);
        if (${entity?uncap_first}List.size() > 0) {
            if (old == null)
                return Message.error("该名称已存在");
            if (old != null && !old.getName().equals(name))
                return Message.error("该名称已存在");
        }
        return Message.ok();
    }

}
</#if>
