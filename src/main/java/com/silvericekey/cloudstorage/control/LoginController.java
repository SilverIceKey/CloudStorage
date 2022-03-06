package com.silvericekey.cloudstorage.control;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.dao.mapper.UserMapper;
import com.silvericekey.cloudstorage.entity.User;
import com.silvericekey.cloudstorage.model.RegisterVo;
import com.silvericekey.cloudstorage.model.RestReponse;
import com.silvericekey.cloudstorage.model.UserVo;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SilverIceKey
 * 用户相关
 */
@Slf4j
@RestController()
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param userVo
     * @return
     */
    @PostMapping(path = "/login")
    public RestReponse login(@RequestBody UserVo userVo) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", userVo.getUsername());
        User user = userMapper.selectOne(userWrapper);
        if (user==null){
            return RestUtil.error("账号不存在");
        }else if (!user.getPassword().equals(MD5.create().digestHex(userVo.getPassword()))){
            return RestUtil.error("账号或密码错误");
        }else {
            return RestUtil.ok();
        }
    }

    @PostMapping(path = "/register")
    public RestReponse register(@RequestBody RegisterVo registerVo) {
        User user = new User();
        user.setUsername(registerVo.getUsername());
        user.setPassword(MD5.create().digestHex(registerVo.getPassword()));
        user.setPhone(registerVo.getPhone());
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", registerVo.getUsername());
        boolean isInsert = userMapper.selectOne(userWrapper)!=null;
        if (!isInsert) {
            userMapper.insert(user);
            return RestUtil.ok();
        } else {
            return RestUtil.error("注册失败,该账号已存在");
        }
    }
}
