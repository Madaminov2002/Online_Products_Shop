package org.example.online_products_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class OnlineProductsShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineProductsShopApplication.class, args);
    }

}
