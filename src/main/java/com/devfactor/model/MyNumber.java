package com.devfactor.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel( value = "The number object", description = "The number object" )
@Data
public class MyNumber {
    @ApiModelProperty( value = "The number id", required = true )
    private int id;
    @ApiModelProperty( value = "The number name", required = true )
    private String name;
}
