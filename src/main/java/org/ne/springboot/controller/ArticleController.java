package org.ne.springboot.controller;

import org.ne.springboot.pojo.Article;
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




}
