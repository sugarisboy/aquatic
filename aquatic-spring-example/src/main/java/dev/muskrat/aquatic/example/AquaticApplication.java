package dev.muskrat.aquatic.example;

import dev.muskrat.aquatic.example.utils.SSLUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AquaticApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
		SSLUtil.turnOffSslChecking();
		SpringApplication.run(AquaticApplication.class, args);
	}

}
