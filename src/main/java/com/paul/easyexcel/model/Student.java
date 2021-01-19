package com.paul.easyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 学生信息
 */
@Data
public class Student extends BaseRowModel {

    /**
     * 学生姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 学生所属班级
     */
//    @ExcelProperty(index = 5)
//    private String team;
}
