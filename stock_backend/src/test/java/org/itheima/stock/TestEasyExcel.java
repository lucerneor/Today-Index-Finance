package org.itheima.stock;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.itheima.stock.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyExcel {

    public List<User> init(){
        //组装数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAddress("上海"+i);
            user.setUserName("张三"+i);
            user.setBirthday(new Date());
            user.setAge(10+i);
            users.add(user);
        }
        return users;
    }


    /**
     * @desc 测试excel导出功能
     */
    @Test
    public void test01(){
        List<User> users = this.init();
        //导出数据到外部的excel下
        EasyExcel.write("C:\\Users\\HP\\Desktop\\阳果 (2)\\test.xls",User.class)
                .sheet("用户信息").doWrite(users);
    }

    /**
     * @desc 读取excel数据
     */
    @Test
    public void test02(){
        List<User> users = new ArrayList<>();

        EasyExcel.read("C:\\Users\\HP\\Desktop\\阳果 (2)\\test.xls", User.class, new AnalysisEventListener<User>() {
            /**
             * 逐行读取excel内容，并封装（读取一行，就回调依次该方法）
             * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
             * @param context analysis context
             */
            @Override
            public void invoke(User data, AnalysisContext context) {
                users.add(data);
//                sysUserMapper.insert(data);
            }

            /**
             * 所有行读取完毕后，回调方法（读取完成的通知）
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.println("excel read finished~~");
            }
        }).sheet("用户信息").doRead();
    }
}
