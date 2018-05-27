package com.devfactor.controller;

import com.devfactor.config.AppConfiguration;
import com.devfactor.model.MyNumber;
import com.devfactor.model.UpdateNumbersRequest;
import com.devfactor.service.ExternalApiService;
import com.devfactor.service.MyDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NumbersControllerTest {

    NumbersController testSubject;
    @Mock
    private MyDataService myDataService;
    @Mock
    private ExternalApiService externalApiService;
    @Mock
    private AppConfiguration appConfiguration;

    @Before
    public void setup() throws Exception {
        testSubject = new NumbersController(appConfiguration, myDataService, externalApiService);
    }

    @Test
    public void updateNumbers() {
        UpdateNumbersRequest request = new UpdateNumbersRequest();
        List<MyNumber> numbers = new ArrayList<>();
        MyNumber number = new MyNumber(); number.setId(1); number.setName("One");
        numbers.add(number);
        request.setNumbers(numbers);
        doNothing().when(myDataService).updateNumbers(numbers);
        testSubject.updateNumbers(request);
        verify(myDataService).updateNumbers(numbers);
    }

}
