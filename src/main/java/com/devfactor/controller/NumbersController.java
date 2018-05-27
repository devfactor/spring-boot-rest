package com.devfactor.controller;

import java.util.List;

import com.devfactor.config.AppConfiguration;
import com.devfactor.exception.NumberNotFoundException;
import com.devfactor.model.*;
import com.devfactor.service.ExternalApiService;
import com.devfactor.service.MyDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myapp/")
@Api( value = "/myapp", description = "Manage numbers" )
public class NumbersController {
    private final AppConfiguration appConfiguration;
    private final MyDataService myDataService;
    private final ExternalApiService externalApiService;

    @Autowired
    public NumbersController(AppConfiguration appConfiguration, MyDataService myDataService, ExternalApiService externalApiService) {
        this.appConfiguration = appConfiguration;
        this.myDataService = myDataService;
        this.externalApiService = externalApiService;
    }

    private void printConfig() {
        StringBuilder sb = new StringBuilder();
        sb.append("Secret: ").append(appConfiguration.getSecret()).append("--");
        sb.append("Url: ").append(appConfiguration.getDbConfig().getUrl()).append("--");
        sb.append("Username: ").append(appConfiguration.getDbConfig().getUsername()).append("--");
        sb.append("Password: ").append(appConfiguration.getDbConfig().getPassword()).append("--");
        System.out.println(sb.toString());
    }

    @ApiOperation(
            value = "List all numbers",
            notes = "List all numbers in the database",
            response = ListNumbersResponse.class
    )
    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public ListNumbersResponse listNumbers() {
        printConfig();
        List<MyNumber> result = myDataService.listMyData();
        ListNumbersResponse response = new ListNumbersResponse();
        response.setMyNumberList(result);
        return response;
    }

    @ApiOperation(
            value = "Get a number",
            notes = "Get a number from the database",
            response = MyNumber.class
    )
    @RequestMapping(value = "/numbers/{id}", method = RequestMethod.GET)
    public MyNumber loadNumberById(@PathVariable(value = "id") int id) throws NumberNotFoundException {
        MyNumber myNumber = myDataService.loadMyDataById(id);
        return myNumber;
    }

    // To test:
    // Put this json in a file qq:
    //{
    //  "numbers": [
    //    {
    //      "id": 1,
    //      "name": "oneone"
    //    },
    //    {
    //      "id": 3,
    //      "name": "threethree"
    //    }
    //  ]
    //}
    //
    // Then:
    //       curl -H 'Content-Type: application/json' -H 'Accept: application/json' -X PUT -d @qq http://localhost:8080/myapp/numbers
    //@RequestMapping(value = "/numbers", method = RequestMethod.PUT)
    //Lets use a shortcut for the above more verbose annotation
    @ApiOperation(
            value = "Batch update numbers",
            notes = "Update one or more numbers to the database"
    )
    @PutMapping(value = "/numbers")
    public ResponseEntity<?> updateNumbers(@RequestBody UpdateNumbersRequest request) {
        List<MyNumber> numbers = request.getNumbers();
        myDataService.updateNumbers(numbers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Test this using curl command just like PUT above
    @ApiOperation(
            value = "Batch create numbers",
            notes = "Create one or more numbers to the database"
    )
    @PostMapping(value = "/numbers")
    public ResponseEntity<?> createNumbers(@RequestBody CreateNumbersRequest request) {
        List<MyNumber> numbers = request.getNumbers();
        myDataService.createNumbers(numbers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // curl -X DELETE http://localhost:8080/myapp/numbers/2
    @ApiOperation(
            value = "Delete a number",
            notes = "Delete a number in the database"
    )
    @DeleteMapping(value = "/numbers/{id}")
    public ResponseEntity<?> deleteNumber(@PathVariable(value = "id") int id) {
        myDataService.deleteNumber(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // curl -X GET -H "Accept: application/json" http://localhost:8080/myapp/externalApiCall/5
    // https://jsonplaceholder.typicode.com/
    @ApiOperation(
            value = "Make an external API call",
            notes = "Get a number from typicode",
            response = TypicodePost.class
    )
    @GetMapping(value = "externalApiCall/{postId}")
    public TypicodePost getTypicodePostById(@PathVariable("postId") int postId) {
        return externalApiService.makeExternalApiCall(postId);
    }

}
