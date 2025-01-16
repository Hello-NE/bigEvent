package org.ne.springboot.controller;

import org.ne.springboot.pojo.Article;
import org.ne.springboot.pojo.PageBean;
import org.ne.springboot.pojo.Result;
import org.ne.springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
//
//    @GetMapping("/list")
//    public Result list() {
//        return Result.success("123");
//    }

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated   Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result list(Integer pageNum, Integer pageSize,
                       @RequestParam(required = false) String categoryId,
                       @RequestParam(required = false) String state) {
         PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
         return Result.success(pageBean);
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated  Article article) {
        articleService.update(article);
        return Result.success(articleService.findById(article.getId()));
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Article article;
        article = articleService.findById(id);
        return Result.success(article);
    }



}
