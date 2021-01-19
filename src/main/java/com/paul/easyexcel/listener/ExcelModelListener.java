package com.paul.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.paul.easyexcel.model.Student;
import com.paul.easyexcel.service.ReadService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建读取excel监听器
 * 异步读取方式
 */
public class ExcelModelListener extends AnalysisEventListener<Student> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    /**
     * 异步存储list
     */
    List<Student> acsyList = new ArrayList<>();

    /**
     * 存储此excel所有学生信息的list
     */
    private Map<String, Student> map = new HashMap<>();

    private static int count = 1;

    @Override
    public void invoke(Student data, AnalysisContext context) {
        System.out.println("解析到一条数据:{ " + data.toString() + " }");
        acsyList.add(data);
        count++;
        if (acsyList.size() >= BATCH_COUNT) {
            saveData(count);
            acsyList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData(count);
        System.out.println("所有数据解析完成！");
        System.out.println(" count ：" + count);
    }

    /**
     * 加上存储数据库
     */
    private void saveData(int count) {
        System.out.println("{ " + count + " }条数据，开始存储数据库！" + acsyList.size());
        ReadService service = new ReadService();
        map = service.addStudent(acsyList);
        System.out.println("存储数据库成功！");
    }

    public Map<String, Student> getMap() {
        return map;
    }
}