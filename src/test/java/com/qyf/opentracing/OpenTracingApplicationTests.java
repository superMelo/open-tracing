package com.qyf.opentracing;

import com.qyf.opentracing.agent.AgentClassLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@SpringBootTest
class OpenTracingApplicationTests {

	@Test
	void contextLoads() {
	}


	public static void main(String[] args) {
		Enumeration<URL> urls;
		try {
			AgentClassLoader.initDefaultLoader();
			urls = AgentClassLoader.getDefault().getResources("openTracing-plugin.def");
			while (urls.hasMoreElements()) {
				URL pluginUrl = urls.nextElement();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
