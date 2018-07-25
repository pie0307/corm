package pro.pie.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author : liuby.
 * Description :
 * Date : Created in 2018/7/25 16:02
 */
@SpringBootApplication(scanBasePackages = {"pro.pie.me"})
public class ApplicationStarter {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
