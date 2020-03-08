package com.imooc.service.impl;

import com.immoc.enums.Sex;
import com.immoc.utils.DateUtil;
import com.immoc.utils.MD5Utils;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private Sid sid;

    @Override
    public boolean queryUsernameExist(String username) {

        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username",username);
        Users result = usersMapper.selectOneByExample(userExample);

        return result == null ? false : true;

    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String userId = sid.nextShort();

        Users users = new Users();

        users.setId(userId);

        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认用户昵称同用户名
        users.setNickname(userBO.getUsername());
        //默认头像
        users.setFace(USER_FACE);
        //设置默认的生日
        users.setBirthday(DateUtil.stringToDate("1970-01-01"));
        //性别保密
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        usersMapper.insert(users);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example((Users.class));
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username",username);
        userCriteria.andEqualTo("password",password);

        Users result = usersMapper.selectOneByExample(userExample);

        return result;
    }



}
