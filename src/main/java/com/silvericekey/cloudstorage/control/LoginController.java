package com.silvericekey.cloudstorage.control;

import com.silvericekey.cloudstorage.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SilverIceKey
 */
@Slf4j
@RestController()
@RequestMapping("/user")
public class LoginController {
    @PostMapping(path = "/Login")
    public String login(@RequestBody UserVo userVo) {
        log.info(userVo.toString());
        return userVo.toString();
    }
}
