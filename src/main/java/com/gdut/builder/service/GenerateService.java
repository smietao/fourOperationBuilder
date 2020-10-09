package com.gdut.builder.service;


public interface GenerateService {

    /**
     * 生成算式
     * @param limit 生成每一个算式的最大值
     * @return 算式表达式
     */
    String generateFormula(int limit);
}
