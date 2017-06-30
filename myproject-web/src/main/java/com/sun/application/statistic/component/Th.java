package com.sun.application.statistic.component;

import org.apache.commons.lang3.StringUtils;

/**
 * 用于生成横向表头的html
 * 这里这样设计的原因是我们的表格是按照横向生成也就是一个tr一个tr的生成，然而
 * 横向表头只有上下级有主子关系，所以我这里用rowNum来区分它们，最终达到我生成tr的效果
 */
public class Th {
    private int colspan = 1;// 合并列
    private int rowspan = 1;// 合并行
    private String thStr;// html代码
    private String value;// th标签文本值
    private int rowNum;// 行号
    private String thId;//这里的id和pid主要用于一个js的功能,即当一行或者一列的td全为空时隐藏掉当前行
    private String pId;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public String getThStr() {
        StringBuilder thHtml = new StringBuilder();
        if (colspan == 0) {
            colspan = 1;
        }
        if (rowspan == 0) {
            rowspan = 1;
        }
        if (StringUtils.isBlank(pId)) {
            pId = "";
        }
        thHtml.append("<th id='").append(thId).append("'").append(" pId='").append(pId).append("' deep='").append(rowNum + 1).append("' colspan='").append(colspan).append("' rowspan='").append(rowspan);
        thHtml.append("'>").append(value).append("</th>");
        return thHtml.toString();
    }

    public void setThStr(String thStr) {
        this.thStr = thStr;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getThId() {
        return thId;
    }

    public void setThId(String thId) {
        this.thId = thId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}