package com.silvericekey.cloudstorage.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author SilverIceKey
 * @title: MyMetaObjectHandler
 * @date 2021/12/2816:41
 */
@Slf4j
@Component
public class MysqlMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 测试下划线
        Object createDatetime = this.getFieldValByName("createTime", metaObject);
        if (createDatetime == null) {
            //测试实体没有的字段，配置在公共填充，不应该set到实体里面
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now())
                    .strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //测试实体没有的字段，配置在公共填充，不应该set到实体里面
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
