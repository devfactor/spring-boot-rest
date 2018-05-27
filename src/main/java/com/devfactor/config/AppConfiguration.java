package com.devfactor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppConfiguration {
    private final DbConfig dbConfig;
    private final String secret;
    @Autowired
    public AppConfiguration(@Value("${secret}") String secret,
                            DbConfig dbConfig) {
        this.dbConfig = dbConfig;
        this.secret = secret;
    }
}
