package com.inson.ersp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        servers = {@Server(url = "${server.url}",description = "server URL")},
        info = @Info(title = "ERSP API", version = "1.0", description = "ERSP Insurance Products Integration")
)

@SpringBootApplication
public class ErspApplication {

    public static String TRANSACTION_ID = "transactionId";

    public static void main(String[] args) {
        SpringApplication.run(ErspApplication.class, args);
    }

}
