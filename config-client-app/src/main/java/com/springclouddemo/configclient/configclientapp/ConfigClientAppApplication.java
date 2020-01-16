package com.springclouddemo.configclient.configclientapp;

import com.thoughtworks.xstream.converters.basic.StringBuilderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class ConfigClientAppApplication {

	@Autowired
	private ConfigClientAppConfiguration properties;

	@Value("${some.other.property}")
	private String someOtherproperty;

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientAppApplication.class, args);
	}

	@RequestMapping("/")
	public String printConfig(){
		StringBuilder str = new StringBuilder();
		return str.append(someOtherproperty).append(" || ").append(properties.getProperty()).toString();
	}

}
