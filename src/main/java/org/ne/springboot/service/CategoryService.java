package org.ne.springboot.service;

import org.ne.springboot.pojo.Category;

import java.util.List;

/**
 * @author en
 * @date 2025/1/1 下午6:13 1月
 **/
public interface CategoryService {

    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
