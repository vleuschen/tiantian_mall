package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.soap.Addressing;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    /**
        事务传播： Propagation
        REQUIRED: 默认，使用当前的事务，如果当前没有事务，则自己新建一个事务，子方法是必须
                运行在一个中的，如果当前存在事务，则加入这个事务，成为一个整体；
                举例：领导没饭吃，我有钱，我会自己买了自己吃，领导有的吃，会分给我吃
        SUPPORTS: 如果当前有事务，则使用事务，如果当前没有事务，则不使用事务
                举例：如果领导有饭吃，我就有饭吃，领导们没饭吃，我就没饭吃
        MANDATORY：强制使用该方法的方法必须开启事务，如果不存在，则抛出异常
                举例：领导必须管饭才能干活，不管饭不给干活
        REQUIRES_NEW:如果当前有事务，创建一个新事务，跟外部有没有事务无关，
                        如果当前没有事务，则同REQUIRED
        NOT_SUPPORTED: 当前没有事务就不适用事务，就算当前有事务，把当前事务挂起，也不使用事务执行相关操作
        NEVER: 不允许调用该方法的服务使用任何事务;
        NESTED: 如果当前有事务，则开启子事务，嵌套事务是独立的，
                如果当前没有事务，则同REQUIRED
                但是如果主事务提交，则会携带子事务一起提交
                如果主事务回滚，则子事务会一起回滚，相反，如果子事务异常，则父事务可以回滚也可以不会滚

     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setAge(23);
        stu.setId(1002);
        stu.setName("吴文龙");

        stuMapper.insert(stu);
    }

    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setAge(24);
        stu.setId(id);
        stuMapper.updateByPrimaryKey(stu);
    }

    @Override
    public void deleteStu(int id) {

        stuMapper.deleteByPrimaryKey(id);
    }
}
