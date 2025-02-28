package com.corpseed.gateway.util;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="CORPSEED-SECURITY", url="http://localhost:9990")
@Service
public interface SecurityFeignClient {
	@PostMapping("/securityService/api/auth/getUserByEmail")
	public String getUserByEmail(@RequestBody Long email); 
}
