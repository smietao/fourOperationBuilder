package com.gdut.builder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @PostMapping("/generate")
    public void generate(int questionNum,int maxLimit) {
        System.out.println("设定的题目数量为" + questionNum);
        System.out.println("题目中数值最大值为" + maxLimit);
    }
}
