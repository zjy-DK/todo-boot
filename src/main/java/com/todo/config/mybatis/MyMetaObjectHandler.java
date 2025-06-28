package com.todo.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.todo.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * myBaits-Plus自动填充字段
 * @author zjy
 * @date 2025/06/03  10:31
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private UserUtils userUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createId", Integer.class, userUtils.getLoginUserId());
        this.strictInsertFill(metaObject, "createName", String.class, userUtils.getLoginUserName());
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateId", Integer.class, userUtils.getLoginUserId());
        this.strictInsertFill(metaObject, "updateName", String.class, userUtils.getLoginUserName());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }
}
