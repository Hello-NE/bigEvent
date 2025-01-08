package org.ne.springboot.controller;

import org.ne.springboot.pojo.Category;
import org.ne.springboot.pojo.Result;
import org.ne.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author en
 * @date 2025/1/1 下午6:11 1月
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {



    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success(category);
    }

//    @GetMapping
//    public Result list(@RequestParam int page, @RequestParam int size) {
//
//    }
    @GetMapping
    public Result findAll() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }


    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
          Category c = categoryService.findById(id);

          return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class)  Category category) {
        categoryService.update(category);
        return Result.success(detail(category.getId()));
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }
}
