package com.paul.easyexcel.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.paul.easyexcel.model.Student;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读excel逻辑
 */
public class ReadService {

    /**
     * 需读取的excel路径
     */
    private static final String successPath = "C:\\Users\\41729\\Desktop\\新建文件夹\\四临床.xlsx";

    private static final List<String> classes = new ArrayList<>();

    /**
     * 未完成总人数
     */
    private Integer totalCount = 0;

    /**
     * 将读取的excel中学生信息存储在list中
     */
    public Map<String, Student> map = new HashMap<>();

    static {
        classes.add("2016-2班");
        classes.add("2016-12班");
        classes.add("2016-19班");
        classes.add("2017-3班");
        classes.add("2017-4班");
        classes.add("2017-16班");
        classes.add("2017-28班");
    }

    /**
     * 将异步读取的学生数据，存储到list中
     *
     * @param acsyList
     * @return
     */
    public Map<String, Student> addStudent(List<Student> acsyList) {

        for (Student student : acsyList) {
            map.put(student.getName(), student);
        }

        return map;
    }

    /**
     * 获得已经成功完成学习的所有学生
     *
     * @return
     */
    public Map<String, Student> getSuccessStudent() {
        Map<String, Student> map = getStudents(successPath);
        System.out.println("完成学习的人数：" + map.size());
        return map;
    }

    /**
     * 获得某班所有学生信息
     *
     * @return
     */
    public Map<String, Student> getStudentByClass(String className) {
        Map<String, Student> map = getStudents(getClassName(className));
//        System.out.println(className + "总人数：" + map.size());
        return map;
    }

    /**
     * 获得班级名
     *
     * @param className
     * @return
     */
    private String getClassName(String className) {
        return "C:\\Users\\41729\\Desktop\\新建文件夹\\" + className + ".xlsx";
    }

    /**
     * 根据路径获得该excel中所有学生信息
     *
     * @param path
     * @return
     */
    private Map<String, Student> getStudents(String path) {
        try {
            // sheetNo --> 读取哪一个 表单
            // headLineMun --> 从哪一行开始读取( 不包括定义的这一行，比如 headLineMun为2 ，那么取出来的数据是从 第三行的数据开始读取 )
            // clazz --> 将读取的数据，转化成对应的实体，需要 extends BaseRowModel
            Sheet sheet = new Sheet(1, 1, Student.class);

            // 这里 取出来的是 Student 的集合
            List<Object> readList = EasyExcelFactory.read(new FileInputStream(path), sheet);
            // 存 Student 实体的 集合
            Map<String, Student> map = new HashMap<>();
            for (Object obj : readList) {
                Student student = (Student) obj;

                map.put(student.getName(), student);
            }

            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void compareStudent() {
        Map<String, Student> successMap = getSuccessStudent();

        for (int i = 0; i < classes.size(); i++) {
            System.out.println();
            System.out.println("----------------------------------");
            //本班未完成人数
            int count = 0;
            Map<String, Student> eachClassMap = getStudentByClass(classes.get(i));
            System.out.print(classes.get(i) + "未完成名单：");
            for (String key : eachClassMap.keySet()) {
                if (!successMap.containsKey(key)) {
                    count++;
                    totalCount++;
                    System.out.print(key + "，");
                }
            }
            if (count == 0) {
                System.out.println("无");
            } else {
                System.out.println();
            }
            System.out.println(classes.get(i) + "未完成人数：" + count);
        }
        System.out.println("全部班级未完成总人数：" + totalCount);
    }
}
