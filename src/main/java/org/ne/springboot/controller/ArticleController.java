package org.ne.springboot.controller;

import org.ne.springboot.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ArticleController {

    @GetMapping("/list")
    public Result list() {
        return Result.success("123");
    }
}
