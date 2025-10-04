package it.corona.fabrick.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fabrick.url")
public class FabrickConfig {
    private String authSchema;
    private String apiKey;
    private String bankAccount;
    private String balance;
}
