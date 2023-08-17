package com.genzdevlabs.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CarTM {
    private String brand;
    private String model;
    private String reg;
    private String year;
    private String fuel;
    private String capacity;
    private String colour;
    private String status;
}
