package org.ne.springboot.controller;


import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Param;
import org.ne.springboot.pojo.Result;
import org.ne.springboot.pojo.User;
import org.ne.springboot.service.UserService;
import org.ne.springboot.utils.MD5Utils;
import org.ne.springboot.utils.ThreadLocalUtils;
import org.ne.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> para) {
        String username = para.get("username").toString();
        String password = para.get("password").toString();
        User u = userService.findByUserName(username);
        if (u == null) {


            userService.register(username, password);
            return Result.success();

        } else {
            return Result.error("user already exist");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> para) {
        String username = para.get("username").toString();
        String password = para.get("password").toString();
        String md5 = MD5Utils.code(password);
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("user not found");
        }

        if (password.isEmpty()) {
            return Result.error("password is empty");
        }

        if (md5 != null && !md5.equals(user.getPassword())) {
            return Result.error("password error");
        }
        String token = TokenUtils.sign(user);
        Map<String, Object> info = new HashMap<>();
        info.put("user", user);
        info.put("token", token);


        return Result.success(info);
    }


//
//    @GetMapping("/detail")
//    public Result detail(@RequestHeader(name = "token") String token) {
//
//        Map<String, Object> info = TokenUtils.parseToken(token);
//        String username = (String) info.get("username");
//
//        User user = userService.findByUserName(username);
//        System.out.println(user);
//        if (user == null) {
//            return Result.error("user not found");
//        }
////        user.setPassword("forbid to acquire the password");
//        return Result.success(user);
//    }


    @GetMapping("/detail")
    public Result detail() {

        Map<String, Object> info = ThreadLocalUtils.get();
        System.out.println(info);
        String username = info.get("username").toString();

        User user = userService.findByUserName(username);
        System.out.println(user);
        if (user == null) {
            return Result.error("user not found");
        }
        return Result.success(user);
    }


    @PostMapping("/update")
    public Result update(@RequestBody @Validated User user) {

        if (user == null) {
            return Result.error("user not found");
        }
        Map<String, Object> info = ThreadLocalUtils.get();
        if (user.getId() != Integer.parseInt((String) info.get("userId"))) {
            return Result.error("user id mismatch");
        }
        userService.update(user);

        return Result.success(userService.findByUserName(user.getUsername()));
    }


    @PostMapping("avatarUpdate")
    public Result avatarUpdate(@RequestParam("userPic") String userPic) {
        userService.updateAvatar(userPic);
        return Result.success();
    }

    @PostMapping("pwdUpdate")
    public Result pwdUpdate(@RequestBody @Validated Map<String, Object> para) {
        String oldPassword = para.get("oldPassword").toString();
        String newPassword = para.get("newPassword").toString();
        String rePassword = para.get("rePassword").toString();
        if (oldPassword == null || newPassword == null || rePassword == null) {
            return Result.error("password format error");
        }

        if (oldPassword.equals(newPassword)) {
            return Result.error("the old password is the same with the new password");
        }

        if (!newPassword.equals(rePassword)) {
            return Result.error("password mismatched");
        }

        String oldMd5 = MD5Utils.code(oldPassword);
        String newMd5 = MD5Utils.code(newPassword);

        Map<String, Object> info = ThreadLocalUtils.get();
        String username = info.get("username").toString();
        User logging_user = userService.findByUserName(username);
        if (!logging_user.getPassword().equals(oldMd5)) {
            return Result.error("the old password mismatch");
        }

        userService.updatePwd(newMd5);

        return Result.success();
    }
}
