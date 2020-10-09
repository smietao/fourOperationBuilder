package com.gdut.builder.service;

import com.gdut.builder.model.Fraction;
import java.util.List;

public class FractionService {

    /*
     * 判断除数是否为0，判断分数是否为0
     * 不合规则返回false
     */
    public boolean isRule(List list) {

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                // 整除2，说明是运算数（分数）
                Fraction fraction = (Fraction) list.get(i);
                if (fraction.getDenominator() == 0) {
                    // 分母为0
                    return false;
                }
            } else {
                // 是运算符
                String flag = (String) list.get(i);
                if ("/".equals(flag)) {
                    // 是除号的话，取下一个运算数，判断是否为0
                    Fraction fraction = (Fraction) list.get(i + 1);
                    if (fraction.getDenominator() == 0 || fraction.getNominator() == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
