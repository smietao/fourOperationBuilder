package com.gdut.builder.service.impl;


import com.gdut.builder.model.Fraction;
import com.gdut.builder.service.GenerateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateServiceImpl implements GenerateService {

    private static FractionServiceImpl fractionService = new FractionServiceImpl();

    /*
     * 生成算式，以及结果 limit表示生成每一个分式的最大值
     */
    public String generateFormula(int limit) {

        String expression = "";
        Random r = new Random();

        char[] c = {'+', '-', '*', '÷'};
        // 生成运算符的数量，题目要求不超过3个。这里范围为1~3
        int operaCount = r.nextInt(3) + 1;
        // 分数的个数，根据运算符个数来生成。例如1个运算符最多有两个分数
        int fractionCount = r.nextInt(operaCount + 1) + 1;
        // 非分数的个数 = 运算符个数 + 1 - 分数的个数
        int unFractionCount = operaCount + 1 - fractionCount;
        // 存放分数的List集合
        List<Fraction> fractionList = new ArrayList<>();
        // 存放符号的List集合
        List<String> symbolList = new ArrayList<>();
        // 存放运算数和运算符
        List FraSymList = new ArrayList();
        for (int i = 0; i < operaCount; i++) {
            // 遍历运算符个数，一个运算符生成一个数
            symbolList.add(String.valueOf(c[r.nextInt(4)]));
        }
        for (int i = 0; i < fractionCount; i++) {
            // 遍历分数的个数
            fractionList.add(new Fraction(true, limit));
        }
        // 剩下的就是非分数
        if (unFractionCount >= 0) {
            for (int i = 0; i < unFractionCount; i++) {
                fractionList.add(new Fraction(false, limit));
            }
        }

        // 运算数的个数 = 符号个数 + 1 即 fractionList.size() = symbolList.size() + 1
        // 因此取其一遍历即可
        int j = 0;
        for (int i = 0; i < symbolList.size(); i++) {
            Fraction fraction = fractionList.get(i);
            String symbol = symbolList.get(i);
            expression = expression + " " + fraction.toString() + " " + symbol;
            FraSymList.add(fraction);
            FraSymList.add(symbol);
            j++;
        }
        // 拼接最后一个运算数
        Fraction lastFraction = fractionList.get(j);
        expression = expression + " " + lastFraction;
        FraSymList.add(lastFraction);

        // 将除号转换为计算用的/
        for (int i = 0; i < FraSymList.size(); i++) {
            if (i % 2 != 0) {
                if (FraSymList.get(i).equals("÷")) {
                    FraSymList.set(i, "/");
                }
            }
        }
        if (!fractionService.isRule(FraSymList)) {
            // 不符合规则，返回null
            return null;
        }
        return expression;


    }

}
