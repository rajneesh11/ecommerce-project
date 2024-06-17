package com.ekart.eKartApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-service",r->r.path("/api/auth/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://auth-service"))
                .route("product-service",r->r.path("/api/product/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://product-service"))
                .build();
    }
}
