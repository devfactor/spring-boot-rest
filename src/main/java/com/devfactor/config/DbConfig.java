package com.devfactor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Data
@Component
public class DbConfig {
    private final String url;
    private final String username;
    private final String password;
    private final String typicodePostUrl;
    public DbConfig(@Value("${database.url}") String url,
                    @Value("${database.username}") String username,
                    @Value("${database.password}") String password,
                    @Value("${external-api.get-post.url}") String typicodePostUrl) throws MalformedURLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.typicodePostUrl = typicodePostUrl;
    }
}
