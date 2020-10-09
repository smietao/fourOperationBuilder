package com.gdut.builder.model;

/*
 * 运算式+运算结果 包装类
 */
public class ResultMap {

    // 运算表达式
    private String exp;
    // 运算结果
    private String result;

    public ResultMap(String exp, String result) {
        super();
        this.exp = exp;
        this.result = result;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return exp + " = " + result;
    }

    public String toStringExp() {
        return exp + " = ";
    }

}
