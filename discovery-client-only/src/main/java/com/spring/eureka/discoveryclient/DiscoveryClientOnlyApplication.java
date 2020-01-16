package com.spring.eureka.discoveryclient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.discovery.EurekaClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DiscoveryClientOnlyApplication {


	@Autowired
	private EurekaClient client;

	@Autowired
	private RestTemplateBuilder builder;

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryClientOnlyApplication.class, args);
	}

	@RequestMapping("/node")
	public String callNodeService(){
		RestTemplate restTemplate = builder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka("node-service", false);
		String baseURL = instanceInfo.getHomePageUrl();
//		String baseURL = "http://localhost:8080/node-service/";

		ResponseEntity<String> response = restTemplate.exchange(baseURL, HttpMethod.GET, null, String.class);
		return response.getBody();

	}


	@RequestMapping("/java")
	public String callJavaService(){
		RestTemplate restTemplate = builder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka("config-server-app", false);
		String baseURL = instanceInfo.getHomePageUrl();
//		String baseURL = "http://localhost:8080/node-service/";

		ResponseEntity<String> response = restTemplate.exchange(baseURL, HttpMethod.GET, null, String.class);
		return response.getBody();

	}

}
