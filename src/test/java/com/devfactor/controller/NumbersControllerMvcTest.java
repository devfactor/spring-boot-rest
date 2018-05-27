/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devfactor.controller;

import com.devfactor.model.CreateNumbersRequest;
import com.devfactor.model.MyNumber;
import com.devfactor.model.TypicodePost;
import com.devfactor.model.UpdateNumbersRequest;
import com.devfactor.service.ExternalApiService;
import com.devfactor.service.MyDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * These are MVC tests. They will test your controllers (resource classes) - e.g. /numbers - by actually calling them
 * using the http stack, but without actually starting up a web server. And the results are
 * all mocked values. This is as opposed to integration tests, which will actually
 * perform the operations via http.
 *
 */
@AutoConfigureMockMvc
@AutoConfigureWebClient
@RunWith(SpringRunner.class)
@WebMvcTest(NumbersController.class)
@TestPropertySource(value = "classpath:prod.properties")
@ComponentScan("com.devfactor")
public class NumbersControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MyDataService myDataService;
    @MockBean
    private ExternalApiService externalApiService;
    @MockBean
    private JdbcTemplate jdbcTemplate;
    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<List<MyNumber>> captor;

    @Test
    public void listNumbers() throws Exception {
        MyNumber res1 = new MyNumber(); res1.setId(1); res1.setName("One");
        MyNumber res2 = new MyNumber(); res2.setId(2); res2.setName("Two");
        List<MyNumber> result = Arrays.asList(res1, res2);
        when(myDataService.listMyData()).thenReturn(result);
        this.mockMvc
                .perform(get("/myapp/numbers").accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.myNumberList[0].id").value(1))
                .andExpect(jsonPath("$.myNumberList[0].name").value("One"))
                .andExpect(jsonPath("$.myNumberList[1].id").value(2))
                .andExpect(jsonPath("$.myNumberList[1].name").value("Two"));
    }

    @Test
    public void loadNumberById() throws Exception {
        MyNumber result = new MyNumber(); result.setId(1); result.setName("One");
        when(myDataService.loadMyDataById(11)).thenReturn(result);
        this.mockMvc
                .perform(get("/myapp/numbers/11").accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("One"));
    }

    @Test
    public void updateNumbers() throws Exception {
        MyNumber res1 = new MyNumber(); res1.setId(1); res1.setName("One");
        MyNumber res2 = new MyNumber(); res2.setId(2); res2.setName("Two");
        List<MyNumber> numbers = Arrays.asList(res1, res2);
        UpdateNumbersRequest request = new UpdateNumbersRequest(); request.setNumbers(numbers);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(request);
        // this update api call doesn't return anything. however, we are testing our code, and we know
        // that our controller converts the json into a list of MyNumber objects. so to test this,
        // we can capture and verify the arguments. Also, we can always check the http status
        doNothing().when(myDataService).updateNumbers(captor.capture());
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/myapp/numbers").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        List<MyNumber> params = captor.getAllValues().get(0);
        assertThat(params.size()).isEqualTo(2);
    }

    @Test
    public void createNumbers() throws Exception {
        MyNumber res1 = new MyNumber(); res1.setId(1); res1.setName("One");
        MyNumber res2 = new MyNumber(); res2.setId(2); res2.setName("Two");
        List<MyNumber> numbers = Arrays.asList(res1, res2);
        CreateNumbersRequest request = new CreateNumbersRequest(); request.setNumbers(numbers);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(request);
        // this create api call doesn't return anything. however, we are testing our code, and we know
        // that our controller converts the json into a list of MyNumber objects. so to test this,
        // we can capture and verify the arguments. Also, we can always check the http status
        doNothing().when(myDataService).createNumbers(captor.capture());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/myapp/numbers").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
        List<MyNumber> params = captor.getAllValues().get(0);
        assertThat(params.size()).isEqualTo(2);
    }

    @Test
    public void deleteNumber() throws Exception {
        // this create api call doesn't return anything. however, we are testing our code, and we know
        // that our controller converts the json into a list of MyNumber objects. so to test this,
        // we can capture and verify the arguments. Also, we can always check the http status
        doNothing().when(myDataService).deleteNumber(11);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/myapp/numbers/11").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTypicodePostById() throws Exception {
        TypicodePost post = new TypicodePost();
        post.setBody("body"); post.setId(1); post.setTitle("title"); post.setUserId(2);
        when(externalApiService.makeExternalApiCall(1)).thenReturn(post);
        this.mockMvc
                .perform(get("/myapp/externalApiCall/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.body").value("body"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.userId").value(2));
    }

    /**
     * This is required if we want to read properties from src/test/resources. And then we
     * also use @TestPropertySource
     */
    @Configuration
    static class Config {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}
