package org.ne.springboot.service.impl;

import org.ne.springboot.mapper.CategoryMapper;
import org.ne.springboot.pojo.Category;
import org.ne.springboot.service.CategoryService;
import org.ne.springboot.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author en
 * @date 2025/1/1 下午6:14 1月
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {


        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String, Object> info = ThreadLocalUtils.get();
        int userId = Integer.parseInt((String) info.get("userId"));
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> info = ThreadLocalUtils.get();
        int userId = Integer.parseInt((String) info.get("userId"));
        List<Category> list = categoryMapper.list(userId);
        return list;
    }

    @Override
    public Category findById(Integer id) {
        Map<String, Object> info = ThreadLocalUtils.get();
        int userId = Integer.parseInt((String) info.get("userId"));
        return categoryMapper.findById(id, userId);

    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> info = ThreadLocalUtils.get();
        int userId = Integer.parseInt((String) info.get("userId"));
        categoryMapper.update(category);


    }
}
