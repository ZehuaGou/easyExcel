package com.paul.easyexcel.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.paul.easyexcel.listener.ExcelModelListener;
import com.paul.easyexcel.model.Student;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class ReadTest {

    /**
     * 同步读取
     */
    @Test
    public void read() {
        ReadService service = new ReadService();
        service.compareStudent();
    }

    /**
     * 异步读取
     */
    @Test
    public void ascyRead() {
        //读取 excel 表格的路径
        String readPath = "C:\\Users\\41729\\Desktop\\新建文件夹\\四临床.xlsx";
//        String readPath = "C:\\Users\\41729\\Desktop\\2017级名单.xlsx";

        try {
            Sheet sheet = new Sheet(1, 1, Student.class);
            ExcelModelListener listener = new ExcelModelListener();
            EasyExcelFactory.readBySax(new FileInputStream(readPath), sheet, listener);
            Map<String, Student> map = listener.getMap();
            System.out.println(map);
            System.out.println(map.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
