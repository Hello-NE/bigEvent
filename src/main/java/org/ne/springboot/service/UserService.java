package org.ne.springboot.service;

import org.ne.springboot.pojo.User;

public interface UserService {



    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatar);

    void updatePwd(String newMd5);
}
