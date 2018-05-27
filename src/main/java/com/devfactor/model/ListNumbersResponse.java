package com.devfactor.model;

import lombok.Data;

import java.util.List;

@Data
public class ListNumbersResponse {
    List<MyNumber> myNumberList;
}
