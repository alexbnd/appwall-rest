package com.radware.appwall.appwallrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.radware.appwall"})
public class AppwallRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppwallRestApplication.class, args);
	}
}
