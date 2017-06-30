package com.sun.application.statistic.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表头单元格对象
 * 
 */
public class HeadCell implements Serializable, Cloneable {

    private static final long serialVersionUID = -1756261752878954234L;

    private String id;
    private String name;
    private HeadCell parent;// 父单元格对象
    private List<HeadCell> children = new ArrayList<HeadCell>();// 子单元格集合
    private Integer deep = 0;// 层深度

    public HeadCell(String id, String name, HeadCell parent, Integer deep) {
        super();
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.deep = deep;
    }

    public HeadCell getParent() {
        return parent;
    }

    public void setParent(HeadCell parent) {
        this.parent = parent;
    }

    public List<HeadCell> getChildren() {
        return children;
    }

    public void setChildren(List<HeadCell> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    /*
     * 克隆headCell实体
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public HeadCell clone() {
        HeadCell o = null;
        try {
            o = (HeadCell) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 如果选择了多个统计项单元格对象的最底层的子对象的个数
     * 
     * @param countItem 统计项的个数
     * @return
     */
    public int getColNum(int countItem) {
        int rowNum = children.size();
        for (HeadCell hc : children) {
            if (hc.getColNum(countItem) > 0) {
                rowNum += hc.getColNum(countItem) - 1;
            }
        }
        return rowNum == 0 ? countItem : rowNum;
    }

    /**
     * 取得单元格对象的最底层的子对象的个数
     * 
     * @return 深度
     */
    public int getRowNum() {
        int rowNum = children.size();
        for (HeadCell hc : children) {
            if (hc.getRowNum() > 0) {
                rowNum += hc.getRowNum() - 1;
            }
        }
        return rowNum;
    }
}