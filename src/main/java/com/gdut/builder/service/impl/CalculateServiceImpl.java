package com.gdut.builder.service.impl;

import com.gdut.builder.model.Fraction;
import com.gdut.builder.service.CalculateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分数的计算类
 * 返回一个分数包装类
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Fraction calculate(List FraSymList) {
        int mulDivIndex = mulDivExist(FraSymList);
        if (mulDivIndex != -1) {
            // 有乘除
            String expression = mulDivCal(FraSymList, mulDivIndex);
            if (expression.equals("error")) {
                return null;
            }
        } else {
            String expression = AddSubCal(FraSymList);
            if (expression.equals("error")) {
                return null;
            }
        }
        if (FraSymList.size() == 1) {
            // 只剩下一个，就是结果了
            return (Fraction) FraSymList.get(0);
        }
        return calculate(FraSymList);
    }

    /*
     * 判断分式里面是否有乘除
     * 有乘除返回乘除的位置，没乘除返回-1
     */
    public int mulDivExist(List list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("*") || list.get(i).equals("/")) {
                return i;
            }
        }
        return -1;
    }

    // 计算分式的乘除，计算结果往前放
    public String mulDivCal(List l, int mulDivIndex) {
        // 删除乘或除号
        String operation = (String) l.remove(mulDivIndex);
        // 乘除号前一个运算数
        Fraction beforeFraction = (Fraction) l.get(mulDivIndex - 1);
        // 乘除号后一个运算数
        Fraction afterFraction = (Fraction) l.get(mulDivIndex);
        // 移除后一位数
        l.remove(mulDivIndex);
        // 如果是除法
        if (operation.equals("*")) {
            Fraction result = beforeFraction.muti(afterFraction);
            // 将前一个运算数覆盖成新的结果
            l.set(mulDivIndex - 1, result);
            if (result.isPositive() == 0) {
                // 不是正数
                return "error";
            }
        }
        if (operation.equals("/")) {
            Fraction result = beforeFraction.div(afterFraction);
            // 将前一个运算数覆盖成新的结果
            l.set(mulDivIndex - 1, result);
            if (result.isPositive() == 0) {
                return "error";
            }
        }
        return "right";
    }

    // 计算分式的加减，计算结果往前放
    public String AddSubCal(List list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("+")) {
                Fraction first = (Fraction) list.get(i - 1);
                list.remove(i);
                Fraction last = (Fraction) list.get(i);
                list.remove(i);
                Fraction result = first.add(last);
                list.set(i - 1, result);
                i--;
                if (result.isPositive() == 0) {
                    return "error";
                }
            }
            if (list.get(i).equals("-")) {
                Fraction first = (Fraction) list.get(i - 1);
                list.remove(i);
                Fraction last = (Fraction) list.get(i);
                list.remove(i);
                Fraction result = first.sub(last);
                list.set(i - 1, result);
                i--;
                if (result.isPositive() == 0) {
                    return "error";
                }
            }
        }
        return "right";
    }
}
