package com.devfactor.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel( value = "TypicodePost", description = "A typicode post" )
@Data
public class TypicodePost {
    @ApiModelProperty( value = "User Id", required = true )
    private int userId;
    @ApiModelProperty( value = "Id", required = true )
    private int id;
    @ApiModelProperty( value = "Title", required = true )
    private String title;
    @ApiModelProperty( value = "Body", required = true )
    private String body;
}
