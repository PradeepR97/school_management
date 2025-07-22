package com.schoolmanagement.dto;

import lombok.Data;



@Data
public class AddressDTO {
    private Long id;
    private Long studentId;
    private String street;
    private String city;
    private String state;
    private String zip;
}

