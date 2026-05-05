package com.jhon.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EUREKA SERVER — Servidor de descubrimiento de microservicios.
 *
 * @EnableEurekaServer activa el panel de control de Eureka y permite
 * que los microservicios clientes se registren automáticamente.
 *
 * Dashboard accesible en: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverApplication.class, args);
    }
}