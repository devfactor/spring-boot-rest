package com.devfactor.service;

import com.devfactor.config.AppConfiguration;
import com.devfactor.model.TypicodePost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalApiService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);
    private final AppConfiguration appConfiguration;
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(AppConfiguration appConfiguration, RestTemplate restTemplate) {
        logger.debug("Initializing ExternalApiService");
        this.appConfiguration = appConfiguration;
        this.restTemplate = restTemplate;
    }

    /**
     * Make this call: curl -X GET -H "Accept: application/json" https://jsonplaceholder.typicode.com/posts/5
     *
     * @param postId
     * @return
     */
    public TypicodePost makeExternalApiCall(int postId) {
        logger.debug("In ExternalApiService.makeExternalApiCall for post id: " + postId);
        // set http headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // prod.properties file, our uri has parameter postId which needs to be filled in
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("postId", Integer.toString(postId));

        String url = appConfiguration.getDbConfig().getTypicodePostUrl();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        String uri = builder.buildAndExpand(uriParams).toUriString();
        // to have it put and return data in a specific class, we have to do this
        ParameterizedTypeReference<TypicodePost> typeRef = new ParameterizedTypeReference<TypicodePost>() {};
        // make the external api call
        ResponseEntity<TypicodePost> result = restTemplate.exchange(uri, HttpMethod.GET, entity, typeRef);
        logger.debug(result.getBody().toString());

        return result.getBody();
    }

}
