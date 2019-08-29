package com.example.tomcat.demomultiplevalve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;

@SpringBootApplication
public class DemomultiplevalveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemomultiplevalveApplication.class, args);
	}

}

@RestController
class ApplicationController {

	@GetMapping("/ping")
	public String ping(){
		return "PONG";
	}
}

class Valve1 extends ValveBase {

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		System.out.println("invoked :: Valve1");
		next.invoke(request, response);
	}
}

class Valve2 extends ValveBase {

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		System.out.println("invoked :: Valve2");
		next.invoke(request, response);
	}
}


@Component
class CustomContainer implements
		WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.setContextPath("/custom");
		factory.setPort(8080);
		factory.addEngineValves(new Valve1());
		factory.addContextValves(new Valve2());

	}
}
