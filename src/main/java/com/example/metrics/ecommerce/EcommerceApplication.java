package com.example.metrics.ecommerce;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) throws IOException {
		DefaultExports.initialize();
		new HTTPServer(8000);
		SpringApplication.run(EcommerceApplication.class, args);
	}
}

