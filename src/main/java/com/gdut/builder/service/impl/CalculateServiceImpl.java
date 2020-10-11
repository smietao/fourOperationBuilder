package com.gdut.builder.service.impl;

import com.gdut.builder.model.Fraction;
import com.gdut.builder.service.CalculateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 分数的计算类
 * 返回一个分数包装类
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Fraction calculate(List FraSymList) {
        Stack<Fraction> tempFrac = new Stack<Fraction>();
        for (int i = 0; i < FraSymList.size(); i++) {
            if (FraSymList.get(i) instanceof Fraction) { // 先将数字存入栈中
                tempFrac.push((Fraction) FraSymList.get(i));
            } else { // 遇到运算符，从栈中取出两个数字进行运算
                if (FraSymList.get(i).equals("+")) {
                    Fraction result = tempFrac.pop().add(tempFrac.pop());
                    tempFrac.push(result);
                } else if (FraSymList.get(i).equals("-")) {
                    Fraction result = tempFrac.pop().sub(tempFrac.pop());
                    tempFrac.push(result);
                } else if (FraSymList.get(i).equals("*")) {
                    Fraction result = tempFrac.pop().muti(tempFrac.pop());
                    tempFrac.push(result);
                } else {
                    Fraction result = tempFrac.pop().div(tempFrac.pop());
                    tempFrac.push(result);
                }
            }
        }
//        int mulDivIndex = mulDivExist(FraSymList);
//        if (mulDivIndex != -1) {
//            // 有乘除
//            String expression = mulDivCal(FraSymList, mulDivIndex);
//            if (expression.equals("error")) {
//                return null;
//            }
//        } else {
//            String expression = AddSubCal(FraSymList);
//            if (expression.equals("error")) {
//                return null;
//            }
//        }
//        if (FraSymList.size() == 1) {
//            // 只剩下一个，就是结果了
//            return (Fraction) FraSymList.get(0);
//        }
//        return calculate(FraSymList)
        return tempFrac.pop();
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
            if (list.get(i).equals("+") || list.get(i).equals("-")) {
                String operation = (String) list.get(i);
                Fraction beforeFraction = (Fraction) list.get(i - 1);
                list.remove(i);
                Fraction afterFraction = (Fraction) list.get(i);
                list.remove(i);
                Fraction result;
                if (operation.equals("+")) {
                    result = beforeFraction.add(afterFraction);
                } else {
                    result = beforeFraction.sub(afterFraction);
                }
                list.set(i - 1, result);
                i--;
                if (result.isPositive() == 0) {
                    return "error";
                }
            }
        }
        return "right";
    }

    @Override
    public Fraction calculateFra(List FraSymList) {
        Stack tempOperator = new Stack(); // 临时存储运算符和 (
        List operator = new ArrayList(); // 存储数值和运算符

        int len = FraSymList.size(); // 数字和运算符的总长度
        int times = 0; // 遍历次数

        while(times < len) {
            if (FraSymList.get(times) instanceof Fraction) {
                Fraction chara = (Fraction) FraSymList.get(times);
                operator.add(chara);
            } else {
                String chara = (String) FraSymList.get(times);
                switch (chara) {
                    case "(":
                        tempOperator.push(chara); // 将 ( 入栈
                        break;
                    case ")":
                        while (tempOperator.peek() != "(") { // 循环查找 ( 并返回，但不删除
                            operator.add(tempOperator.pop()); // 将除 ) 外的运算符存入 operator 中
                        }
                        tempOperator.pop(); // 弹出 ( ，丢弃括号 ()
                        break;
                    case "+":
                    case "-":
                        while(!tempOperator.empty() && tempOperator.peek() != "(") {
                            operator.add(tempOperator.pop());
                        }
                        tempOperator.push(chara);
                        break;
                    case "*":
                    case "/":
                        while(!tempOperator.empty() && tempOperator.peek().toString().matches("[*/]")) {
                            operator.add(tempOperator.pop());
                        }
                        tempOperator.push(chara);
                        break;
                    default:
                        operator.add(chara);
                        break;
                }
            }
            times++;
        }

        while(!tempOperator.empty()) {
            operator.add(tempOperator.pop());
        }
        System.out.println(operator);
        return calculate(operator);
    }
}
