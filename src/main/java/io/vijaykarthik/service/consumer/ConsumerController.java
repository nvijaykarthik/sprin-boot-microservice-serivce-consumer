package io.vijaykarthik.service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ConsumerController {

	
	private static final Logger log = LoggerFactory.getLogger(ConsumerController.class);

	@Autowired 
	RestTemplate restTemplate;
	
	 @Autowired
	 private EurekaClient discoveryClient;
	 
	 // will work if it is not load balanced via ribbon ie., RestTemplate Bean will have @loadbalanced anotation
	 @GetMapping("/")
	 public String getService() {
		 InstanceInfo info=discoveryClient.getNextServerFromEureka("api-gateway", false);
			String url=info.getHomePageUrl()+"/service-one";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			log.info(response.getBody());
			return response.getBody();
	 }
	 
	 @GetMapping("/ribbon")
	 public String getServiceRibbon() {
		    String url="http://api-gateway/service-one";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			log.info(response.getBody());
			return response.getBody();
	 }
}
