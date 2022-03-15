package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lk.cms.common.entity.Message;
import java.util.*;
import java.io.*;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    Message add${entity}(${entity} ${entity?uncap_first});

    Message delete${entity}(String id);

    Message update${entity}(${entity} ${entity?uncap_first});

    Message getById(String id);

    Message get${entity}List(Page page, String keywords);

    Message checkNameIsExist(String name, Serializable id);

}
</#if>
