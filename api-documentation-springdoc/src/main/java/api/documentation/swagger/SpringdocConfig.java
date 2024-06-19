package api.documentation.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

    @Bean
    public GroupedOpenApi membersOpenApi() {
        return GroupedOpenApi.builder()
                .group("members")
                .pathsToMatch("/members/**")
                .build();
    }
    @Bean
    public GroupedOpenApi petsOpenApi() {
        return GroupedOpenApi.builder()
                .group("pets")
                .pathsToMatch("/pets/**")
                .build();
    }
}
