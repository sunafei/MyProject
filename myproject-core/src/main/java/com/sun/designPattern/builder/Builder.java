package com.sun.designPattern.builder;

/**
 * 抽象建造者类，确定产品有两个部件组成，并声明一个得到产品建造后结果的方法getResult
 * @author SunAFei
 *
 */
public abstract class Builder {
    public abstract void builderPartA();

    public abstract void builderPartB();
    
    public abstract Product getResult();
}