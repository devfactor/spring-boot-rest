package com.devfactor.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel( value = "Create numbers request", description = "The request to create multiple numbers" )
@Data
public class CreateNumbersRequest {
    @ApiModelProperty( value = "The list of numbers to create", required = true )
    List<MyNumber> numbers;
}
