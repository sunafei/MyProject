package com.sun.designPattern.builder;

/**
 * 指挥者类
 * @author SunAFei
 *
 */
public class Director {
    
    public void construct(Builder builder) {//指挥建造过程
        builder.builderPartA();
        builder.builderPartB();
    }
}
