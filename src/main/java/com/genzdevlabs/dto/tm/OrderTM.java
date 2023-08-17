package com.genzdevlabs.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderTM {
    private String oid;
    private String nic;
    private String reg;
    private String name;
    private String brand;
    private String model;
    private String colour;
    private String date;
}
