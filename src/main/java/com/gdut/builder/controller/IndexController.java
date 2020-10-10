package com.gdut.builder.controller;

import com.gdut.builder.model.Result;
import com.gdut.builder.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private GenerateService generateService;

    @PostMapping("/generate")
    public List<Result> generate(Integer questionNum, Integer maxLimit) {
        return generateService.generateList(questionNum,maxLimit);
    }

}
