package org.itheima.stock.config;

import org.itheima.stock.pojo.domain.StockInfoConfig;
import org.itheima.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 定义公共配置类
 */
@Configuration
@EnableConfigurationProperties({StockInfoConfig.class}) //  开启对象相关配置对象的加载
public class CommonConfig {

    /**
     * 定义密码加密容器、匹配器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 基于雪花算法保证生成的Id唯一规划
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        /**
         * 参数1：机器ID
         * 参数2：机房ID
         * 机房和机器的编号一般由运维人员进行唯一性规划
         */
        return new IdWorker();
    }
}
