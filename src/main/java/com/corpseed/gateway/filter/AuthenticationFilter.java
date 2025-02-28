package com.corpseed.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


import com.corpseed.gateway.util.JwtUtil;
import com.corpseed.gateway.util.SecurityFeignClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    SecurityFeignClient securityFeignClient;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
//                    jwtUtil.validateToken(authHeader);
                    String userName = jwtUtil.validateToken(authHeader);
                    
                    if(userName.equals("vipan@corpseed.com")) {
                  	  System.out.println("test.....successs");
                    }else {
                      throw new RuntimeException("un authorized access to application");
                    }
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
                
                
//              String userName = jwtUtil.validateToken(authHeader);
//              if(userName.equals("vipan@corpseed.com")) {
//            	  System.out.println("test.....successs");
//              }else {
//                throw new RuntimeException("un authorized access to application");
//
//              }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}