package com.company.cloud.turbine.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class TurbineServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TurbineServerApplication.class).web(true).run(args);
	}
}
