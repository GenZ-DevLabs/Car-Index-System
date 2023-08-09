package com.genzdevlabs.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Car {
    private String brand;
    private String model;
    private String reg;
    private String year;
    private String fuel;
    private String capacity;
    private String colour;
}
