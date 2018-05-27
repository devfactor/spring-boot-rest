package com.devfactor.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel( value = "Update numbers request", description = "The request to update multiple numbers" )
@Data
public class UpdateNumbersRequest {
    @ApiModelProperty( value = "The list of numbers to update", required = true )
    List<MyNumber> numbers;
}
