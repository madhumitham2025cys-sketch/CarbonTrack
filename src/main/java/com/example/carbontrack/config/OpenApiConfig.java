package com.example.carbontrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI carbonTrackApi() {
        return new OpenAPI().info(new Info()
                .title("CarbonTrack API")
                .description("REST API for Household Carbon Footprint Calculator with Challenges")
                .version("1.0"));
    }
}
