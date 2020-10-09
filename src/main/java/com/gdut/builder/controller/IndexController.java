package com.gdut.builder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @PostMapping("/generate")
    @ResponseBody
    public String generate(Integer questionNum,Integer maxLimit) {
        System.out.println("设定的题目数量为" + questionNum);
        System.out.println("题目中数值最大值为" + maxLimit);
        return "操作成功";
    }
}
