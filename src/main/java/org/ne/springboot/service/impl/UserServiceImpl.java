package org.ne.springboot.service.impl;

import org.ne.springboot.mapper.UserMapper;
import org.ne.springboot.pojo.User;
import org.ne.springboot.service.UserService;
import org.ne.springboot.utils.MD5Utils;
import org.ne.springboot.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        String md5 = MD5Utils.code(password);

        userMapper.add(username, md5);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatar) {
        Map<String,Object> info = ThreadLocalUtils.get();
        int id = Integer.parseInt(info.get("userId").toString());
        userMapper.updateAvatar(avatar, id);
    }

    @Override
    public void updatePwd(String newMd5) {
        Map<String,Object> info = ThreadLocalUtils.get();
        int id = Integer.parseInt(info.get("userId").toString());
        userMapper.updatePwd(newMd5, id);
    }


}
