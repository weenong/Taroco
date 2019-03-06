package cn.taroco.rbac.admin;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author liuht
 * @date 2017年10月27日13:59:05
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
public class TarocoRbacApplication {
    public static void main(String[] args) {
        SpringApplication.run(TarocoRbacApplication.class, args);
    }
}
