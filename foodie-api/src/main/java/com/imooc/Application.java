package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
// 扫描mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.imooc.mapper")
// 扫描包以及相关组件包
@ComponentScan(basePackages = {"org.n3r.idworker","com.imooc"})
@EnableScheduling //开启定时任务
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


}
